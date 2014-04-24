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

import com.elusivehawk.engine.math.Vector;
import com.elusivehawk.engine.physics.jbullet.collision.shapes.TriangleCallback;
import com.elusivehawk.engine.physics.jbullet.linearmath.VectorUtil;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 *
 * @author jezek2
 */
public abstract class TriangleRaycastCallback extends TriangleCallback {
	
	//protected final BulletStack stack = BulletStack.get();

	public final Vector from = new Vector();
	public final Vector to = new Vector();

	public float hitFraction;

	public TriangleRaycastCallback(Vector from2, Vector to2) {
		this.from.set(from2);
		this.to.set(to2);
		this.hitFraction = 1f;
	}
	
	@Override
	public void processTriangle(Vector[] triangle, int partId, int triangleIndex) {
		Vector vert0 = triangle[0];
		Vector vert1 = triangle[1];
		Vector vert2 = triangle[2];

		Vector v10 = Stack.alloc(new Vector(3));
		v10.sub(vert1, vert0);

		Vector v20 = Stack.alloc(new Vector(3));
		v20.sub(vert2, vert0);

		Vector triangleNormal = Stack.alloc(new Vector(3));
		triangleNormal.cross(v10, v20);

		float dist = vert0.dot(triangleNormal);
		float dist_a = triangleNormal.dot(from);
		dist_a -= dist;
		float dist_b = triangleNormal.dot(to);
		dist_b -= dist;

		if (dist_a * dist_b >= 0f) {
			return; // same sign
		}

		float proj_length = dist_a - dist_b;
		float distance = (dist_a) / (proj_length);
		// Now we have the intersection point on the plane, we'll see if it's inside the triangle
		// Add an epsilon as a tolerance for the raycast,
		// in case the ray hits exacly on the edge of the triangle.
		// It must be scaled for the triangle size.

		if (distance < hitFraction) {
			float edge_tolerance = triangleNormal.lengthSquared();
			edge_tolerance *= -0.0001f;
			Vector point = new Vector(3);
			VectorUtil.setInterpolate(point, from, to, distance);
			{
				Vector v0p = Stack.alloc(new Vector(3));
				v0p.sub(vert0, point);
				Vector v1p = Stack.alloc(new Vector(3));
				v1p.sub(vert1, point);
				Vector cp0 = Stack.alloc(new Vector(3));
				cp0.cross(v0p, v1p);

				if (cp0.dot(triangleNormal) >= edge_tolerance) {
					Vector v2p = Stack.alloc(new Vector(3));
					v2p.sub(vert2, point);
					Vector cp1 = Stack.alloc(new Vector(3));
					cp1.cross(v1p, v2p);
					if (cp1.dot(triangleNormal) >= edge_tolerance) {
						Vector cp2 = Stack.alloc(new Vector(3));
						cp2.cross(v2p, v0p);

						if (cp2.dot(triangleNormal) >= edge_tolerance) {

							if (dist_a > 0f) {
								hitFraction = reportHit(triangleNormal, distance, partId, triangleIndex);
							}
							else {
								Vector tmp = Stack.alloc(new Vector(3));
								tmp.negate(triangleNormal);
								hitFraction = reportHit(tmp, distance, partId, triangleIndex);
							}
						}
					}
				}
			}
		}
	}

	public abstract float reportHit(Vector hitNormalLocal, float hitFraction, int partId, int triangleIndex );

}
