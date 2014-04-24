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

package com.elusivehawk.engine.physics.jbullet.collision.shapes;

import com.elusivehawk.engine.math.Vector;
import com.elusivehawk.engine.physics.jbullet.collision.broadphase.BroadphaseNativeType;
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import com.elusivehawk.engine.physics.jbullet.linearmath.TransformUtil;
import com.elusivehawk.engine.physics.jbullet.linearmath.VectorUtil;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * StaticPlaneShape simulates an infinite non-moving (static) collision plane.
 * 
 * @author jezek2
 */
public class StaticPlaneShape extends ConcaveShape {

	protected final Vector localAabbMin = new Vector();
	protected final Vector localAabbMax = new Vector();
	
	protected final Vector planeNormal = new Vector();
	protected float planeConstant;
	protected final Vector localScaling = new Vector(0f, 0f, 0f);

	public StaticPlaneShape(Vector planeNormal, float planeConstant) {
		this.planeNormal.normalize(planeNormal);
		this.planeConstant = planeConstant;
	}

	public Vector getPlaneNormal(Vector out) {
		out.set(planeNormal);
		return out;
	}

	public float getPlaneConstant() {
		return planeConstant;
	}
	
	@Override
	public void processAllTriangles(TriangleCallback callback, Vector aabbMin, Vector aabbMax) {
		Vector tmp = Stack.alloc(new Vector(3));
		Vector tmp1 = Stack.alloc(new Vector(3));
		Vector tmp2 = Stack.alloc(new Vector(3));

		Vector halfExtents = Stack.alloc(new Vector(3));
		halfExtents.sub(aabbMax, aabbMin);
		halfExtents.scale(0.5f);

		float radius = halfExtents.length();
		Vector center = Stack.alloc(new Vector(3));
		center.add(aabbMax, aabbMin);
		center.scale(0.5f);

		// this is where the triangles are generated, given AABB and plane equation (normal/constant)

		Vector tangentDir0 = Stack.alloc(new Vector(3)), tangentDir1 = Stack.alloc(new Vector(3));

		// tangentDir0/tangentDir1 can be precalculated
		TransformUtil.planeSpace1(planeNormal, tangentDir0, tangentDir1);

		Vector supVertex0 = Stack.alloc(new Vector(3)), supVertex1 = Stack.alloc(new Vector(3));

		Vector projectedCenter = Stack.alloc(new Vector(3));
		tmp.scale(planeNormal.dot(center) - planeConstant, planeNormal);
		projectedCenter.sub(center, tmp);

		Vector[] triangle = new Vector[] { Stack.alloc(new Vector(3)), Stack.alloc(new Vector(3)), Stack.alloc(new Vector(3)) };

		tmp1.scale(radius, tangentDir0);
		tmp2.scale(radius, tangentDir1);
		VectorUtil.add(triangle[0], projectedCenter, tmp1, tmp2);

		tmp1.scale(radius, tangentDir0);
		tmp2.scale(radius, tangentDir1);
		tmp.sub(tmp1, tmp2);
		VectorUtil.add(triangle[1], projectedCenter, tmp);

		tmp1.scale(radius, tangentDir0);
		tmp2.scale(radius, tangentDir1);
		tmp.sub(tmp1, tmp2);
		triangle[2].sub(projectedCenter, tmp);

		callback.processTriangle(triangle, 0, 0);

		tmp1.scale(radius, tangentDir0);
		tmp2.scale(radius, tangentDir1);
		tmp.sub(tmp1, tmp2);
		triangle[0].sub(projectedCenter, tmp);

		tmp1.scale(radius, tangentDir0);
		tmp2.scale(radius, tangentDir1);
		tmp.add(tmp1, tmp2);
		triangle[1].sub(projectedCenter, tmp);

		tmp1.scale(radius, tangentDir0);
		tmp2.scale(radius, tangentDir1);
		VectorUtil.add(triangle[2], projectedCenter, tmp1, tmp2);

		callback.processTriangle(triangle, 0, 1);
	}

	@Override
	public void getAabb(Transform t, Vector aabbMin, Vector aabbMax) {
		aabbMin.set(-1e30f, -1e30f, -1e30f);
		aabbMax.set(1e30f, 1e30f, 1e30f);
	}

	@Override
	public BroadphaseNativeType getShapeType() {
		return BroadphaseNativeType.STATIC_PLANE_PROXYTYPE;
	}

	@Override
	public void setLocalScaling(Vector scaling) {
		localScaling.set(scaling);
	}

	@Override
	public Vector getLocalScaling(Vector out) {
		out.set(localScaling);
		return out;
	}

	@Override
	public void calculateLocalInertia(float mass, Vector inertia) {
		//moving concave objects not supported
		inertia.set(0f, 0f, 0f);
	}

	@Override
	public String getName() {
		return "STATICPLANE";
	}

}
