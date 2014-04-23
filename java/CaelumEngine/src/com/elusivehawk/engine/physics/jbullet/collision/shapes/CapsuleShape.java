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

import static com.elusivehawk.engine.math.MathConst.*;
import com.elusivehawk.engine.math.MathHelper;
import com.elusivehawk.engine.math.Matrix;
import com.elusivehawk.engine.math.Vector;
import com.elusivehawk.engine.physics.jbullet.BulletGlobals;
import com.elusivehawk.engine.physics.jbullet.collision.broadphase.BroadphaseNativeType;
import com.elusivehawk.engine.physics.jbullet.linearmath.MatrixUtil;
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import com.elusivehawk.engine.physics.jbullet.linearmath.VectorUtil;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * CapsuleShape represents a capsule around the Y axis, there is also the
 * {@link CapsuleShapeX} aligned around the X axis and {@link CapsuleShapeZ} around
 * the Z axis.<p>
 *
 * The total height is height+2*radius, so the height is just the height between
 * the center of each "sphere" of the capsule caps.<p>
 *
 * CapsuleShape is a convex hull of two spheres. The {@link MultiSphereShape} is
 * a more general collision shape that takes the convex hull of multiple sphere,
 * so it can also represent a capsule when just using two spheres.
 * 
 * @author jezek2
 */
public class CapsuleShape extends ConvexInternalShape {
	
	protected int upAxis;

	// only used for CapsuleShapeZ and CapsuleShapeX subclasses.
	CapsuleShape() {
	}
	
	public CapsuleShape(float radius, float height) {
		upAxis = 1;
		implicitShapeDimensions.set(radius, 0.5f * height, radius);
	}

	@Override
	public Vector localGetSupportingVertexWithoutMargin(Vector vec0, Vector out)
	{
		Vector supVec = out;
		supVec.setAll(0f);

		float maxDot = -1e30f;

		Vector vec = Stack.alloc(vec0);
		float lenSqr = MathHelper.lengthSquared(vec);
		if (lenSqr < 0.0001f) {
			vec.setAll(0f);
			vec.set(0, 1f);
		}
		else {
			float rlen = 1f / (float) Math.sqrt(lenSqr);
			vec.scale(rlen);
		}

		Vector vtx = Stack.alloc(new Vector(3));
		float newDot;

		float radius = getRadius();

		Vector tmp1 = Stack.alloc(new Vector(3)),
				tmp2 = Stack.alloc(new Vector(3)),
				pos = Stack.alloc(new Vector(3));

		{
			pos.set(0f, 0f, 0f);
			pos.set(getUpAxis(), getHalfHeight(), false);
			
			VectorUtil.mul(tmp1, vec, localScaling);
			tmp1.scale(radius);
			tmp2.scale(getMargin(), vec);
			vtx.add(pos, tmp1);
			vtx.sub(tmp2);
			newDot = vec.dot(vtx);
			if (newDot > maxDot) {
				maxDot = newDot;
				supVec.set(vtx);
			}
		}
		{
			pos.set(0f, 0f, 0f);
			pos.set(getUpAxis(), -getHalfHeight());
			
			VectorUtil.mul(tmp1, vec, localScaling);
			tmp1.scale(radius);
			tmp2.scale(getMargin(), vec);
			vtx.add(pos, tmp1);
			vtx.sub(tmp2);
			newDot = vec.dot(vtx);
			if (newDot > maxDot) {
				maxDot = newDot;
				supVec.set(vtx);
			}
		}

		return out;
	}

	@Override
	public void batchedUnitVectorGetSupportingVertexWithoutMargin(Vector[] vectors, Vector[] supportVerticesOut, int numVectors) {
		// TODO: implement
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void calculateLocalInertia(float mass, Vector inertia)
	{
		// as an approximation, take the inertia of the box that bounds the spheres

		Transform ident = Stack.alloc(Transform.class);
		ident.setIdentity();

		float radius = getRadius();

		Vector halfExtents = Stack.alloc(new Vector(3));
		halfExtents.setAll(radius);
		halfExtents.set(getUpAxis(), radius + getHalfHeight(), false);

		float margin = BulletGlobals.CONVEX_DISTANCE_MARGIN;

		float lx = 2f * (halfExtents.get(X) + margin);
		float ly = 2f * (halfExtents.get(Y) + margin);
		float lz = 2f * (halfExtents.get(Z) + margin);
		float x2 = lx * lx;
		float y2 = ly * ly;
		float z2 = lz * lz;
		float scaledmass = mass * 0.08333333f;
		
		inertia.set(X, scaledmass * (y2 + z2), false);
		inertia.set(Y, scaledmass * (x2 + z2), false);
		inertia.set(Z, scaledmass * (x2 + y2), false);
	}

	@Override
	public BroadphaseNativeType getShapeType() {
		return BroadphaseNativeType.CAPSULE_SHAPE_PROXYTYPE;
	}
	
	@Override
	public void getAabb(Transform t, Vector aabbMin, Vector aabbMax) {
		Vector tmp = Stack.alloc(new Vector(3));

		Vector halfExtents = Stack.alloc(new Vector(3));
		halfExtents.setAll(getRadius());
		halfExtents.set(upAxis, getRadius() + getHalfHeight(), false);
		
		halfExtents.addAll(getMargin());
		
		Matrix abs_b = Stack.alloc(new Matrix(3, 3));
		abs_b.set(t.basis);
		MatrixUtil.absolute(abs_b);

		Vector center = t.origin;
		Vector extent = Stack.alloc(new Vector(3));
		
		for (int c = 0; c < 3; c++)
		{
			abs_b.getRow(c, tmp);
			extent.set(c, tmp.dot(halfExtents), c == 2);
			
		}
		/*abs_b.getRow(0, tmp);
		extent.x = tmp.dot(halfExtents);
		abs_b.getRow(1, tmp);
		extent.y = tmp.dot(halfExtents);
		abs_b.getRow(2, tmp);
		extent.z = tmp.dot(halfExtents);*/

		aabbMin.sub(center, extent);
		aabbMax.add(center, extent);
	}

	@Override
	public String getName() {
		return "CapsuleShape";
	}
	
	public int getUpAxis() {
		return upAxis;
	}
	
	public float getRadius() {
		int radiusAxis = (upAxis + 2) % 3;
		return implicitShapeDimensions.get(radiusAxis);
	}

	public float getHalfHeight() {
		return implicitShapeDimensions.get(upAxis);
	}

}
