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
import static com.elusivehawk.engine.math.MathConst.Y;
import static com.elusivehawk.engine.math.MathConst.Z;
import com.elusivehawk.engine.math.Vector;
import com.elusivehawk.engine.physics.jbullet.collision.broadphase.BroadphaseNativeType;
import com.elusivehawk.engine.physics.jbullet.collision.dispatch.CollisionObject;
import com.elusivehawk.engine.physics.jbullet.dynamics.RigidBody;
import com.elusivehawk.engine.physics.jbullet.linearmath.AabbUtil2;
import com.elusivehawk.engine.physics.jbullet.linearmath.ScalarUtil;
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import com.elusivehawk.engine.physics.jbullet.linearmath.VectorUtil;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * BoxShape is a box primitive around the origin, its sides axis aligned with length
 * specified by half extents, in local shape coordinates. When used as part of a
 * {@link CollisionObject} or {@link RigidBody} it will be an oriented box in world space.
 *
 * @author jezek2
 */
public class BoxShape extends PolyhedralConvexShape
{
	@SuppressWarnings("unqualified-field-access")
	public BoxShape(Vector boxHalfExtents)
	{
		Vector margin = new Vector(getMargin(), getMargin(), getMargin());
		VectorUtil.mul(implicitShapeDimensions, boxHalfExtents, localScaling);
		implicitShapeDimensions.sub(margin);
	}
	
	public Vector getHalfExtentsWithMargin(Vector out)
	{
		Vector halfExtents = getHalfExtentsWithoutMargin(out);
		Vector margin = Stack.alloc(new Vector(3));
		margin.set(getMargin(), getMargin(), getMargin());
		halfExtents.add(margin);
		return out;
	}
	
	public Vector getHalfExtentsWithoutMargin(Vector out)
	{
		out.set(this.implicitShapeDimensions); // changed in Bullet 2.63: assume the scaling and margin are included
		return out;
	}
	
	@Override
	public BroadphaseNativeType getShapeType()
	{
		return BroadphaseNativeType.BOX_SHAPE_PROXYTYPE;
	}
	
	@Override
	public Vector localGetSupportingVertex(Vector vec, Vector out)
	{
		Vector halfExtents = getHalfExtentsWithoutMargin(out);
		
		halfExtents.addAll(getMargin());
		
		out.set(ScalarUtil.fsel(vec.get(X), halfExtents.get(X),
				-halfExtents.get(X)),
				ScalarUtil.fsel(vec.get(Y), halfExtents.get(Y),
						-halfExtents.get(Y)),
						ScalarUtil.fsel(vec.get(Z), halfExtents.get(Z),
								-halfExtents.get(Z)));
		return out;
	}
	
	@Override
	public Vector localGetSupportingVertexWithoutMargin(Vector vec, Vector out)
	{
		Vector halfExtents = getHalfExtentsWithoutMargin(out);
		
		out.set(ScalarUtil.fsel(vec.get(X), halfExtents.get(X),
				-halfExtents.get(X)),
				ScalarUtil.fsel(vec.get(Y), halfExtents.get(Y),
						-halfExtents.get(Y)),
						ScalarUtil.fsel(vec.get(Z), halfExtents.get(Z),
								-halfExtents.get(Z)));
		return out;
	}
	
	@Override
	public void batchedUnitVectorGetSupportingVertexWithoutMargin(
			Vector[] vectors, Vector[] supportVerticesOut, int numVectors)
	{
		Vector halfExtents = getHalfExtentsWithoutMargin(Stack
				.alloc(new Vector(3)));
		
		for (int i = 0; i < numVectors; i++)
		{
			Vector vec = vectors[i];
			supportVerticesOut[i].set(ScalarUtil.fsel(vec.get(X),
					halfExtents.get(X), -halfExtents.get(X)), ScalarUtil.fsel(
							vec.get(Y), halfExtents.get(Y), -halfExtents.get(Y)),
							ScalarUtil.fsel(vec.get(Z), halfExtents.get(Z),
									-halfExtents.get(Z)));
		}
	}
	
	@Override
	public void setMargin(float margin)
	{
		// correct the implicitShapeDimensions for the margin
		Vector oldMargin = Stack.alloc(new Vector(3));
		oldMargin.set(getMargin(), getMargin(), getMargin());
		Vector implicitShapeDimensionsWithMargin = Stack.alloc(new Vector(3));
		implicitShapeDimensionsWithMargin.add(this.implicitShapeDimensions,
				oldMargin);
		
		super.setMargin(margin);
		Vector newMargin = Stack.alloc(new Vector(3));
		newMargin.set(getMargin(), getMargin(), getMargin());
		this.implicitShapeDimensions.sub(implicitShapeDimensionsWithMargin,
				newMargin);
	}
	
	@Override
	public void setLocalScaling(Vector scaling)
	{
		Vector oldMargin = Stack.alloc(new Vector(3));
		oldMargin.set(getMargin(), getMargin(), getMargin());
		Vector implicitShapeDimensionsWithMargin = Stack.alloc(new Vector(3));
		implicitShapeDimensionsWithMargin.add(this.implicitShapeDimensions,
				oldMargin);
		Vector unScaledImplicitShapeDimensionsWithMargin = Stack
				.alloc(new Vector(3));
		VectorUtil.div(unScaledImplicitShapeDimensionsWithMargin,
				implicitShapeDimensionsWithMargin, this.localScaling);
		
		super.setLocalScaling(scaling);
		
		VectorUtil.mul(this.implicitShapeDimensions,
				unScaledImplicitShapeDimensionsWithMargin, this.localScaling);
		this.implicitShapeDimensions.sub(oldMargin);
	}
	
	@Override
	public void getAabb(Transform t, Vector aabbMin, Vector aabbMax)
	{
		AabbUtil2.transformAabb(
				getHalfExtentsWithoutMargin(Stack.alloc(new Vector(3))),
				getMargin(), t, aabbMin, aabbMax);
	}
	
	@Override
	public void calculateLocalInertia(float mass, Vector inertia)
	{
		// btScalar margin = btScalar(0.);
		Vector halfExtents = getHalfExtentsWithMargin(Stack
				.alloc(new Vector(3)));
		
		float lx = 2f * halfExtents.get(X);
		float ly = 2f * halfExtents.get(Y);
		float lz = 2f * halfExtents.get(Z);
		
		inertia.set(mass / 12f * (ly * ly + lz * lz), mass / 12f
				* (lx * lx + lz * lz), mass / 12f * (lx * lx + ly * ly));
	}
	
	@Override
	public void getPlane(Vector planeNormal, Vector planeSupport, int i)
	{
		// this plane might not be aligned...
		Vector plane = Stack.alloc(new Vector(4));
		getPlaneEquation(plane, i);
		planeNormal.set(plane.get(X), plane.get(Y), plane.get(Z));
		Vector tmp = Stack.alloc(new Vector(3));
		tmp.negate(planeNormal);
		localGetSupportingVertex(tmp, planeSupport);
	}
	
	@Override
	public int getNumPlanes()
	{
		return 6;
	}
	
	@Override
	public int getNumVertices()
	{
		return 8;
	}
	
	@Override
	public int getNumEdges()
	{
		return 12;
	}
	
	@Override
	public void getVertex(int i, Vector vtx)
	{
		Vector halfExtents = getHalfExtentsWithoutMargin(Stack
				.alloc(new Vector(3)));
		
		vtx.set(halfExtents.get(X) * (1 - (i & 1)) - halfExtents.get(X)
				* (i & 1), halfExtents.get(Y) * (1 - ((i & 2) >> 1))
				- halfExtents.get(Y) * ((i & 2) >> 1), halfExtents.get(Z)
				* (1 - ((i & 4) >> 2)) - halfExtents.get(Z) * ((i & 4) >> 2));
	}
	
	public void getPlaneEquation(Vector plane, int i)
	{
		Vector halfExtents = getHalfExtentsWithoutMargin(Stack
				.alloc(new Vector(3)));
		
		switch (i)
		{
			case 0:
				plane.set(1f, 0f, 0f, -halfExtents.get(X));
				break;
			case 1:
				plane.set(-1f, 0f, 0f, -halfExtents.get(X));
				break;
			case 2:
				plane.set(0f, 1f, 0f, -halfExtents.get(Y));
				break;
			case 3:
				plane.set(0f, -1f, 0f, -halfExtents.get(Y));
				break;
			case 4:
				plane.set(0f, 0f, 1f, -halfExtents.get(Z));
				break;
			case 5:
				plane.set(0f, 0f, -1f, -halfExtents.get(Z));
				break;
			default:
				assert (false);
		}
	}
	
	@Override
	public void getEdge(int i, Vector pa, Vector pb)
	{
		int edgeVert0 = 0;
		int edgeVert1 = 0;
		
		switch (i)
		{
			case 0:
				edgeVert0 = 0;
				edgeVert1 = 1;
				break;
			case 1:
				edgeVert0 = 0;
				edgeVert1 = 2;
				break;
			case 2:
				edgeVert0 = 1;
				edgeVert1 = 3;
				
				break;
			case 3:
				edgeVert0 = 2;
				edgeVert1 = 3;
				break;
			case 4:
				edgeVert0 = 0;
				edgeVert1 = 4;
				break;
			case 5:
				edgeVert0 = 1;
				edgeVert1 = 5;
				
				break;
			case 6:
				edgeVert0 = 2;
				edgeVert1 = 6;
				break;
			case 7:
				edgeVert0 = 3;
				edgeVert1 = 7;
				break;
			case 8:
				edgeVert0 = 4;
				edgeVert1 = 5;
				break;
			case 9:
				edgeVert0 = 4;
				edgeVert1 = 6;
				break;
			case 10:
				edgeVert0 = 5;
				edgeVert1 = 7;
				break;
			case 11:
				edgeVert0 = 6;
				edgeVert1 = 7;
				break;
			default:
				assert (false);
		}
		
		getVertex(edgeVert0, pa);
		getVertex(edgeVert1, pb);
	}
	
	@Override
	public boolean isInside(Vector pt, float tolerance)
	{
		Vector halfExtents = getHalfExtentsWithoutMargin(Stack
				.alloc(new Vector(3)));
		
		// btScalar minDist = 2*tolerance;
		
		boolean result = (pt.get(X) <= (halfExtents.get(X) + tolerance))
				&& (pt.get(X) >= (-halfExtents.get(X) - tolerance))
				&& (pt.get(Y) <= (halfExtents.get(Y) + tolerance))
				&& (pt.get(Y) >= (-halfExtents.get(Y) - tolerance))
				&& (pt.get(Z) <= (halfExtents.get(Z) + tolerance))
				&& (pt.get(Z) >= (-halfExtents.get(Z) - tolerance));
		
		return result;
	}
	
	@Override
	public String getName()
	{
		return "Box";
	}
	
	@Override
	public int getNumPreferredPenetrationDirections()
	{
		return 6;
	}
	
	@Override
	public void getPreferredPenetrationDirection(int index,
			Vector penetrationVector)
	{
		switch (index)
		{
			case 0:
				penetrationVector.set(1f, 0f, 0f);
				break;
			case 1:
				penetrationVector.set(-1f, 0f, 0f);
				break;
			case 2:
				penetrationVector.set(0f, 1f, 0f);
				break;
			case 3:
				penetrationVector.set(0f, -1f, 0f);
				break;
			case 4:
				penetrationVector.set(0f, 0f, 1f);
				break;
			case 5:
				penetrationVector.set(0f, 0f, -1f);
				break;
			default:
				assert (false);
		}
	}

}
