/*
 * Java port of Bullet (c) 2008 Martin Dvorak <jezek2@advel.cz>
 *
 * Bullet Continuous Collision Detection and Physics Library
 * Copyright (c) 2003-2008 Erwin Coumans  http://www.bulletphysics.com/
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from
 * the use of this software.
 * 
 * Permission is granted to anyone to use this software for any purpose, 
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 * 
 * 1. The origin of this software must not be misrepresented; you must not
 *    claim that you wrote the original software. If you use this software
 *    in a product, an acknowledgment in the product documentation would be
 *    appreciated but is not required.
 * 2. Altered source versions must be plainly marked as such, and must not be
 *    misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 */

package com.elusivehawk.engine.physics.jbullet.collision.narrowphase;

import java.util.Arrays;
import static com.elusivehawk.engine.math.MathConst.*;
import com.elusivehawk.engine.math.Matrix;
import com.elusivehawk.engine.math.Quaternion;
import com.elusivehawk.engine.math.Vector;
import com.elusivehawk.engine.physics.jbullet.BulletGlobals;
import com.elusivehawk.engine.physics.jbullet.collision.shapes.ConvexShape;
import com.elusivehawk.engine.physics.jbullet.linearmath.MatrixUtil;
import com.elusivehawk.engine.physics.jbullet.linearmath.QuaternionUtil;
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import com.elusivehawk.engine.physics.jbullet.linearmath.VectorUtil;
import com.elusivehawk.engine.physics.jbullet.util.ArrayPool;
import com.elusivehawk.engine.physics.jbullet.util.ObjectStackList;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */

/*
GJK-EPA collision solver by Nathanael Presson
Nov.2006
*/

/**
 * GjkEpaSolver contributed under zlib by Nathanael Presson.
 * 
 * @author jezek2
 */
public class GjkEpaSolver {

	protected final ArrayPool<float[]> floatArrays = ArrayPool.get(float.class);

	protected final ObjectStackList<Mkv> stackMkv = new ObjectStackList<Mkv>(Mkv.class);
	protected final ObjectStackList<He> stackHe = new ObjectStackList<He>(He.class);
	protected final ObjectStackList<Face> stackFace = new ObjectStackList<Face>(Face.class);

	protected void pushStack() {
		this.stackMkv.push();
		this.stackHe.push();
		this.stackFace.push();
	}

	protected void popStack() {
		this.stackMkv.pop();
		this.stackHe.pop();
		this.stackFace.pop();
	}

	public enum ResultsStatus {
		Separated,		/* Shapes doesnt penetrate												*/ 
		Penetrating,	/* Shapes are penetrating												*/ 
		GJK_Failed,		/* GJK phase fail, no big issue, shapes are probably just 'touching'	*/ 
		EPA_Failed,		/* EPA phase fail, bigger problem, need to save parameters, and debug	*/ 
	}
	
	public static class Results {
		public ResultsStatus status;
		public final Vector[] witnesses/*[2]*/ = new Vector[] { new Vector(3), new Vector(3) };
		public final Vector normal = new Vector();
		public float depth;
		public int epa_iterations;
		public int gjk_iterations;
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	private static final float cstInf = BulletGlobals.SIMD_INFINITY;
	private static final float cstPi = BulletGlobals.SIMD_PI;
	private static final float cst2Pi = BulletGlobals.SIMD_2_PI;
	private static final int GJK_maxiterations = 128;
	private static final int GJK_hashsize = 1 << 6;
	private static final int GJK_hashmask = GJK_hashsize - 1;
	private static final float GJK_insimplex_eps = 0.0001f;
	private static final float GJK_sqinsimplex_eps = GJK_insimplex_eps * GJK_insimplex_eps;
	private static final int EPA_maxiterations = 256;
	private static final float EPA_inface_eps = 0.01f;
	private static final float EPA_accuracy = 0.001f;
	
	////////////////////////////////////////////////////////////////////////////

	public static class Mkv {
		public final Vector w = new Vector(); // Minkowski vertice
		public final Vector r = new Vector(); // Ray

		public void set(Mkv m) {
			this.w.set(m.w);
			this.r.set(m.r);
		}
	}

	public static class He {
		public final Vector v = new Vector();
		public He n;
	}
	
	protected class GJK {
		//protected final BulletStack stack = BulletStack.get();
		
		//public btStackAlloc sa;
		//public Block sablock;
		public final He[] table = new He[GJK_hashsize];
		public final Matrix[] wrotations/*[2]*/ = new Matrix[] { new Matrix(3, 3), new Matrix(3, 3) };
		public final Vector[] positions/*[2]*/ = new Vector[] { new Vector(), new Vector() };
		public final ConvexShape[] shapes = new ConvexShape[2];
		public final Mkv[] simplex = new Mkv[5];
		public final Vector ray = new Vector();
		public /*unsigned*/ int order;
		public /*unsigned*/ int iterations;
		public float margin;
		public boolean failed;
		
		{
			for (int i=0; i<this.simplex.length; i++) this.simplex[i] = new Mkv();
		}

		public GJK() {
		}

		public GJK(/*StackAlloc psa,*/
				Matrix wrot0, Vector pos0, ConvexShape shape0,
				Matrix wrot1, Vector pos1, ConvexShape shape1) {
			this(wrot0, pos0, shape0, wrot1, pos1, shape1, 0f);
		}

		public GJK(/*StackAlloc psa,*/
				Matrix wrot0, Vector pos0, ConvexShape shape0,
				Matrix wrot1, Vector pos1, ConvexShape shape1,
				float pmargin) {
			init(wrot0, pos0, shape0, wrot1, pos1, shape1, pmargin);
		}
		
		public void init(/*StackAlloc psa,*/
				Matrix wrot0, Vector pos0, ConvexShape shape0,
				Matrix wrot1, Vector pos1, ConvexShape shape1,
				float pmargin) {
			pushStack();
			this.wrotations[0].set(wrot0);
			this.positions[0].set(pos0);
			this.shapes[0] = shape0;
			this.wrotations[1].set(wrot1);
			this.positions[1].set(pos1);
			this.shapes[1] = shape1;
			//sa		=psa;
			//sablock	=sa->beginBlock();
			this.margin = pmargin;
			this.failed = false;
		}
		
		public void destroy() {
			popStack();
		}
		
		// vdh: very dummy hash
		public /*unsigned*/ int Hash(Vector v) {
			int h = (int)(v.get(X)* 15461) ^ (int)(v.get(Y)* 83003) ^ (int)(v.get(Z)* 15473);
			return (h * 169639) & GJK_hashmask;
		}

		public Vector LocalSupport(Vector d, /*unsigned*/ int i, Vector out) {
			Vector tmp = Stack.alloc(new Vector(3));
			MatrixUtil.transposeTransform(tmp, d, this.wrotations[i]);

			this.shapes[i].localGetSupportingVertex(tmp, out);
			this.wrotations[i].transform(out);
			out.add(this.positions[i]);

			return out;
		}
		
		public void Support(Vector d, Mkv v) {
			v.r.set(d);

			Vector tmp1 = LocalSupport(d, 0, Stack.alloc(new Vector(3)));

			Vector tmp = Stack.alloc(new Vector(3));
			tmp.set(d);
			tmp.negate();
			Vector tmp2 = LocalSupport(tmp, 1, Stack.alloc(new Vector(3)));

			v.w.sub(tmp1, tmp2);
			v.w.scaleAdd(this.margin, d, v.w);
		}

		public boolean FetchSupport() {
			int h = Hash(this.ray);
			He e = this.table[h];
			while (e != null) {
				if (e.v.equals(this.ray)) {
					--this.order;
					return false;
				}
				e = e.n;
			}
			//e = (He*)sa->allocate(sizeof(He));
			//e = new He();
			e = GjkEpaSolver.this.stackHe.get();
			e.v.set(this.ray);
			e.n = this.table[h];
			this.table[h] = e;
			Support(this.ray, this.simplex[++this.order]);
			return (this.ray.dot(this.simplex[this.order].w) > 0);
		}

		public boolean SolveSimplex2(Vector ao, Vector ab) {
			if (ab.dot(ao) >= 0) {
				Vector cabo = Stack.alloc(new Vector(3));
				cabo.cross(ab, ao);
				if (cabo.lengthSquared() > GJK_sqinsimplex_eps) {
					this.ray.cross(cabo, ab);
				}
				else {
					return true;
				}
			}
			else {
				this.order = 0;
				this.simplex[0].set(this.simplex[1]);
				this.ray.set(ao);
			}
			return (false);
		}

		public boolean SolveSimplex3(Vector ao, Vector ab, Vector ac)
		{
			Vector tmp = Stack.alloc(new Vector(3));
			tmp.cross(ab, ac);
			return (SolveSimplex3a(ao,ab,ac,tmp));
		}
		
		public boolean SolveSimplex3a(Vector ao, Vector ab, Vector ac, Vector cabc) {
			// TODO: optimize

			Vector tmp = Stack.alloc(new Vector(3));
			tmp.cross(cabc, ab);

			Vector tmp2 = Stack.alloc(new Vector(3));
			tmp2.cross(cabc, ac);

			if (tmp.dot(ao) < -GJK_insimplex_eps) {
				this.order = 1;
				this.simplex[0].set(this.simplex[1]);
				this.simplex[1].set(this.simplex[2]);
				return SolveSimplex2(ao, ab);
			}
			else if (tmp2.dot(ao) > +GJK_insimplex_eps) {
				this.order = 1;
				this.simplex[1].set(this.simplex[2]);
				return SolveSimplex2(ao, ac);
			}
			else {
				float d = cabc.dot(ao);
				if (Math.abs(d) > GJK_insimplex_eps) {
					if (d > 0) {
						this.ray.set(cabc);
					}
					else {
						this.ray.negate(cabc);

						Mkv swapTmp = new Mkv();
						swapTmp.set(this.simplex[0]);
						this.simplex[0].set(this.simplex[1]);
						this.simplex[1].set(swapTmp);
					}
					return false;
				}
				return true;
			}
		}
		
		public boolean SolveSimplex4(Vector ao, Vector ab, Vector ac, Vector ad) {
			// TODO: optimize

			Vector crs = Stack.alloc(new Vector(3));

			Vector tmp = Stack.alloc(new Vector(3));
			tmp.cross(ab, ac);

			Vector tmp2 = Stack.alloc(new Vector(3));
			tmp2.cross(ac, ad);

			Vector tmp3 = Stack.alloc(new Vector(3));
			tmp3.cross(ad, ab);

			if (tmp.dot(ao) > GJK_insimplex_eps) {
				crs.set(tmp);
				this.order = 2;
				this.simplex[0].set(this.simplex[1]);
				this.simplex[1].set(this.simplex[2]);
				this.simplex[2].set(this.simplex[3]);
				return SolveSimplex3a(ao, ab, ac, crs);
			}
			else if (tmp2.dot(ao) > GJK_insimplex_eps) {
				crs.set(tmp2);
				this.order = 2;
				this.simplex[2].set(this.simplex[3]);
				return SolveSimplex3a(ao, ac, ad, crs);
			}
			else if (tmp3.dot(ao) > GJK_insimplex_eps) {
				crs.set(tmp3);
				this.order = 2;
				this.simplex[1].set(this.simplex[0]);
				this.simplex[0].set(this.simplex[2]);
				this.simplex[2].set(this.simplex[3]);
				return SolveSimplex3a(ao, ad, ab, crs);
			}
			else {
				return (true);
			}
		}
		
		public boolean SearchOrigin() {
			Vector tmp = Stack.alloc(new Vector(3));
			tmp.set(1f, 0f, 0f);
			return SearchOrigin(tmp);
		}
		
		public boolean SearchOrigin(Vector initray) {
			Vector tmp1 = Stack.alloc(new Vector(3));
			Vector tmp2 = Stack.alloc(new Vector(3));
			Vector tmp3 = Stack.alloc(new Vector(3));
			Vector tmp4 = Stack.alloc(new Vector(3));

			this.iterations = 0;
			this.order = -1;
			this.failed = false;
			this.ray.set(initray);
			this.ray.normalize();

			Arrays.fill(this.table, null);

			FetchSupport();
			this.ray.negate(this.simplex[0].w);
			for (; this.iterations < GJK_maxiterations; ++this.iterations) {
				float rl = this.ray.length();
				this.ray.scale(1f / (rl > 0f ? rl : 1f));
				if (FetchSupport()) {
					boolean found = false;
					switch (this.order) {
						case 1: {
							tmp1.negate(this.simplex[1].w);
							tmp2.sub(this.simplex[0].w, this.simplex[1].w);
							found = SolveSimplex2(tmp1, tmp2);
							break;
						}
						case 2: {
							tmp1.negate(this.simplex[2].w);
							tmp2.sub(this.simplex[1].w, this.simplex[2].w);
							tmp3.sub(this.simplex[0].w, this.simplex[2].w);
							found = SolveSimplex3(tmp1, tmp2, tmp3);
							break;
						}
						case 3: {
							tmp1.negate(this.simplex[3].w);
							tmp2.sub(this.simplex[2].w, this.simplex[3].w);
							tmp3.sub(this.simplex[1].w, this.simplex[3].w);
							tmp4.sub(this.simplex[0].w, this.simplex[3].w);
							found = SolveSimplex4(tmp1, tmp2, tmp3, tmp4);
							break;
						}
					}
					if (found) {
						return true;
					}
				}
				else {
					return false;
				}
			}
			this.failed = true;
			return false;
		}
		
		public boolean EncloseOrigin() {
			Vector tmp = Stack.alloc(new Vector(3));
			Vector tmp1 = Stack.alloc(new Vector(3));
			Vector tmp2 = Stack.alloc(new Vector(3));

			switch (this.order) {
				// Point
				case 0:
					break;
				// Line
				case 1: {
					Vector ab = Stack.alloc(new Vector(3));
					ab.sub(this.simplex[1].w, this.simplex[0].w);

					Vector[] b = new Vector[] { Stack.alloc(new Vector(3)), Stack.alloc(new Vector(3)), Stack.alloc(new Vector(3)) };
					b[0].set(1f, 0f, 0f);
					b[1].set(0f, 1f, 0f);
					b[2].set(0f, 0f, 1f);
					
					b[0].cross(ab, b[0]);
					b[1].cross(ab, b[1]);
					b[2].cross(ab, b[2]);

					float m[] = new float[] { b[0].lengthSquared(), b[1].lengthSquared(), b[2].lengthSquared() };

					Quaternion tmpQuat = Stack.alloc(Quaternion.class);
					tmp.normalize(ab);
					QuaternionUtil.setRotation(tmpQuat, tmp, cst2Pi / 3f);

					Matrix r = Stack.alloc(new Matrix(3, 3));
					MatrixUtil.setRotation(r, tmpQuat);

					Vector w = Stack.alloc(new Vector(3));
					w.set(b[m[0] > m[1] ? m[0] > m[2] ? 0 : 2 : m[1] > m[2] ? 1 : 2]);

					tmp.normalize(w);
					Support(tmp, this.simplex[4]); r.transform(w);
					tmp.normalize(w);
					Support(tmp, this.simplex[2]); r.transform(w);
					tmp.normalize(w);
					Support(tmp, this.simplex[3]); r.transform(w);
					this.order = 4;
					return (true);
				}
				// Triangle
				case 2: {
					tmp1.sub(this.simplex[1].w, this.simplex[0].w);
					tmp2.sub(this.simplex[2].w, this.simplex[0].w);
					Vector n = Stack.alloc(new Vector(3));
					n.cross(tmp1, tmp2);
					n.normalize();

					Support(n, this.simplex[3]);

					tmp.negate(n);
					Support(tmp, this.simplex[4]);
					this.order = 4;
					return (true);
				}
				// Tetrahedron
				case 3:
					return (true);
				// Hexahedron
				case 4:
					return (true);
			}
			return (false);
		}
		
	}

	////////////////////////////////////////////////////////////////////////////

	private static int[] mod3 = new int[] { 0, 1, 2, 0, 1 };

	private static final int[][] tetrahedron_fidx/*[4][3]*/ = new int[][] {{2,1,0},{3,0,1},{3,1,2},{3,2,0}};
	private static final int[][] tetrahedron_eidx/*[6][4]*/ = new int[][] {{0,0,2,1},{0,1,1,1},{0,2,3,1},{1,0,3,2},{2,0,1,2},{3,0,2,2}};

	private static final int[][] hexahedron_fidx/*[6][3]*/ = new int[][] {{2,0,4},{4,1,2},{1,4,0},{0,3,1},{0,2,3},{1,3,2}};
	private static final int[][] hexahedron_eidx/*[9][4]*/ = new int[][] {{0,0,4,0},{0,1,2,1},{0,2,1,2},{1,1,5,2},{1,0,2,0},{2,2,3,2},{3,1,5,0},{3,0,4,2},{5,1,4,1}};

	public static class Face {
		public final Mkv[] v = new Mkv[3];
		public final Face[] f = new Face[3];
		public final int[] e = new int[3];
		public final Vector n = new Vector();
		public float d;
		public int mark;
		public Face prev;
		public Face next;
	}
	
	protected class EPA {
		//protected final BulletStack stack = BulletStack.get();
		
		public GJK gjk;
		//public btStackAlloc* sa;
		public Face root;
		public int nfaces;
		public int iterations;
		public final Vector[][] features = new Vector[2][3];
		public final Vector[] nearest/*[2]*/ = new Vector[] { new Vector(), new Vector() };
		public final Vector normal = new Vector();
		public float depth;
		public boolean failed;
		
		{
			for (int i=0; i<this.features.length; i++) {
				for (int j=0; j<this.features[i].length; j++) {
					this.features[i][j] = new Vector();
				}
			}
		}

		public EPA(GJK pgjk) {
			this.gjk = pgjk;
			//sa = pgjk->sa;
		}
		
		public Vector GetCoordinates(Face face, Vector out) {
			Vector tmp = Stack.alloc(new Vector(3));
			Vector tmp1 = Stack.alloc(new Vector(3));
			Vector tmp2 = Stack.alloc(new Vector(3));

			Vector o = Stack.alloc(new Vector(3));
			o.scale(-face.d, face.n);

			float[] a = GjkEpaSolver.this.floatArrays.getFixed(3);

			tmp1.sub(face.v[0].w, o);
			tmp2.sub(face.v[1].w, o);
			tmp.cross(tmp1, tmp2);
			a[0] = tmp.length();

			tmp1.sub(face.v[1].w, o);
			tmp2.sub(face.v[2].w, o);
			tmp.cross(tmp1, tmp2);
			a[1] = tmp.length();

			tmp1.sub(face.v[2].w, o);
			tmp2.sub(face.v[0].w, o);
			tmp.cross(tmp1, tmp2);
			a[2] = tmp.length();

			float sm = a[0] + a[1] + a[2];

			out.set(a[1], a[2], a[0]);
			out.scale(1f / (sm > 0f ? sm : 1f));

			GjkEpaSolver.this.floatArrays.release(a);

			return out;
		}
		
		public Face FindBest() {
			Face bf = null;
			if (this.root != null) {
				Face cf = this.root;
				float bd = cstInf;
				do {
					if (cf.d < bd) {
						bd = cf.d;
						bf = cf;
					}
				}
				while (null != (cf = cf.next));
			}
			return bf;
		}

		public boolean Set(Face f, Mkv a, Mkv b, Mkv c) {
			Vector tmp1 = Stack.alloc(new Vector(3));
			Vector tmp2 = Stack.alloc(new Vector(3));
			Vector tmp3 = Stack.alloc(new Vector(3));

			Vector nrm = Stack.alloc(new Vector(3));
			tmp1.sub(b.w, a.w);
			tmp2.sub(c.w, a.w);
			nrm.cross(tmp1, tmp2);

			float len = nrm.length();

			tmp1.cross(a.w, b.w);
			tmp2.cross(b.w, c.w);
			tmp3.cross(c.w, a.w);

			boolean valid = (tmp1.dot(nrm) >= -EPA_inface_eps) &&
					(tmp2.dot(nrm) >= -EPA_inface_eps) &&
					(tmp3.dot(nrm) >= -EPA_inface_eps);

			f.v[0] = a;
			f.v[1] = b;
			f.v[2] = c;
			f.mark = 0;
			f.n.scale(1f / (len > 0f ? len : cstInf), nrm);
			f.d = Math.max(0, -f.n.dot(a.w));
			return valid;
		}
		
		public Face NewFace(Mkv a, Mkv b, Mkv c) {
			//Face pf = new Face();
			Face pf = GjkEpaSolver.this.stackFace.get();
			if (Set(pf, a, b, c)) {
				if (this.root != null) {
					this.root.prev = pf;
				}
				pf.prev = null;
				pf.next = this.root;
				this.root = pf;
				++this.nfaces;
			}
			else {
				pf.prev = pf.next = null;
			}
			return (pf);
		}
	
		public void Detach(Face face) {
			if (face.prev != null || face.next != null) {
				--this.nfaces;
				if (face == this.root) {
					this.root = face.next;
					this.root.prev = null;
				}
				else {
					if (face.next == null) {
						face.prev.next = null;
					}
					else {
						face.prev.next = face.next;
						face.next.prev = face.prev;
					}
				}
				face.prev = face.next = null;
			}
		}

		public void Link(Face f0, int e0, Face f1, int e1) {
			f0.f[e0] = f1; f1.e[e1] = e0;
			f1.f[e1] = f0; f0.e[e0] = e1;
		}

		public Mkv Support(Vector w) {
			//Mkv v = new Mkv();
			Mkv v = GjkEpaSolver.this.stackMkv.get();
			this.gjk.Support(w, v);
			return v;
		}
		
		public int BuildHorizon(int markid, Mkv w, Face f, int e, Face[] cf, Face[] ff) {
			int ne = 0;
			if (f.mark != markid) {
				int e1 = mod3[e + 1];
				if ((f.n.dot(w.w) + f.d) > 0) {
					Face nf = NewFace(f.v[e1], f.v[e], w);
					Link(nf, 0, f, e);
					if (cf[0] != null) {
						Link(cf[0], 1, nf, 2);
					}
					else {
						ff[0] = nf;
					}
					cf[0] = nf;
					ne = 1;
				}
				else {
					int e2 = mod3[e + 2];
					Detach(f);
					f.mark = markid;
					ne += BuildHorizon(markid, w, f.f[e1], f.e[e1], cf, ff);
					ne += BuildHorizon(markid, w, f.f[e2], f.e[e2], cf, ff);
				}
			}
			return (ne);
		}

		public float EvaluatePD() {
			return EvaluatePD(EPA_accuracy);
		}
		
		public float EvaluatePD(float accuracy) {
			pushStack();
			try {
				Vector tmp = Stack.alloc(new Vector(3));

				//btBlock* sablock = sa->beginBlock();
				Face bestface = null;
				int markid = 1;
				this.depth = -cstInf;
				this.normal.set(0f, 0f, 0f);
				this.root = null;
				this.nfaces = 0;
				this.iterations = 0;
				this.failed = false;
				/* Prepare hull		*/
				if (this.gjk.EncloseOrigin()) {
					//const U* pfidx = 0;
					int[][] pfidx_ptr = null;
					int pfidx_index = 0;

					int nfidx = 0;
					//const U* peidx = 0;
					int[][] peidx_ptr = null;
					int peidx_index = 0;

					int neidx = 0;
					Mkv[] basemkv = new Mkv[5];
					Face[] basefaces = new Face[6];
					switch (this.gjk.order) {
						// Tetrahedron
						case 3:
							 {
								//pfidx=(const U*)fidx;
								pfidx_ptr = tetrahedron_fidx;
								pfidx_index = 0;

								nfidx = 4;

								//peidx=(const U*)eidx;
								peidx_ptr = tetrahedron_eidx;
								peidx_index = 0;

								neidx = 6;
							}
							break;
						// Hexahedron
						case 4:
							 {
								//pfidx=(const U*)fidx;
								pfidx_ptr = hexahedron_fidx;
								pfidx_index = 0;

								nfidx = 6;

								//peidx=(const U*)eidx;
								peidx_ptr = hexahedron_eidx;
								peidx_index = 0;

								neidx = 9;
							}
							break;
					}
					int i;

					for (i = 0; i <= this.gjk.order; ++i) {
						basemkv[i] = new Mkv();
						basemkv[i].set(this.gjk.simplex[i]);
					}
					for (i = 0; i < nfidx; ++i, pfidx_index++) {
						basefaces[i] = NewFace(basemkv[pfidx_ptr[pfidx_index][0]], basemkv[pfidx_ptr[pfidx_index][1]], basemkv[pfidx_ptr[pfidx_index][2]]);
					}
					for (i = 0; i < neidx; ++i, peidx_index++) {
						Link(basefaces[peidx_ptr[peidx_index][0]], peidx_ptr[peidx_index][1], basefaces[peidx_ptr[peidx_index][2]], peidx_ptr[peidx_index][3]);
					}
				}
				if (0 == this.nfaces) {
					//sa->endBlock(sablock);
					return (this.depth);
				}
				/* Expand hull		*/
				for (; this.iterations < EPA_maxiterations; ++this.iterations) {
					Face bf = FindBest();
					if (bf != null) {
						tmp.negate(bf.n);
						Mkv w = Support(tmp);
						float d = bf.n.dot(w.w) + bf.d;
						bestface = bf;
						if (d < -accuracy) {
							Face[] cf = new Face[]{null};
							Face[] ff = new Face[]{null};
							int nf = 0;
							Detach(bf);
							bf.mark = ++markid;
							for (int i = 0; i < 3; ++i) {
								nf += BuildHorizon(markid, w, bf.f[i], bf.e[i], cf, ff);
							}
							if (nf <= 2) {
								break;
							}
							Link(cf[0], 1, ff[0], 2);
						}
						else {
							break;
						}
					}
					else {
						break;
					}
				}
				/* Extract contact	*/
				if (bestface != null) {
					Vector b = GetCoordinates(bestface, Stack.alloc(new Vector(3)));
					this.normal.set(bestface.n);
					this.depth = Math.max(0, bestface.d);
					for (int i = 0; i < 2; ++i) {
						float s = i != 0 ? -1f : 1f;
						for (int j = 0; j < 3; ++j) {
							tmp.scale(s, bestface.v[j].r);
							this.gjk.LocalSupport(tmp, i, this.features[i][j]);
						}
					}

					Vector tmp1 = Stack.alloc(new Vector(3));
					Vector tmp2 = Stack.alloc(new Vector(3));
					Vector tmp3 = Stack.alloc(new Vector(3));

					tmp1.scale(b.get(X), this.features[0][0]);
					tmp2.scale(b.get(Y), this.features[0][1]);
					tmp3.scale(b.get(Z), this.features[0][2]);
					VectorUtil.add(this.nearest[0], tmp1, tmp2, tmp3);

					tmp1.scale(b.get(X), this.features[1][0]);
					tmp2.scale(b.get(Y), this.features[1][1]);
					tmp3.scale(b.get(Z), this.features[1][2]);
					VectorUtil.add(this.nearest[1], tmp1, tmp2, tmp3);
				}
				else {
					this.failed = true;
				}
				//sa->endBlock(sablock);
				return (this.depth);
			}
			finally {
				popStack();
			}
		}
		
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	private GJK gjk = new GJK();
	
	public boolean collide(ConvexShape shape0, Transform wtrs0,
			ConvexShape shape1, Transform wtrs1,
			float radialmargin/*,
			btStackAlloc* stackAlloc*/,
			Results results) {
		
		// Initialize
		results.witnesses[0].set(0f, 0f, 0f);
		results.witnesses[1].set(0f, 0f, 0f);
		results.normal.set(0f, 0f, 0f);
		results.depth = 0;
		results.status = ResultsStatus.Separated;
		results.epa_iterations = 0;
		results.gjk_iterations = 0;
		/* Use GJK to locate origin		*/
		this.gjk.init(/*stackAlloc,*/
				wtrs0.basis, wtrs0.origin, shape0,
				wtrs1.basis, wtrs1.origin, shape1,
				radialmargin + EPA_accuracy);
		try {
			boolean collide = this.gjk.SearchOrigin();
			results.gjk_iterations = this.gjk.iterations + 1;
			if (collide) {
				/* Then EPA for penetration depth	*/
				EPA epa = new EPA(this.gjk);
				float pd = epa.EvaluatePD();
				results.epa_iterations = epa.iterations + 1;
				if (pd > 0) {
					results.status = ResultsStatus.Penetrating;
					results.normal.set(epa.normal);
					results.depth = pd;
					results.witnesses[0].set(epa.nearest[0]);
					results.witnesses[1].set(epa.nearest[1]);
					return (true);
				}
				if (epa.failed) {
					results.status = ResultsStatus.EPA_Failed;
				}
			}
			else {
				if (this.gjk.failed) {
					results.status = ResultsStatus.GJK_Failed;
				}
			}
			return (false);
		}
		finally {
			this.gjk.destroy();
		}
	}
	
}
