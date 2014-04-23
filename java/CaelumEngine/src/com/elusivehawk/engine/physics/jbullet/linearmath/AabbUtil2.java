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

import static com.elusivehawk.engine.math.MathConst.*;
import com.elusivehawk.engine.math.Matrix;
import com.elusivehawk.engine.math.Vector;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * Utility functions for axis aligned bounding boxes (AABB).
 * 
 * @author jezek2
 */
public class AabbUtil2
{
	public static void aabbExpand(Vector aabbMin, Vector aabbMax, Vector expansionMin, Vector expansionMax)
	{
		aabbMin.add(expansionMin);
		aabbMax.add(expansionMax);
	}

	public static int outcode(Vector p, Vector halfExtent)
	{
		return (p.get(X) < -halfExtent.get(X) ? 0x01 : 0x0) |
				(p.get(X) > halfExtent.get(X) ? 0x08 : 0x0) |
				(p.get(Y) < -halfExtent.get(Y) ? 0x02 : 0x0) |
				(p.get(Y) > halfExtent.get(Y) ? 0x10 : 0x0) |
				(p.get(Z) < -halfExtent.get(Z) ? 0x4 : 0x0) |
				(p.get(Z) > halfExtent.get(Z) ? 0x20 : 0x0);
	}
	
	public static boolean rayAabb(Vector rayFrom, Vector rayTo, Vector aabbMin, Vector aabbMax, float[] param, Vector normal)
	{
		Vector aabbHalfExtent = Stack.alloc(new Vector(3)),
				aabbCenter = Stack.alloc(new Vector(3)),
				source = Stack.alloc(new Vector(3)),
				target = Stack.alloc(new Vector(3)),
				r = Stack.alloc(new Vector(3)),
				hitNormal = Stack.alloc(new Vector(3));

		aabbHalfExtent.sub(aabbMax, aabbMin);
		aabbHalfExtent.scale(0.5f);

		aabbCenter.add(aabbMax, aabbMin);
		aabbCenter.scale(0.5f);

		source.sub(rayFrom, aabbCenter);
		target.sub(rayTo, aabbCenter);

		int sourceOutcode = outcode(source, aabbHalfExtent);
		int targetOutcode = outcode(target, aabbHalfExtent);
		if ((sourceOutcode & targetOutcode) == 0x0) {
			float lambda_enter = 0f;
			float lambda_exit = param[0];
			r.sub(target, source);

			float normSign = 1f;
			hitNormal.set(0f, 0f, 0f);
			int bit = 1;

			for (int j = 0; j < 2; j++) {
				for (int i = 0; i != 3; ++i) {
					if ((sourceOutcode & bit) != 0) {
						float lambda = (-source.get(i) - aabbHalfExtent.get(i) * normSign) / r.get(i);
						if (lambda_enter <= lambda) {
							lambda_enter = lambda;
							hitNormal.set(0f, 0f, 0f);
							hitNormal.set(i, normSign, false);
						}
					}
					else if ((targetOutcode & bit) != 0) {
						float lambda = (-source.get(i) - aabbHalfExtent.get(i) * normSign) / r.get(i);
						//btSetMin(lambda_exit, lambda);
						lambda_exit = Math.min(lambda_exit, lambda);
					}
					bit <<= 1;
				}
				normSign = -1f;
			}
			if (lambda_enter <= lambda_exit) {
				param[0] = lambda_enter;
				normal.set(hitNormal);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Conservative test for overlap between two AABBs.
	 */
	public static boolean testAabbAgainstAabb2(Vector aabbMin1, Vector aabbMax1, Vector aabbMin2, Vector aabbMax2)
	{
		boolean overlap = true;
		overlap = (aabbMin1.get(X) > aabbMax2.get(X) || aabbMax1.get(X) < aabbMin2.get(X)) ? false : overlap;
		overlap = (aabbMin1.get(Z) > aabbMax2.get(Z) || aabbMax1.get(Z) < aabbMin2.get(Z)) ? false : overlap;
		overlap = (aabbMin1.get(Y) > aabbMax2.get(Y) || aabbMax1.get(Y) < aabbMin2.get(Y)) ? false : overlap;
		return overlap;
	}
	
	/**
	 * Conservative test for overlap between triangle and AABB.
	 */
	public static boolean testTriangleAgainstAabb2(Vector[] vertices, Vector aabbMin, Vector aabbMax)
	{
		Vector p1 = vertices[0];
		Vector p2 = vertices[1];
		Vector p3 = vertices[2];

		if (Math.min(Math.min(p1.get(X), p2.get(X)), p3.get(X)) > aabbMax.get(X)) return false;
		if (Math.max(Math.max(p1.get(X), p2.get(X)), p3.get(X)) < aabbMin.get(X)) return false;

		if (Math.min(Math.min(p1.get(Z), p2.get(Z)), p3.get(Z)) > aabbMax.get(Z)) return false;
		if (Math.max(Math.max(p1.get(Z), p2.get(Z)), p3.get(Z)) < aabbMin.get(Z)) return false;

		if (Math.min(Math.min(p1.get(Y), p2.get(Y)), p3.get(Y)) > aabbMax.get(Y)) return false;
		if (Math.max(Math.max(p1.get(Y), p2.get(Y)), p3.get(Y)) < aabbMin.get(Y)) return false;
		
		return true;
	}

	public static void transformAabb(Vector halfExtents, float margin, Transform t, Vector aabbMinOut, Vector aabbMaxOut)
	{
		Vector halfExtentsWithMargin = Stack.alloc(new Vector(3));
		halfExtentsWithMargin.set(X, halfExtents.get(X) + margin, false);
		halfExtentsWithMargin.set(Y, halfExtents.get(Y) + margin, false);
		halfExtentsWithMargin.set(Z, halfExtents.get(Z) + margin, true);

		Matrix abs_b = Stack.alloc(t.basis);
		MatrixUtil.absolute(abs_b);

		Vector tmp = Stack.alloc(new Vector(3));

		Vector center = Stack.alloc(t.origin);
		Vector extent = Stack.alloc(new Vector(3));
		abs_b.getRow(0, tmp);
		extent.set(X, tmp.dot(halfExtentsWithMargin));
		abs_b.getRow(1, tmp);
		extent.set(Y, tmp.dot(halfExtentsWithMargin));
		abs_b.getRow(2, tmp);
		extent.set(Z, tmp.dot(halfExtentsWithMargin));

		aabbMinOut.sub(center, extent);
		aabbMaxOut.add(center, extent);
	}

	public static void transformAabb(Vector localAabbMin, Vector localAabbMax, float margin, Transform trans, Vector aabbMinOut, Vector aabbMaxOut) {
		assert (localAabbMin.get(X) <= localAabbMax.get(X));
		assert (localAabbMin.get(Y) <= localAabbMax.get(Y));
		assert (localAabbMin.get(Z) <= localAabbMax.get(Z));

		Vector localHalfExtents = Stack.alloc(new Vector(3));
		localHalfExtents.sub(localAabbMax, localAabbMin);
		localHalfExtents.scale(0.5f);
		
		for (int c = 0; c < 3; c++)
		{
			localHalfExtents.addAll(margin);
			
		}
		
		Vector localCenter = Stack.alloc(new Vector(3));
		localCenter.add(localAabbMax, localAabbMin);
		localCenter.scale(0.5f);

		Matrix abs_b = Stack.alloc(trans.basis);
		MatrixUtil.absolute(abs_b);

		Vector center = Stack.alloc(localCenter);
		trans.transform(center);

		Vector extent = Stack.alloc(new Vector(3));
		Vector tmp = Stack.alloc(new Vector(3));

		abs_b.getRow(0, tmp);
		extent.set(X, tmp.dot(localHalfExtents));
		abs_b.getRow(1, tmp);
		extent.set(Y, tmp.dot(localHalfExtents));
		abs_b.getRow(2, tmp);
		extent.set(Z, tmp.dot(localHalfExtents));

		aabbMinOut.sub(center, extent);
		aabbMaxOut.add(center, extent);
	}

}
