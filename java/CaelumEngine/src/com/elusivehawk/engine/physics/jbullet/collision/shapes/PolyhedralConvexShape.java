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

import static com.elusivehawk.util.math.MathConst.X;
import static com.elusivehawk.util.math.MathConst.Y;
import static com.elusivehawk.util.math.MathConst.Z;
import com.elusivehawk.engine.physics.jbullet.linearmath.AabbUtil2;
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import com.elusivehawk.util.math.Vector;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * PolyhedralConvexShape is an internal interface class for polyhedral convex shapes.
 * 
 * @author jezek2
 */
public abstract class PolyhedralConvexShape extends ConvexInternalShape
{
	private static Vector[] _directions = new Vector[] {
			new Vector(1f, 0f, 0f), new Vector(0f, 1f, 0f),
			new Vector(0f, 0f, 1f), new Vector(-1f, 0f, 0f),
			new Vector(0f, -1f, 0f), new Vector(0f, 0f, -1f) };
	
	private static Vector[] _supporting = new Vector[] {
			new Vector(0f, 0f, 0f), new Vector(0f, 0f, 0f),
			new Vector(0f, 0f, 0f), new Vector(0f, 0f, 0f),
			new Vector(0f, 0f, 0f), new Vector(0f, 0f, 0f) };
	
	protected final Vector localAabbMin = new Vector(1f, 1f, 1f);
	protected final Vector localAabbMax = new Vector(-1f, -1f, -1f);
	protected boolean isLocalAabbValid = false;
	
	// /** optional Hull is for optional Separating Axis Test Hull collision detection, see Hull.cpp */
	// public Hull optionalHull = null;
	
	@Override
	public Vector localGetSupportingVertexWithoutMargin(Vector vec0, Vector out)
	{
		int i;
		Vector supVec = out;
		supVec.set(0f, 0f, 0f);
		
		float maxDot = -1e30f;
		
		Vector vec = Stack.alloc(vec0);
		float lenSqr = vec.lengthSquared();
		if (lenSqr < 0.0001f)
		{
			vec.set(1f, 0f, 0f);
		}
		else
		{
			float rlen = 1f / (float)Math.sqrt(lenSqr);
			vec.scale(rlen);
		}
		
		Vector vtx = Stack.alloc(new Vector(3));
		float newDot;
		
		for (i = 0; i < getNumVertices(); i++)
		{
			getVertex(i, vtx);
			newDot = vec.dot(vtx);
			if (newDot > maxDot)
			{
				maxDot = newDot;
				supVec = vtx;
			}
		}
		
		return out;
	}
	
	@Override
	public void batchedUnitVectorGetSupportingVertexWithoutMargin(
			Vector[] vectors, Vector[] supportVerticesOut, int numVectors)
	{
		int i;
		
		Vector vtx = Stack.alloc(new Vector(3));
		float newDot;
		
		// JAVA NOTE: rewritten as code used W coord for temporary usage in Vector3
		// TODO: optimize it
		float[] wcoords = new float[numVectors];
		
		for (i = 0; i < numVectors; i++)
		{
			// TODO: used w in vector3:
			// supportVerticesOut[i].get(W)= -1e30f;
			wcoords[i] = -1e30f;
		}
		
		for (int j = 0; j < numVectors; j++)
		{
			Vector vec = vectors[j];
			
			for (i = 0; i < getNumVertices(); i++)
			{
				getVertex(i, vtx);
				newDot = vec.dot(vtx);
				// if (newDot > supportVerticesOut[j].w)
				if (newDot > wcoords[j])
				{
					// WARNING: don't swap next lines, the w component would get overwritten!
					supportVerticesOut[j].set(vtx);
					// supportVerticesOut[j].get(W)= newDot;
					wcoords[j] = newDot;
				}
			}
		}
	}
	
	@Override
	public void calculateLocalInertia(float mass, Vector inertia)
	{
		// not yet, return box inertia
		
		float margin = getMargin();
		
		Transform ident = Stack.alloc(Transform.class);
		ident.setIdentity();
		Vector aabbMin = Stack.alloc(new Vector(3)), aabbMax = Stack
				.alloc(new Vector(3));
		getAabb(ident, aabbMin, aabbMax);
		
		Vector halfExtents = Stack.alloc(new Vector(3));
		halfExtents.sub(aabbMax, aabbMin);
		halfExtents.scale(0.5f);
		
		float lx = 2f * (halfExtents.get(X) + margin);
		float ly = 2f * (halfExtents.get(Y) + margin);
		float lz = 2f * (halfExtents.get(Z) + margin);
		float x2 = lx * lx;
		float y2 = ly * ly;
		float z2 = lz * lz;
		float scaledmass = mass * 0.08333333f;
		
		inertia.set(y2 + z2, x2 + z2, x2 + y2);
		inertia.scale(scaledmass);
	}
	
	private void getNonvirtualAabb(Transform trans, Vector aabbMin,
			Vector aabbMax, float margin)
	{
		// lazy evaluation of local aabb
		assert (isLocalAabbValid);
		
		AabbUtil2.transformAabb(localAabbMin, localAabbMax, margin, trans,
				aabbMin, aabbMax);
	}
	
	@Override
	public void getAabb(Transform trans, Vector aabbMin, Vector aabbMax)
	{
		getNonvirtualAabb(trans, aabbMin, aabbMax, getMargin());
	}
	
	protected final void _PolyhedralConvexShape_getAabb(Transform trans,
			Vector aabbMin, Vector aabbMax)
	{
		getNonvirtualAabb(trans, aabbMin, aabbMax, getMargin());
	}
	
	public void recalcLocalAabb()
	{
		isLocalAabbValid = true;
		
		// #if 1
		
		batchedUnitVectorGetSupportingVertexWithoutMargin(_directions,
				_supporting, 6);
		
		for (int i = 0; i < 3; i++)
		{
			localAabbMax.set(i, _supporting[i].get(i) + collisionMargin);
			localAabbMin.set(i, _supporting[i + 3].get(i) - collisionMargin);
		}
		
		// #else
		// for (int i=0; i<3; i++) {
		// Vector vec = Stack.alloc(new Vector(3));
		// vec.set(0f, 0f, 0f);
		// VectorUtil.setCoord(vec, i, 1f);
		// Vector tmp = localGetSupportingVertex(vec, Stack.alloc(new Vector(3)));
		// VectorUtil.setCoord(localAabbMax, i, VectorUtil.getCoord(tmp, i) + collisionMargin);
		// VectorUtil.setCoord(vec, i, -1f);
		// localGetSupportingVertex(vec, tmp);
		// VectorUtil.setCoord(localAabbMin, i, VectorUtil.getCoord(tmp, i) - collisionMargin);
		// }
		// #endif
	}
	
	@Override
	public void setLocalScaling(Vector scaling)
	{
		super.setLocalScaling(scaling);
		recalcLocalAabb();
	}
	
	public abstract int getNumVertices();
	
	public abstract int getNumEdges();
	
	public abstract void getEdge(int i, Vector pa, Vector pb);
	
	public abstract void getVertex(int i, Vector vtx);
	
	public abstract int getNumPlanes();
	
	public abstract void getPlane(Vector planeNormal, Vector planeSupport, int i);
	
	// public abstract int getIndex(int i) const = 0 ;
	
	public abstract boolean isInside(Vector pt, float tolerance);
	
}
