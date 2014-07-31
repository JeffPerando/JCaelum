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

package com.elusivehawk.engine.physics.jbullet.linearmath;

import static com.elusivehawk.util.math.MathConst.*;
import com.elusivehawk.engine.physics.jbullet.BulletGlobals;
import com.elusivehawk.engine.physics.jbullet.util.ArrayPool;
import com.elusivehawk.util.math.MathConst;
import com.elusivehawk.util.math.Matrix;
import com.elusivehawk.util.math.Quaternion;
import com.elusivehawk.util.math.Vector;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * Utility functions for matrices.
 * 
 * @author jezek2
 */
public class MatrixUtil
{
	public static void scale(Matrix dest, Matrix mat, Vector s)
	{
		for (int x = 0; x < 3; x++)
		{
			for (int y = 0; y < 3; y++)
			{
				dest.set(x, y, mat.get(x, y) * s.get(y));
				
			}
			
		}
		
		/*dest.m00 = mat.m00 * s.x;   dest.m01 = mat.m01 * s.y;   dest.m02 = mat.m02 * s.z;
		dest.m10 = mat.m10 * s.x;   dest.m11 = mat.m11 * s.y;   dest.m12 = mat.m12 * s.z;
		dest.m20 = mat.m20 * s.x;   dest.m21 = mat.m21 * s.y;   dest.m22 = mat.m22 * s.z;*/
	}
	
	public static void absolute(Matrix mat)
	{
		for (int x = 0; x < 3; x++)
		{
			for (int y = 0; y < 3; y++)
			{
				mat.set(x, y, Math.abs(mat.get(x, y)));
				
			}
			
		}
		
		/*mat.m00 = Math.abs(mat.m00);
		mat.m01 = Math.abs(mat.m01);
		mat.m02 = Math.abs(mat.m02);
		mat.m10 = Math.abs(mat.m10);
		mat.m11 = Math.abs(mat.m11);
		mat.m12 = Math.abs(mat.m12);
		mat.m20 = Math.abs(mat.m20);
		mat.m21 = Math.abs(mat.m21);
		mat.m22 = Math.abs(mat.m22);*/
	}
	
	public static void setFromOpenGLSubMatrix(Matrix mat, float[] m)
	{
		mat.set(0, 0, m[0]); mat.set(0, 1, m[4]); mat.set(0, 2, m[8]);
		mat.set(1, 0, m[1]); mat.set(1, 1, m[5]); mat.set(1, 2, m[9]);
		mat.set(2, 0, m[2]); mat.set(1, 1, m[6]); mat.set(2, 2, m[10]);
		
	}
	
	public static void getOpenGLSubMatrix(Matrix mat, float[] m)
	{
		m[0] = mat.get(0, 0);
		m[1] = mat.get(1, 0);
		m[2] = mat.get(2, 0);
		m[3] = 0f;
		m[4] = mat.get(0, 1);
		m[5] = mat.get(1, 1);
		m[6] = mat.get(2, 1);
		m[7] = 0f;
		m[8] = mat.get(0, 2);
		m[9] = mat.get(1, 2);
		m[10] = mat.get(2, 2);
		m[11] = 0f;
		
	}
	
	/**
	 * Sets rotation matrix from euler angles. The euler angles are applied in ZYX
	 * order. This means a vector is first rotated about X then Y and then Z axis.
	 */
	public static void setEulerZYX(Matrix mat, Vector euler)
	{
		setEulerZYX(mat, euler.get(X), euler.get(Y), euler.get(Z));
		
	}
	
	/**
	 * Sets rotation matrix from euler angles. The euler angles are applied in ZYX
	 * order. This means a vector is first rotated about X then Y and then Z axis.
	 */
	public static void setEulerZYX(Matrix mat, float eulerX, float eulerY, float eulerZ)
	{
		float cx = (float)Math.cos(eulerX);
		float cy = (float)Math.cos(eulerY);
		float cz = (float)Math.cos(eulerZ);
		float sx = (float)Math.sin(eulerX);
		float sy = (float)Math.sin(eulerY);
		float sz = (float)Math.sin(eulerZ);
		
		float cxz = cx * cz;
		float cxsz = cx * sz;
		float sxcz = sx * cz;
		float sxz = sx * sz;
		
		mat.setRow(0, cy * cz, sy * sxcz - cxsz, sy * cxz + sxz);
		mat.setRow(1, cy * sz, sy * sxz + cxz, sy * cxsz - sxcz);
		mat.setRow(2, -sy, cy * sx, cy * cx);
		
	}
	
	private static float tdotx(Matrix mat, Vector vec)
	{
		return mat.get(0, 0) * vec.get(X) + mat.get(1, 0) * vec.get(Y) + mat.get(2, 0) * vec.get(Z);
	}
	
	private static float tdoty(Matrix mat, Vector vec)
	{
		return mat.get(0, 1) * vec.get(X) + mat.get(1, 1) * vec.get(Y) + mat.get(2, 1) * vec.get(Z);
	}
	
	private static float tdotz(Matrix mat, Vector vec)
	{
		return mat.get(0, 2) * vec.get(X) + mat.get(1, 2) * vec.get(Y) + mat.get(2, 2) * vec.get(Z);
	}
	
	public static void transposeTransform(Vector dest, Vector vec, Matrix mat)
	{
		float x = tdotx(mat, vec);
		float y = tdoty(mat, vec);
		float z = tdotz(mat, vec);
		
		dest.set(X, x, false);
		dest.set(Y, y, false);
		dest.set(Z, z);
		
	}
	
	public static void setRotation(Matrix dest, Quaternion q)
	{
		float x = q.get(MathConst.X),
				y = q.get(MathConst.Y),
				z = q.get(MathConst.Z),
				w = q.get(MathConst.W);
		
		float d = x * x + y * y + z * z + w * w;
		assert (d != 0f);
		float s = 2f / d;
		float xs = x * s, ys = y * s, zs = z * s;
		float wx = w * xs, wy = w * ys, wz = w * zs;
		float xx = x * xs, xy = x * ys, xz = x * zs;
		float yy = y * ys, yz = y * zs, zz = z * zs;
		dest.set(0, 0, 1f - (yy + zz));
		dest.set(0, 1, xy - wz);
		dest.set(0, 2, xz + wy);
		dest.set(1, 0, xy + wz);
		dest.set(1, 1, 1f - (xx + zz));
		dest.set(1, 2, yz - wx);
		dest.set(2, 0, xz - wy);
		dest.set(2, 1, yz + wx);
		dest.set(2, 2, 1f - (xx + yy));
		
	}
	
	public static void getRotation(Matrix mat, Quaternion dest)
	{
		ArrayPool<float[]> floatArrays = ArrayPool.get(float.class);
		
		float trace = mat.get(0, 0) + mat.get(1, 1) + mat.get(2, 2);
		float[] temp = floatArrays.getFixed(4);

		if (trace > 0f)
		{
			float s = (float)Math.sqrt(trace + 1f);
			temp[3] = (s * 0.5f);
			s = 0.5f / s;
			
			temp[0] = ((mat.get(2, 1) - mat.get(1, 2)) * s);
			temp[1] = ((mat.get(0, 2) - mat.get(2, 0)) * s);
			temp[2] = ((mat.get(1, 0) - mat.get(0, 1)) * s);
			
		}
		else
		{
			int i = mat.get(0, 0) < mat.get(1, 1) ? (mat.get(1, 1) < mat.get(2, 2) ? 2 : 1) : (mat.get(0, 0) < mat.get(2, 2) ? 2 : 0);
			int j = (i + 1) % 3;
			int k = (i + 2) % 3;
			
			float s = (float) Math.sqrt(mat.get(i, i) - mat.get(j, j) - mat.get(k, k) + 1f);
			temp[i] = s * 0.5f;
			s = 0.5f / s;
			
			temp[3] = (mat.get(k, j) - mat.get(j, k)) * s;
			temp[j] = (mat.get(j, i) + mat.get(i, j)) * s;
			temp[k] = (mat.get(k, i) + mat.get(i, k)) * s;
			
		}
		
		for (int c = 0; c < 4; c++)
		{
			dest.set(c, temp[c], false);
			
		}
		
		dest.onChanged();
		
		floatArrays.release(temp);
		
	}

	private static float cofac(Matrix mat, int r1, int c1, int r2, int c2)
	{
		return mat.get(r1, c1) * mat.get(r2, c2) - mat.get(r1, c2) * mat.get(r2, c1);
	}
	
	public static void invert(Matrix mat)
	{
		float co_x = cofac(mat, 1, 1, 2, 2);
		float co_y = cofac(mat, 1, 2, 2, 0);
		float co_z = cofac(mat, 1, 0, 2, 1);
		
		float det = mat.get(0, 0) * co_x + mat.get(0, 1) * co_y + mat.get(0, 2) * co_z;
		
		assert (det != 0f);
		
		float s = 1f / det;
		float m00 = co_x * s;
		float m01 = cofac(mat, 0, 2, 2, 1) * s;
		float m02 = cofac(mat, 0, 1, 1, 2) * s;
		float m10 = co_y * s;
		float m11 = cofac(mat, 0, 0, 2, 2) * s;
		float m12 = cofac(mat, 0, 2, 1, 0) * s;
		float m20 = co_z * s;
		float m21 = cofac(mat, 0, 1, 2, 0) * s;
		float m22 = cofac(mat, 0, 0, 1, 1) * s;
		
		mat.set(0, 0, m00);
		mat.set(0, 1, m01);
		mat.set(0, 2, m02);
		
		mat.set(1, 0, m10);
		mat.set(1, 1, m11);
		mat.set(1, 2, m12);
		
		mat.set(2, 0, m20);
		mat.set(2, 1, m21);
		mat.set(2, 2, m22);
		
	}
	
	/**
	 * Diagonalizes this matrix by the Jacobi method. rot stores the rotation
	 * from the coordinate system in which the matrix is diagonal to the original
	 * coordinate system, i.e., old_this = rot * new_this * rot^T. The iteration
	 * stops when all off-diagonal elements are less than the threshold multiplied
	 * by the sum of the absolute values of the diagonal, or when maxSteps have
	 * been executed. Note that this matrix is assumed to be symmetric.
	 */
	// JAVA NOTE: diagonalize method from 2.71
	public static void diagonalize(Matrix mat, Matrix rot, float threshold, int maxSteps)
	{
		Vector row = Stack.alloc(new Vector(3));
		
		rot.setIdentity();
		for (int step = maxSteps; step > 0; step--)
		{
			// find off-diagonal element [p][q] with largest magnitude
			int p = 0;
			int q = 1;
			int r = 2;
			float max = Math.abs(mat.get(0, 1));
			float v = Math.abs(mat.get(0, 2));
			
			if (v > max)
			{
				q = 2;
				r = 1;
				max = v;
				
			}
			
			v = Math.abs(mat.get(1, 2));
			
			if (v > max)
			{
				p = 1;
				q = 2;
				r = 0;
				max = v;
				
			}

			float t = threshold * (Math.abs(mat.get(0, 0)) + Math.abs(mat.get(1, 1)) + Math.abs(mat.get(2, 2)));
			
			if (max <= t)
			{
				if (max <= BulletGlobals.SIMD_EPSILON * t)
				{
					return;
				}
				
				step = 1;
				
			}

			// compute Jacobi rotation J which leads to a zero for element [p][q]
			float mpq = mat.get(p, q);
			float theta = (mat.get(q, q) - mat.get(p, p)) / (2 * mpq);
			float theta2 = theta * theta;
			float cos;
			float sin;
			if ((theta2 * theta2) < (10f / BulletGlobals.SIMD_EPSILON))
			{
				t = (theta >= 0f) ? 1f / (theta + (float) Math.sqrt(1f + theta2)) : 1f / (theta - (float) Math.sqrt(1f + theta2));
				cos = 1f / (float) Math.sqrt(1f + t * t);
				sin = cos * t;
				
			}
			else
			{
				// approximation for large theta-value, i.e., a nearly diagonal matrix
				t = 1 / (theta * (2 + 0.5f / theta2));
				cos = 1 - 0.5f * t * t;
				sin = cos * t;
				
			}
			
			// apply rotation to matrix (this = J^T * this * J)
			mat.set(p, q, 0f);
			mat.set(q, p, 0f);
			mat.set(p, p, mat.get(p, p) - t * mpq);
			mat.set(q, q, mat.get(q, q) + t * mpq);
			
			float mrp = mat.get(r, p);
			float mrq = mat.get(r, q);
			
			mat.set(r, p, cos * mrp - sin * mrq);
			mat.set(p, r, cos * mrp - sin * mrq);
			mat.set(r, q, cos * mrq + sin * mrp);
			mat.set(q, r, cos * mrq + sin * mrp);
			
			// apply rotation to rot (rot = rot * J)
			for (int i=0; i<3; i++)
			{
				rot.getRow(i, row);
				
				mrp = row.get(p);
				mrq = row.get(q);
				row.set(p, cos * mrp - sin * mrq, false);
				row.set(q, cos * mrq + sin * mrp, false);
				rot.setRow(i, row);
				
			}
			
		}
		
	}
	
}
