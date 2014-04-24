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

import static com.elusivehawk.engine.math.MathConst.X;
import com.elusivehawk.engine.math.Vector;
import com.elusivehawk.engine.physics.jbullet.BulletGlobals;
import com.elusivehawk.engine.physics.jbullet.collision.broadphase.BroadphaseNativeType;
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * CylinderShape class implements a cylinder shape primitive, centered around
 * the origin. Its central axis aligned with the Y axis. {@link CylinderShapeX} is aligned with the X axis and {@link CylinderShapeZ} around the Z axis.
 * 
 * @author jezek2
 */
public class CylinderShape extends BoxShape
{
	protected int upAxis;
	
	@SuppressWarnings("unqualified-field-access")
	public CylinderShape(Vector halfExtents)
	{
		super(halfExtents);
		upAxis = 1;
		recalcLocalAabb();
	}
	
	protected CylinderShape(Vector halfExtents, boolean unused)
	{
		super(halfExtents);
	}
	
	@Override
	public void getAabb(Transform t, Vector aabbMin, Vector aabbMax)
	{
		_PolyhedralConvexShape_getAabb(t, aabbMin, aabbMax);
	}
	
	protected Vector cylinderLocalSupportX(Vector halfExtents, Vector v,
			Vector out)
	{
		return cylinderLocalSupport(halfExtents, v, 0, 1, 0, 2, out);
	}
	
	protected Vector cylinderLocalSupportY(Vector halfExtents, Vector v,
			Vector out)
	{
		return cylinderLocalSupport(halfExtents, v, 1, 0, 1, 2, out);
	}
	
	protected Vector cylinderLocalSupportZ(Vector halfExtents, Vector v,
			Vector out)
	{
		return cylinderLocalSupport(halfExtents, v, 2, 0, 2, 1, out);
	}
	
	private Vector cylinderLocalSupport(Vector halfExtents, Vector v,
			int cylinderUpAxis, int XX, int YY, int ZZ, Vector out)
	{
		// mapping depends on how cylinder local orientation is
		// extents of the cylinder is: X,Y is for radius, and Z for height
		
		float radius = halfExtents.get(XX);
		float halfHeight = halfExtents.get(cylinderUpAxis);
		
		float d;
		
		float s = (float)Math.sqrt(v.get(XX) * v.get(XX) + v.get(ZZ)
				* v.get(ZZ));
		if (s != 0f)
		{
			d = radius / s;
			out.set(XX, v.get(XX) * d, false);
			out.set(YY, v.get(YY) < 0f ? -halfHeight : halfHeight, false);
			out.set(ZZ, v.get(ZZ) * d);
			
			return out;
		}
		
		out.set(XX, radius, false);
		out.set(YY, v.get(YY) < 0f ? -halfHeight : halfHeight, false);
		out.set(ZZ, 0f);
		
		return out;
	}
	
	@Override
	public Vector localGetSupportingVertexWithoutMargin(Vector vec, Vector out)
	{
		return cylinderLocalSupportY(
				getHalfExtentsWithoutMargin(Stack.alloc(new Vector(3))), vec,
				out);
	}
	
	@Override
	public void batchedUnitVectorGetSupportingVertexWithoutMargin(
			Vector[] vectors, Vector[] supportVerticesOut, int numVectors)
	{
		for (int i = 0; i < numVectors; i++)
		{
			cylinderLocalSupportY(
					getHalfExtentsWithoutMargin(Stack.alloc(new Vector(3))),
					vectors[i], supportVerticesOut[i]);
		}
	}
	
	@Override
	public Vector localGetSupportingVertex(Vector vec, Vector out)
	{
		Vector supVertex = out;
		localGetSupportingVertexWithoutMargin(vec, supVertex);
		
		if (getMargin() != 0f)
		{
			Vector vecnorm = Stack.alloc(vec);
			if (vecnorm.lengthSquared() < (BulletGlobals.SIMD_EPSILON * BulletGlobals.SIMD_EPSILON))
			{
				vecnorm.set(-1f, -1f, -1f);
			}
			vecnorm.normalize();
			supVertex.scaleAdd(getMargin(), vecnorm, supVertex);
		}
		return out;
	}
	
	@Override
	public BroadphaseNativeType getShapeType()
	{
		return BroadphaseNativeType.CYLINDER_SHAPE_PROXYTYPE;
	}
	
	public int getUpAxis()
	{
		return this.upAxis;
	}
	
	public float getRadius()
	{
		return getHalfExtentsWithMargin(Stack.alloc(new Vector(3))).get(X);
	}
	
	@Override
	public String getName()
	{
		return "CylinderY";
	}

}
