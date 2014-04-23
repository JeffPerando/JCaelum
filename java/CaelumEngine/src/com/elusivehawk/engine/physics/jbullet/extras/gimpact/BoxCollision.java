/*
 * Java port of Bullet (c) 2008 Martin Dvorak <jezek2@advel.cz>
 *
 * This source file is part of GIMPACT Library.
 *
 * For the latest info, see http://gimpact.sourceforge.net/
 *
 * Copyright (c) 2007 Francisco Leon Najera. C.C. 80087371.
 * email: projectileman@yahoo.com
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

package com.elusivehawk.engine.physics.jbullet.extras.gimpact;

import com.elusivehawk.engine.math.Matrix;
import com.elusivehawk.engine.math.Vector;
import static com.elusivehawk.engine.math.MathConst.*;
import com.elusivehawk.engine.physics.jbullet.BulletGlobals;
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 *
 * @author jezek2
 */
class BoxCollision {
	
	public static final float BOX_PLANE_EPSILON = 0.000001f;

	public static boolean BT_GREATER(float x, float y) {
		return Math.abs(x) > y;
	}

	public static float BT_MAX3(float a, float b, float c) {
		return Math.max(a, Math.max(b, c));
	}

	public static float BT_MIN3(float a, float b, float c) {
		return Math.min(a, Math.min(b, c));
	}
	
	public static boolean TEST_CROSS_EDGE_BOX_MCR(Vector edge, Vector absolute_edge, Vector pointa, Vector pointb, Vector _extend, int i_dir_0, int i_dir_1, int i_comp_0, int i_comp_1) {
		float dir0 = -edge.get(i_dir_0);
		float dir1 = edge.get(i_dir_1);
		float pmin = pointa.get(i_comp_0) * dir0 + pointa.get(i_comp_1) * dir1;
		float pmax = pointb.get(i_comp_0) * dir0 + pointb.get(i_comp_1) * dir1;
		if (pmin > pmax) {
			//BT_SWAP_NUMBERS(pmin,pmax);
			pmin = pmin + pmax;
			pmax = pmin - pmax;
			pmin = pmin - pmax;
		}
		float abs_dir0 = absolute_edge.get(i_dir_0);
		float abs_dir1 = absolute_edge.get(i_dir_1);
		float rad = _extend.get(i_comp_0) * abs_dir0 + _extend.get(i_comp_1) * abs_dir1;
		if (pmin > rad || -rad > pmax) {
			return false;
		}
		return true;
	}

	public static boolean TEST_CROSS_EDGE_BOX_X_AXIS_MCR(Vector edge, Vector absolute_edge, Vector pointa, Vector pointb, Vector _extend) {
		return TEST_CROSS_EDGE_BOX_MCR(edge, absolute_edge, pointa, pointb, _extend, 2, 1, 1, 2);
	}

	public static boolean TEST_CROSS_EDGE_BOX_Y_AXIS_MCR(Vector edge, Vector absolute_edge, Vector pointa, Vector pointb, Vector _extend) {
		return TEST_CROSS_EDGE_BOX_MCR(edge, absolute_edge, pointa, pointb, _extend, 0, 2, 2, 0);
	}

	public static boolean TEST_CROSS_EDGE_BOX_Z_AXIS_MCR(Vector edge, Vector absolute_edge, Vector pointa, Vector pointb, Vector _extend) {
		return TEST_CROSS_EDGE_BOX_MCR(edge, absolute_edge, pointa, pointb, _extend, 1, 0, 0, 1);
	}
	
	/**
	 * Returns the dot product between a vec3f and the col of a matrix.
	 */
	public static float bt_mat3_dot_col(Matrix mat, Vector vec3, int colindex) {
		return vec3.get(X) * mat.get(0, colindex) + vec3.get(Y) * mat.get(1, colindex) + vec3.get(Z) * mat.get(2, colindex);
	}

	/**
	 * Compairison of transformation objects.
	 */
	public static boolean compareTransformsEqual(Transform t1, Transform t2) {
		return t1.equals(t2);
	}
	
	////////////////////////////////////////////////////////////////////////////

	public static class BoxBoxTransformCache {
		public final Vector T1to0 = new Vector(3); // Transforms translation of model1 to model 0
		public final Matrix R1to0 = new Matrix(3, 3); // Transforms Rotation of model1 to model 0, equal  to R0' * R1
		public final Matrix AR = new Matrix(3, 3);    // Absolute value of m_R1to0
		
		public void set(BoxBoxTransformCache cache) {
			throw new UnsupportedOperationException();
		}
		
		public void calc_absolute_matrix() {
			//static const btVector3 vepsi(1e-6f,1e-6f,1e-6f);
			//m_AR[0] = vepsi + m_R1to0[0].absolute();
			//m_AR[1] = vepsi + m_R1to0[1].absolute();
			//m_AR[2] = vepsi + m_R1to0[2].absolute();

			for (int i=0; i<3; i++) {
				for (int j=0; j<3; j++) {
					AR.set(i, j, 1e-6f + Math.abs(R1to0.get(i, j)));
				}
			}
		}

		/**
		 * Calc the transformation relative  1 to 0. Inverts matrics by transposing.
		 */
		public void calc_from_homogenic(Transform trans0, Transform trans1) {
			Transform temp_trans = Stack.alloc(Transform.class);
			temp_trans.inverse(trans0);
			temp_trans.mul(trans1);

			T1to0.set(temp_trans.origin);
			R1to0.set(temp_trans.basis);

			calc_absolute_matrix();
		}
		
		/**
		 * Calcs the full invertion of the matrices. Useful for scaling matrices.
		 */
		public void calc_from_full_invert(Transform trans0, Transform trans1) {
			R1to0.invert(trans0.basis);
			T1to0.negate(trans0.origin);
			R1to0.transform(T1to0);

			Vector tmp = Stack.alloc(new Vector(3));
			tmp.set(trans1.origin);
			R1to0.transform(tmp);
			T1to0.add(tmp);

			R1to0.mul(trans1.basis);

			calc_absolute_matrix();
		}
		
		public Vector transform(Vector point, Vector out)
		{
			if (point == out)
			{
				point = Stack.alloc(point);
				
			}
			
			Vector tmp = Stack.alloc(new Vector(3));
			
			for (int c = 0; c < 3; c++)
			{
				R1to0.getRow(c, tmp);
				out.set(c, tmp.dot(point) + T1to0.get(c), c == 2);
				
			}
			
			/*R1to0.getRow(0, tmp);
			out.x = tmp.dot(point) + T1to0.get(X);
			R1to0.getRow(1, tmp);
			out.y = tmp.dot(point) + T1to0.get(Y);
			R1to0.getRow(2, tmp);
			out.z = tmp.dot(point) + T1to0.get(Z);*/
			return out;
		}
		
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	public static class AABB
	{
		public final Vector min = new Vector(3),
				max = new Vector(3);
		
		public AABB() {
		}

		public AABB(Vector V1, Vector V2, Vector V3) {
			calc_from_triangle(V1, V2, V3);
		}

		public AABB(Vector V1, Vector V2, Vector V3, float margin) {
			calc_from_triangle_margin(V1, V2, V3, margin);
		}

		public AABB(AABB other) {
			set(other);
		}

		public AABB(AABB other, float margin)
		{
			this(other);
			
			increment_margin(margin);
			/*min.x -= margin;
			min.y -= margin;
			min.z -= margin;
			max.x += margin;
			max.y += margin;
			max.z += margin;*/
		}

		public void init(Vector V1, Vector V2, Vector V3, float margin)
		{
			calc_from_triangle_margin(V1, V2, V3, margin);
			
		}

		public void set(AABB other)
		{
			min.set(other.min);
			max.set(other.max);
			
		}

		public void invalidate()
		{
			min.setAll(BulletGlobals.SIMD_INFINITY);
			max.setAll(-BulletGlobals.SIMD_INFINITY);
			
		}

		public void increment_margin(float margin)
		{
			this.increment_margin(this.min, this.max, margin);
		}
		
		public void increment_margin(Vector minsrc, Vector maxsrc, float margin)
		{
			for (int c = 0; c < 3; c++)
			{
				min.set(c, minsrc.get(c) - margin, c == 2);
				max.set(c, maxsrc.get(c) + margin, c == 2);
				
			}
			/*min.x -= margin;
			min.y -= margin;
			min.z -= margin;
			max.x += margin;
			max.y += margin;
			max.z += margin;*/
		}

		public void copy_with_margin(AABB other, float margin)
		{
			increment_margin(other.min, other.max, margin);
			
		}
		
		public void calc_from_triangle(Vector V1, Vector V2, Vector V3)
		{
			for (int c = 0; c < 3; c++)
			{
				min.set(c, BT_MIN3(V1.get(c), V2.get(c), V3.get(c)), c == 2);
				max.set(c, BT_MAX3(V1.get(c), V2.get(c), V3.get(c)), c == 2);
				
			}
			
			/*min.x = BT_MIN3(V1.x, V2.x, V3.x);
			min.y = BT_MIN3(V1.y, V2.y, V3.y);
			min.z = BT_MIN3(V1.z, V2.z, V3.z);

			max.x = BT_MAX3(V1.x, V2.x, V3.x);
			max.y = BT_MAX3(V1.y, V2.y, V3.y);
			max.z = BT_MAX3(V1.z, V2.z, V3.z);*/
			
		}

		public void calc_from_triangle_margin(Vector V1, Vector V2, Vector V3, float margin)
		{
			calc_from_triangle(V1, V2, V3);
			increment_margin(margin);
			
		}
		
		/**
		 * Apply a transform to an AABB.
		 */
		public void appy_transform(Transform trans)
		{
			Vector tmp = Stack.alloc(new Vector(3));

			Vector center = Stack.alloc(new Vector(3));
			center.add(max, min);
			center.scale(0.5f);

			Vector extends_ = Stack.alloc(new Vector(3));
			extends_.sub(max, center);

			// Compute new center
			trans.transform(center);

			Vector textends = Stack.alloc(new Vector(3));
			
			for (int c = 0; c < 3; c++)
			{
				trans.basis.getRow(c, tmp);
				tmp.absolute();
				textends.set(c, extends_.dot(tmp), c == 2);
				
			}
			
			/*trans.basis.getRow(0, tmp);
			tmp.absolute();
			textends.x = extends_.dot(tmp);

			trans.basis.getRow(1, tmp);
			tmp.absolute();
			textends.y = extends_.dot(tmp);

			trans.basis.getRow(2, tmp);
			tmp.absolute();
			textends.z = extends_.dot(tmp);*/
			
			min.sub(center, textends);
			max.add(center, textends);
		}

		/**
		 * Apply a transform to an AABB.
		 */
		public void appy_transform_trans_cache(BoxBoxTransformCache trans)
		{
			Vector tmp = Stack.alloc(new Vector(3));

			Vector center = Stack.alloc(new Vector(3));
			center.add(max, min);
			center.scale(0.5f);

			Vector extends_ = Stack.alloc(new Vector(3));
			extends_.sub(max, center);

			// Compute new center
			trans.transform(center, center);

			Vector textends = Stack.alloc(new Vector(3));

			for (int c = 0; c < 3; c++)
			{
				trans.R1to0.getRow(c, tmp);
				tmp.absolute();
				textends.set(c, extends_.dot(tmp), c == 2);
				
			}
			
			/*trans.R1to0.getRow(0, tmp);
			tmp.absolute();
			textends.x = extends_.dot(tmp);

			trans.R1to0.getRow(1, tmp);
			tmp.absolute();
			textends.y = extends_.dot(tmp);

			trans.R1to0.getRow(2, tmp);
			tmp.absolute();
			textends.z = extends_.dot(tmp);*/
			
			min.sub(center, textends);
			max.add(center, textends);
		}
		
		/**
		 * Merges a Box.
		 */
		public void merge(AABB box)
		{
			for (int c = 0; c < 3; c++)
			{
				min.set(c, Math.min(min.get(c), box.min.get(c)), c == 2);
				max.set(c, Math.max(max.get(c), box.max.get(c)), c == 2);
				
			}
			
			/*min.x = Math.min(min.x, box.min.x);
			min.y = Math.min(min.y, box.min.y);
			min.z = Math.min(min.z, box.min.z);

			max.x = Math.max(max.x, box.max.x);
			max.y = Math.max(max.y, box.max.y);
			max.z = Math.max(max.z, box.max.z);*/
		}

		/**
		 * Merges a point.
		 */
		public void merge_point(Vector point)
		{
			for (int c = 0; c < 3; c++)
			{
				min.set(c, Math.min(min.get(c), point.get(c)), c == 2);
				max.set(c, Math.max(max.get(c), point.get(c)), c == 2);
				
			}
			
			/*min.x = Math.min(min.x, point.x);
			min.y = Math.min(min.y, point.y);
			min.z = Math.min(min.z, point.z);

			max.x = Math.max(max.x, point.x);
			max.y = Math.max(max.y, point.y);
			max.z = Math.max(max.z, point.z);*/
			
		}
		
		/**
		 * Gets the extend and center.
		 */
		public void get_center_extend(Vector center, Vector extend) {
			center.add(max, min);
			center.scale(0.5f);

			extend.sub(max, center);
		}
		
		/**
		 * Finds the intersecting box between this box and the other.
		 */
		public void find_intersection(AABB other, AABB intersection)
		{
			for (int c = 0; c < 3; c++)
			{
				intersection.min.set(c, Math.max(other.min.get(c), min.get(c)), c == 2);
				intersection.max.set(c, Math.min(other.max.get(c), max.get(c)), c == 2);
				
			}
			/*intersection.min.x = Math.max(other.min.x, min.x);
			intersection.min.y = Math.max(other.min.y, min.y);
			intersection.min.z = Math.max(other.min.z, min.z);

			intersection.max.x = Math.min(other.max.x, max.x);
			intersection.max.y = Math.min(other.max.y, max.y);
			intersection.max.z = Math.min(other.max.z, max.z);*/
			
		}

		public boolean has_collision(AABB other)
		{
			for (int c = 0; c < 3; c++)
			{
				if (min.get(c) > other.max.get(c) || max.get(c) < other.min.get(c))
				{
					return false;
				}
				
			}
			/*if (min.x > other.max.x ||
			    max.x < other.min.x ||
			    min.y > other.max.y ||
			    max.y < other.min.y ||
			    min.z > other.max.z ||
			    max.z < other.min.z) {
				return false;
			}*/
			return true;
		}
		
		/**
		 * Finds the Ray intersection parameter.
		 * 
		 * @param aabb     aligned box
		 * @param vorigin  a vec3f with the origin of the ray
		 * @param vdir     a vec3f with the direction of the ray
		 */
		public boolean collide_ray(Vector vorigin, Vector vdir)
		{
			Vector extents = Stack.alloc(new Vector(3)),
					center = Stack.alloc(new Vector(3));
			get_center_extend(center, extents);
			
			float[] d = new float[3];
			
			for (int c = 0; c < 3; c++)
			{
				d[c] = vorigin.get(c) - center.get(c);
				if (BT_GREATER(d[c], extents.get(c)) && d[c] * vdir.get(c) >= 0.0f) return false;
				
			}
			
			float f = vdir.get(Y) * d[Z] - vdir.get(Z) * d[Y];
			if (Math.abs(f) > extents.get(Y) * Math.abs(vdir.get(Z)) + extents.get(Z) * Math.abs(vdir.get(Y))) return false;
			
			f = vdir.get(Z) * d[X] - vdir.get(X) * d[Z];
			if (Math.abs(f) > extents.get(X) * Math.abs(vdir.get(Z)) + extents.get(Z) * Math.abs(vdir.get(X))) return false;
			
			f = vdir.get(X) * d[Y] - vdir.get(Y) * d[X];
			if (Math.abs(f) > extents.get(X) * Math.abs(vdir.get(Y)) + extents.get(Y) * Math.abs(vdir.get(X))) return false;
			
			return true;
		}
	
		public void projection_interval(Vector direction, float[] vmin, float[] vmax)
		{
			Vector tmp = Stack.alloc(new Vector(3));
			
			Vector center = Stack.alloc(new Vector(3));
			Vector extend = Stack.alloc(new Vector(3));
			get_center_extend(center, extend);
			
			float _fOrigin = direction.dot(center);
			tmp.absolute(direction);
			float _fMaximumExtent = extend.dot(tmp);
			vmin[0] = _fOrigin - _fMaximumExtent;
			vmax[0] = _fOrigin + _fMaximumExtent;
		}
		
		public PlaneIntersectionType plane_classify(Vector plane)
		{
			Vector tmp = Stack.alloc(new Vector(3));

			float[] _fmin = new float[1], _fmax = new float[1];
			tmp.set(plane);
			projection_interval(tmp, _fmin, _fmax);

			if (plane.get(W) > _fmax[0] + BOX_PLANE_EPSILON)
			{
				return PlaneIntersectionType.BACK_PLANE; // 0
			}

			if (plane.get(W) + BOX_PLANE_EPSILON >= _fmin[0])
			{
				return PlaneIntersectionType.COLLIDE_PLANE; //1
			}
			
			return PlaneIntersectionType.FRONT_PLANE; //2
		}
		
		public boolean overlapping_trans_conservative(AABB box, Transform trans1_to_0)
		{
			AABB tbox = Stack.alloc(box);
			tbox.appy_transform(trans1_to_0);
			return has_collision(tbox);
		}

		public boolean overlapping_trans_conservative2(AABB box, BoxBoxTransformCache trans1_to_0)
		{
			AABB tbox = Stack.alloc(box);
			tbox.appy_transform_trans_cache(trans1_to_0);
			return has_collision(tbox);
		}
		
		/**
		 * transcache is the transformation cache from box to this AABB.
		 */
		public boolean overlapping_trans_cache(AABB box,
				BoxBoxTransformCache transcache, boolean fulltest)
		{
			Vector tmp = Stack.alloc(new Vector(3));
			
			// Taken from OPCODE
			Vector ea = Stack.alloc(new Vector(3)), eb = Stack
					.alloc(new Vector(3)); // extends
			Vector ca = Stack.alloc(new Vector(3)), cb = Stack
					.alloc(new Vector(3)); // extends
			get_center_extend(ca, ea);
			box.get_center_extend(cb, eb);
			
			Vector T = Stack.alloc(new Vector(3));
			float t, t2;
			
			// Class I : A's basis vectors
			for (int i = 0; i < 3; i++)
			{
				transcache.R1to0.getRow(i, tmp);
				T.set(i, tmp.dot(cb) + transcache.T1to0.get(i) - ca.get(i));
				
				transcache.AR.getRow(i, tmp);
				t = tmp.dot(eb) + ea.get(i);
				if (BT_GREATER(T.get(i), t))
				{
					return false;
				}
			}
			// Class II : B's basis vectors
			for (int i = 0; i < 3; i++)
			{
				t = bt_mat3_dot_col(transcache.R1to0, T, i);
				t2 = bt_mat3_dot_col(transcache.AR, ea, i) + eb.get(i);
				if (BT_GREATER(t, t2))
				{
					return false;
				}
			}
			// Class III : 9 cross products
			if (fulltest)
			{
				int m, n, o, p, q, r;
				for (int i = 0; i < 3; i++)
				{
					m = (i + 1) % 3;
					n = (i + 2) % 3;
					o = (i == 0) ? 1 : 0;
					p = (i == 2) ? 1 : 2;
					for (int j = 0; j < 3; j++)
					{
						q = j == 2 ? 1 : 2;
						r = j == 0 ? 1 : 0;
						t = T.get(n) * transcache.R1to0.get(m, j) - T.get(m)
								* transcache.R1to0.get(n, j);
						t2 = ea.get(o) * transcache.AR.get(p, j) + ea.get(p)
								* transcache.AR.get(o, j) + eb.get(r)
								* transcache.AR.get(i, q) + eb.get(q)
								* transcache.AR.get(i, r);
						if (BT_GREATER(t, t2))
						{
							return false;
						}
					}
				}
			}
			return true;
		}
		
		/**
		 * Simple test for planes.
		 */
		public boolean collide_plane(Vector plane)
		{
			PlaneIntersectionType classify = plane_classify(plane);
			return (classify == PlaneIntersectionType.COLLIDE_PLANE);
		}
		
		/**
		 * Test for a triangle, with edges.
		 */
		public boolean collide_triangle_exact(Vector p1, Vector p2, Vector p3, Vector triangle_plane)
		{
			if (!collide_plane(triangle_plane))
			{
				return false;
			}
			Vector center = Stack.alloc(new Vector(3)),
					extends_ = Stack.alloc(new Vector(3));
			
			get_center_extend(center, extends_);
			
			Vector v1 = Stack.alloc(new Vector(3));
			v1.sub(p1, center);
			Vector v2 = Stack.alloc(new Vector(3));
			v2.sub(p2, center);
			Vector v3 = Stack.alloc(new Vector(3));
			v3.sub(p3, center);
			
			// First axis
			Vector diff = Stack.alloc(new Vector(3));
			diff.sub(v2, v1);
			Vector abs_diff = Stack.alloc(new Vector(3));
			abs_diff.absolute(diff);
			
			// Test With X axis
			TEST_CROSS_EDGE_BOX_X_AXIS_MCR(diff, abs_diff, v1, v3, extends_);
			// Test With Y axis
			TEST_CROSS_EDGE_BOX_Y_AXIS_MCR(diff, abs_diff, v1, v3, extends_);
			// Test With Z axis
			TEST_CROSS_EDGE_BOX_Z_AXIS_MCR(diff, abs_diff, v1, v3, extends_);
			
			diff.sub(v3, v2);
			abs_diff.absolute(diff);
			
			// Test With X axis
			TEST_CROSS_EDGE_BOX_X_AXIS_MCR(diff, abs_diff, v2, v1, extends_);
			// Test With Y axis
			TEST_CROSS_EDGE_BOX_Y_AXIS_MCR(diff, abs_diff, v2, v1, extends_);
			// Test With Z axis
			TEST_CROSS_EDGE_BOX_Z_AXIS_MCR(diff, abs_diff, v2, v1, extends_);
			
			diff.sub(v1, v3);
			abs_diff.absolute(diff);
			
			// Test With X axis
			TEST_CROSS_EDGE_BOX_X_AXIS_MCR(diff, abs_diff, v3, v2, extends_);
			// Test With Y axis
			TEST_CROSS_EDGE_BOX_Y_AXIS_MCR(diff, abs_diff, v3, v2, extends_);
			// Test With Z axis
			TEST_CROSS_EDGE_BOX_Z_AXIS_MCR(diff, abs_diff, v3, v2, extends_);
			
			return true;
		}

	}
	
}
