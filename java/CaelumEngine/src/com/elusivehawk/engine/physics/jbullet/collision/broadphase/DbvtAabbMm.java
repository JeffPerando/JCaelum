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

// Dbvt implementation by Nathanael Presson

package com.elusivehawk.engine.physics.jbullet.collision.broadphase;

import static com.elusivehawk.util.math.MathConst.*;
import com.elusivehawk.engine.physics.jbullet.linearmath.MatrixUtil;
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import com.elusivehawk.engine.physics.jbullet.linearmath.VectorUtil;
import com.elusivehawk.util.math.Vector;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 *
 * @author jezek2
 */
public class DbvtAabbMm {

	private final Vector mi = new Vector(3);
	private final Vector mx = new Vector(3);

	public DbvtAabbMm() {
	}

	public DbvtAabbMm(DbvtAabbMm o) {
		set(o);
	}
	
	public void set(DbvtAabbMm o) {
		mi.set(o.mi);
		mx.set(o.mx);
	}
	
	public static void swap(DbvtAabbMm p1, DbvtAabbMm p2) {
		Vector tmp = Stack.alloc(new Vector(3));
		
		tmp.set(p1.mi);
		p1.mi.set(p2.mi);
		p2.mi.set(tmp);

		tmp.set(p1.mx);
		p1.mx.set(p2.mx);
		p2.mx.set(tmp);
	}

	public Vector Center(Vector out) {
		out.add(mi, mx);
		out.scale(0.5f);
		return out;
	}
	
	public Vector Lengths(Vector out) {
		out.sub(mx, mi);
		return out;
	}
	
	public Vector Extents(Vector out) {
		out.sub(mx, mi);
		out.scale(0.5f);
		return out;
	}
	
	public Vector Mins() {
		return mi;
	}

	public Vector Maxs() {
		return mx;
	}
	
	public static DbvtAabbMm FromCE(Vector c, Vector e, DbvtAabbMm out) {
		DbvtAabbMm box = out;
		box.mi.sub(c, e);
		box.mx.add(c, e);
		return box;
	}

	public static DbvtAabbMm FromCR(Vector c, float r, DbvtAabbMm out) {
		Vector tmp = Stack.alloc(new Vector(3));
		tmp.set(r, r, r);
		return FromCE(c, tmp, out);
	}

	public static DbvtAabbMm FromMM(Vector mi, Vector mx, DbvtAabbMm out) {
		DbvtAabbMm box = out;
		box.mi.set(mi);
		box.mx.set(mx);
		return box;
	}
	
	//public static  DbvtAabbMm	FromPoints( btVector3* pts,int n);
	//public static  DbvtAabbMm	FromPoints( btVector3** ppts,int n);
	
	public void Expand(Vector e) {
		mi.sub(e);
		mx.add(e);
	}

	public void SignedExpand(Vector e)
	{
		for (int c = 0; c < 3; c++)
		{
			if (e.get(c) > 0)
			{
				mx.add(c, e.get(c), c == 2);
				
			}
			else
			{
				mi.add(c, e.get(c), c == 2);
				
			}
			
		}
		/*if (e.get(X) > 0) {
			mx.get(X) += e.get(X);
		}
		else {
			mi.get(X) += e.get(X);
		}
		
		if (e.get(Y) > 0) {
			mx.get(Y) += e.get(Y);
		}
		else {
			mi.get(Y) += e.get(Y);
		}
		
		if (e.get(Z) > 0) {
			mx.get(Z) += e.get(Z);
		}
		else {
			mi.get(Z) += e.get(Z);
		}*/
	}

	public boolean Contain(DbvtAabbMm a) {
		return ((mi.get(X) <= a.mi.get(X)) &&
		        (mi.get(Y) <= a.mi.get(Y)) &&
		        (mi.get(Z) <= a.mi.get(Z)) &&
		        (mx.get(X) >= a.mx.get(X)) &&
		        (mx.get(Y) >= a.mx.get(Y)) &&
		        (mx.get(Z) >= a.mx.get(Z)));
	}

	public int Classify(Vector n, float o, int s) {
		Vector pi = Stack.alloc(new Vector(3));
		Vector px = Stack.alloc(new Vector(3));

		switch (s) {
			case (0 + 0 + 0):
				px.set(mi.get(X), mi.get(Y), mi.get(Z));
				pi.set(mx.get(X), mx.get(Y), mx.get(Z));
				break;
			case (1 + 0 + 0):
				px.set(mx.get(X), mi.get(Y), mi.get(Z));
				pi.set(mi.get(X), mx.get(Y), mx.get(Z));
				break;
			case (0 + 2 + 0):
				px.set(mi.get(X), mx.get(Y), mi.get(Z));
				pi.set(mx.get(X), mi.get(Y), mx.get(Z));
				break;
			case (1 + 2 + 0):
				px.set(mx.get(X), mx.get(Y), mi.get(Z));
				pi.set(mi.get(X), mi.get(Y), mx.get(Z));
				break;
			case (0 + 0 + 4):
				px.set(mi.get(X), mi.get(Y), mx.get(Z));
				pi.set(mx.get(X), mx.get(Y), mi.get(Z));
				break;
			case (1 + 0 + 4):
				px.set(mx.get(X), mi.get(Y), mx.get(Z));
				pi.set(mi.get(X), mx.get(Y), mi.get(Z));
				break;
			case (0 + 2 + 4):
				px.set(mi.get(X), mx.get(Y), mx.get(Z));
				pi.set(mx.get(X), mi.get(Y), mi.get(Z));
				break;
			case (1 + 2 + 4):
				px.set(mx.get(X), mx.get(Y), mx.get(Z));
				pi.set(mi.get(X), mi.get(Y), mi.get(Z));
				break;
		}
		
		if ((n.dot(px) + o) < 0) {
			return -1;
		}
		if ((n.dot(pi) + o) >= 0) {
			return +1;
		}
		return 0;
	}

	public float ProjectMinimum(Vector v, int signs) {
		Vector[] b = new Vector[]{ mx, mi };
		Vector p = Stack.alloc(new Vector(3));
		p.set(b[(signs >> 0) & 1].get(X),
		      b[(signs >> 1) & 1].get(Y),
		      b[(signs >> 2) & 1].get(Z));
		return p.dot(v);
	}
	 
	public static boolean Intersect(DbvtAabbMm a, DbvtAabbMm b) {
		return ((a.mi.get(X) <= b.mx.get(X)) &&
		        (a.mx.get(X) >= b.mi.get(X)) &&
		        (a.mi.get(Y) <= b.mx.get(Y)) &&
		        (a.mx.get(Y) >= b.mi.get(Y)) &&
		        (a.mi.get(Z) <= b.mx.get(Z)) &&
		        (a.mx.get(Z) >= b.mi.get(Z)));
	}

	public static boolean Intersect(DbvtAabbMm a, DbvtAabbMm b, Transform xform) {
		Vector d0 = Stack.alloc(new Vector(3));
		Vector d1 = Stack.alloc(new Vector(3));
		Vector tmp = Stack.alloc(new Vector(3));

		// JAVA NOTE: check
		b.Center(d0);
		xform.transform(d0);
		d0.sub(a.Center(tmp));

		MatrixUtil.transposeTransform(d1, d0, xform.basis);

		float[] s0 = new float[] { 0, 0 };
		float[] s1 = new float[2];
		s1[0] = xform.origin.dot(d0);
		s1[1] = s1[0];

		a.AddSpan(d0, s0, 0, s0, 1);
		b.AddSpan(d1, s1, 0, s1, 1);
		if (s0[0] > (s1[1])) {
			return false;
		}
		if (s0[1] < (s1[0])) {
			return false;
		}
		return true;
	}

	public static boolean Intersect(DbvtAabbMm a, Vector b) {
		return ((b.get(X) >= a.mi.get(X)) &&
		        (b.get(Y) >= a.mi.get(Y)) &&
		        (b.get(Z) >= a.mi.get(Z)) &&
		        (b.get(X) <= a.mx.get(X)) &&
		        (b.get(Y) <= a.mx.get(Y)) &&
		        (b.get(Z) <= a.mx.get(Z)));
	}

	public static boolean Intersect(DbvtAabbMm a, Vector org, Vector invdir, int[] signs) {
		Vector[] bounds = new Vector[]{a.mi, a.mx};
		float txmin = (bounds[signs[0]].get(X) - org.get(X)) * invdir.get(X);
		float txmax = (bounds[1 - signs[0]].get(X) - org.get(X)) * invdir.get(X);
		float tymin = (bounds[signs[1]].get(Y) - org.get(Y)) * invdir.get(Y);
		float tymax = (bounds[1 - signs[1]].get(Y) - org.get(Y)) * invdir.get(Y);
		if ((txmin > tymax) || (tymin > txmax)) {
			return false;
		}
		
		if (tymin > txmin) {
			txmin = tymin;
		}
		if (tymax < txmax) {
			txmax = tymax;
		}
		float tzmin = (bounds[signs[2]].get(Z) - org.get(Z)) * invdir.get(Z);
		float tzmax = (bounds[1 - signs[2]].get(Z) - org.get(Z)) * invdir.get(Z);
		if ((txmin > tzmax) || (tzmin > txmax)) {
			return false;
		}
		
		if (tzmin > txmin) {
			txmin = tzmin;
		}
		if (tzmax < txmax) {
			txmax = tzmax;
		}
		return (txmax > 0);
	}

	public static float Proximity(DbvtAabbMm a, DbvtAabbMm b) {
		Vector d = Stack.alloc(new Vector(3));
		Vector tmp = Stack.alloc(new Vector(3));

		d.add(a.mi, a.mx);
		tmp.add(b.mi, b.mx);
		d.sub(tmp);
		return Math.abs(d.get(X)) + Math.abs(d.get(Y)) + Math.abs(d.get(Z));
	}

	public static void Merge(DbvtAabbMm a, DbvtAabbMm b, DbvtAabbMm r) {
		for (int i=0; i<3; i++) {
			if (a.mi.get(i) < b.mi.get(i)) {
				r.mi.set(i, a.mi.get(i), i == 2);
			}
			else {
				r.mi.set(i, b.mi.get(i), i == 2);
			}
			
			if (a.mx.get(i) > b.mx.get(i)) {
				r.mx.set(i, a.mx.get(i), i == 2);
			}
			else {
				r.mx.set(i, b.mx.get(i), i == 2);
			}
		}
	}

	public static boolean NotEqual(DbvtAabbMm a, DbvtAabbMm b) {
		return ((a.mi.get(X) != b.mi.get(X)) ||
		        (a.mi.get(Y) != b.mi.get(Y)) ||
		        (a.mi.get(Z) != b.mi.get(Z)) ||
		        (a.mx.get(X) != b.mx.get(X)) ||
		        (a.mx.get(Y) != b.mx.get(Y)) ||
		        (a.mx.get(Z) != b.mx.get(Z)));
	}
	
	private void AddSpan(Vector d, float[] smi, int smi_idx, float[] smx, int smx_idx) {
		for (int i=0; i<3; i++) {
			if (d.get(i) < 0) {
				smi[smi_idx] += mx.get(i) * d.get(i);
				smx[smx_idx] += mi.get(i) * d.get(i);
			}
			else {
				smi[smi_idx] += mi.get(i) * d.get(i);
				smx[smx_idx] += mx.get(i) * d.get(i);
			}
		}
	}
	
}
