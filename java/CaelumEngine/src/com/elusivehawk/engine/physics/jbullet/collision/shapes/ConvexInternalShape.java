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
import com.elusivehawk.engine.physics.jbullet.BulletGlobals;
import com.elusivehawk.engine.physics.jbullet.linearmath.MatrixUtil;
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import com.elusivehawk.engine.physics.jbullet.linearmath.VectorUtil;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * ConvexInternalShape is an internal base class, shared by most convex shape implementations.
 * 
 * @author jezek2
 */
public abstract class ConvexInternalShape extends ConvexShape
{

	// local scaling. collisionMargin is not scaled !
	protected final Vector localScaling = new Vector(1f, 1f, 1f);
	protected final Vector implicitShapeDimensions = new Vector(3);
	protected float collisionMargin = BulletGlobals.CONVEX_DISTANCE_MARGIN;

	/**
	 * getAabb's default implementation is brute force, expected derived classes to implement a fast dedicated version.
	 */
	@Override
	public void getAabb(Transform t, Vector aabbMin, Vector aabbMax) {
		getAabbSlow(t, aabbMin, aabbMax);
	}
	
	@Override
	public void getAabbSlow(Transform trans, Vector minAabb, Vector maxAabb) {
		float margin = getMargin();
		Vector vec = Stack.alloc(new Vector(3)),
				tmp1 = Stack.alloc(new Vector(3)),
				tmp2 = Stack.alloc(new Vector(3));
		
		for (int i=0;i<3;i++)
		{
			vec.setAll(0f);
			vec.set(i, 1f, false);

			MatrixUtil.transposeTransform(tmp1, vec, trans.basis);
			localGetSupportingVertex(tmp1, tmp2);
			
			trans.transform(tmp2);

			maxAabb.set(i, tmp2.get(i) + margin, i == 2);

			vec.set(i, -1f, false);

			MatrixUtil.transposeTransform(tmp1, vec, trans.basis);
			localGetSupportingVertex(tmp1, tmp2);
			trans.transform(tmp2);

			minAabb.set(i, tmp2.get(i) - margin, false);
		}
	}

	@Override
	public Vector localGetSupportingVertex(Vector vec, Vector out) {
		Vector supVertex = localGetSupportingVertexWithoutMargin(vec, out);

		if (getMargin() != 0f) {
			Vector vecnorm = Stack.alloc(vec);
			if (vecnorm.length() < (BulletGlobals.FLT_EPSILON * BulletGlobals.FLT_EPSILON)) {
				vecnorm.setAll(-1f);
			}
			vecnorm.normalize();
			supVertex.scaleAdd(getMargin(), vecnorm, supVertex);
		}
		return out;
	}
	
	@Override
	public void setLocalScaling(Vector scaling) {
		localScaling.absolute(scaling);
	}
	
	@Override
	public Vector getLocalScaling(Vector out) {
		out.set(localScaling);
		return out;
	}

	@Override
	public float getMargin() {
		return collisionMargin;
	}

	@Override
	public void setMargin(float margin) {
		this.collisionMargin = margin;
	}

	@Override
	public int getNumPreferredPenetrationDirections() {
		return 0;
	}

	@Override
	public void getPreferredPenetrationDirection(int index, Vector penetrationVector) {
		throw new InternalError();
	}
	
}
