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

import static com.elusivehawk.util.math.MathConst.*;
import com.elusivehawk.engine.physics.jbullet.collision.broadphase.BroadphaseNativeType;
import com.elusivehawk.engine.physics.jbullet.linearmath.MatrixUtil;
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import com.elusivehawk.engine.physics.jbullet.linearmath.VectorUtil;
import com.elusivehawk.engine.physics.jbullet.util.ObjectArrayList;
import com.elusivehawk.util.math.Matrix;
import com.elusivehawk.util.math.Vector;
import cz.advel.stack.Stack;

// JAVA NOTE: CompoundShape from 2.71

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * CompoundShape allows to store multiple other {@link CollisionShape}s. This allows
 * for moving concave collision objects. This is more general than the {@link BvhTriangleMeshShape}.
 * 
 * @author jezek2
 */
public class CompoundShape extends CollisionShape
{
	private final ObjectArrayList<CompoundShapeChild> children = new ObjectArrayList<CompoundShapeChild>();
	private final Vector localAabbMin = new Vector(1e30f, 1e30f, 1e30f);
	private final Vector localAabbMax = new Vector(-1e30f, -1e30f, -1e30f);
	
	private OptimizedBvh aabbTree = null;
	
	private float collisionMargin = 0f;
	protected final Vector localScaling = new Vector(1f, 1f, 1f);
	
	public void addChildShape(Transform localTransform, CollisionShape shape)
	{
		// m_childTransforms.push_back(localTransform);
		// m_childShapes.push_back(shape);
		CompoundShapeChild child = new CompoundShapeChild();
		child.transform.set(localTransform);
		child.childShape = shape;
		child.childShapeType = shape.getShapeType();
		child.childMargin = shape.getMargin();
		
		this.children.add(child);
		
		// extend the local aabbMin/aabbMax
		Vector _localAabbMin = Stack.alloc(new Vector(3)), _localAabbMax = Stack
				.alloc(new Vector(3));
		shape.getAabb(localTransform, _localAabbMin, _localAabbMax);
		
		// JAVA NOTE: rewritten
		// for (int i=0;i<3;i++)
		// {
		// if (this.localAabbMin[i] > _localAabbMin[i])
		// {
		// this.localAabbMin[i] = _localAabbMin[i];
		// }
		// if (this.localAabbMax[i] < _localAabbMax[i])
		// {
		// this.localAabbMax[i] = _localAabbMax[i];
		// }
		// }
		VectorUtil.setMin(this.localAabbMin, _localAabbMin);
		VectorUtil.setMax(this.localAabbMax, _localAabbMax);
	}
	
	/**
	 * Remove all children shapes that contain the specified shape.
	 */
	public void removeChildShape(CollisionShape shape)
	{
		boolean done_removing;
		
		// Find the children containing the shape specified, and remove those children.
		do
		{
			done_removing = true;
			
			for (int i = 0; i < this.children.size(); i++)
			{
				if (this.children.getQuick(i).childShape == shape)
				{
					this.children.removeQuick(i);
					done_removing = false;  // Do another iteration pass after removing from the vector
					break;
				}
			}
		}
		while (!done_removing);
		
		recalculateLocalAabb();
	}
	
	public int getNumChildShapes()
	{
		return this.children.size();
	}
	
	public CollisionShape getChildShape(int index)
	{
		return this.children.getQuick(index).childShape;
	}
	
	public Transform getChildTransform(int index, Transform out)
	{
		out.set(this.children.getQuick(index).transform);
		return out;
	}
	
	public ObjectArrayList<CompoundShapeChild> getChildList()
	{
		return this.children;
	}
	
	/**
	 * getAabb's default implementation is brute force, expected derived classes to implement a fast dedicated version.
	 */
	@Override
	public void getAabb(Transform trans, Vector aabbMin, Vector aabbMax)
	{
		Vector localHalfExtents = Stack.alloc(new Vector(3));
		localHalfExtents.sub(this.localAabbMax, this.localAabbMin);
		localHalfExtents.scale(0.5f);
		localHalfExtents.addAll(getMargin());
		
		Vector localCenter = Stack.alloc(new Vector(3));
		localCenter.add(this.localAabbMax, this.localAabbMin);
		localCenter.scale(0.5f);
		
		Matrix abs_b = Stack.alloc(trans.basis);
		MatrixUtil.absolute(abs_b);
		
		Vector center = Stack.alloc(localCenter);
		trans.transform(center);
		
		Vector tmp = Stack.alloc(new Vector(3));
		
		Vector extent = Stack.alloc(new Vector(3));
		abs_b.getRow(0, tmp);
		extent.set(X, tmp.dot(localHalfExtents), false);
		abs_b.getRow(1, tmp);
		extent.set(Y, tmp.dot(localHalfExtents), false);
		abs_b.getRow(2, tmp);
		extent.set(Z, tmp.dot(localHalfExtents));
		
		aabbMin.sub(center, extent);
		aabbMax.add(center, extent);
	}
	
	/**
	 * Re-calculate the local Aabb. Is called at the end of removeChildShapes.
	 * Use this yourself if you modify the children or their transforms.
	 */
	public void recalculateLocalAabb()
	{
		// Recalculate the local aabb
		// Brute force, it iterates over all the shapes left.
		this.localAabbMin.set(1e30f, 1e30f, 1e30f);
		this.localAabbMax.set(-1e30f, -1e30f, -1e30f);
		
		Vector tmpLocalAabbMin = Stack.alloc(new Vector(3));
		Vector tmpLocalAabbMax = Stack.alloc(new Vector(3));
		
		// extend the local aabbMin/aabbMax
		for (int j = 0; j < this.children.size(); j++)
		{
			this.children.getQuick(j).childShape.getAabb(
					this.children.getQuick(j).transform, tmpLocalAabbMin,
					tmpLocalAabbMax);
			
			for (int i = 0; i < 3; i++)
			{
				if (this.localAabbMin.get(i) > tmpLocalAabbMin.get(i))
				{
					this.localAabbMin.set(i, tmpLocalAabbMin.get(i), i == 2);
				}
				if (this.localAabbMax.get(i) < tmpLocalAabbMax.get(i))
				{
					this.localAabbMax.set(i, tmpLocalAabbMax.get(i), i == 2);
				}
			}
		}
	}
	
	@Override
	public void setLocalScaling(Vector scaling)
	{
		this.localScaling.set(scaling);
	}
	
	@Override
	public Vector getLocalScaling(Vector out)
	{
		out.set(this.localScaling);
		return out;
	}
	
	@Override
	public void calculateLocalInertia(float mass, Vector inertia)
	{
		// approximation: take the inertia from the aabb for now
		Transform ident = Stack.alloc(Transform.class);
		ident.setIdentity();
		Vector aabbMin = Stack.alloc(new Vector(3)), aabbMax = Stack
				.alloc(new Vector(3));
		getAabb(ident, aabbMin, aabbMax);
		
		Vector halfExtents = Stack.alloc(new Vector(3));
		halfExtents.sub(aabbMax, aabbMin);
		halfExtents.scale(0.5f);
		
		float lx = 2f * halfExtents.get(X);
		float ly = 2f * halfExtents.get(Y);
		float lz = 2f * halfExtents.get(Z);
		
		inertia.set(X, (mass / 12f) * (ly * ly + lz * lz), false);
		inertia.set(Y, (mass / 12f) * (lx * lx + lz * lz), false);
		inertia.set(Z, (mass / 12f) * (lx * lx + ly * ly));
	}
	
	@Override
	public BroadphaseNativeType getShapeType()
	{
		return BroadphaseNativeType.COMPOUND_SHAPE_PROXYTYPE;
	}
	
	@Override
	public void setMargin(float margin)
	{
		this.collisionMargin = margin;
	}
	
	@Override
	public float getMargin()
	{
		return this.collisionMargin;
	}
	
	@Override
	public String getName()
	{
		return "Compound";
	}
	
	// this is optional, but should make collision queries faster, by culling non-overlapping nodes
	// void createAabbTreeFromChildren();
	
	public OptimizedBvh getAabbTree()
	{
		return this.aabbTree;
	}
	
	/**
	 * Computes the exact moment of inertia and the transform from the coordinate
	 * system defined by the principal axes of the moment of inertia and the center
	 * of mass to the current coordinate system. "masses" points to an array
	 * of masses of the children. The resulting transform "principal" has to be
	 * applied inversely to all children transforms in order for the local coordinate
	 * system of the compound shape to be centered at the center of mass and to coincide
	 * with the principal axes. This also necessitates a correction of the world transform
	 * of the collision object by the principal transform.
	 */
	public void calculatePrincipalAxisTransform(float[] masses,
			Transform principal, Vector inertia)
	{
		int n = this.children.size();
		
		float totalMass = 0;
		Vector center = Stack.alloc(new Vector(3));
		center.set(0, 0, 0);
		for (int k = 0; k < n; k++)
		{
			center.scaleAdd(masses[k],
					this.children.getQuick(k).transform.origin, center);
			totalMass += masses[k];
		}
		center.scale(1f / totalMass);
		principal.origin.set(center);
		
		Matrix tensor = Stack.alloc(new Matrix(3, 3));
		
		for (int k = 0; k < n; k++)
		{
			Vector i = Stack.alloc(new Vector(3));
			this.children.getQuick(k).childShape.calculateLocalInertia(
					masses[k], i);
			
			Transform t = this.children.getQuick(k).transform;
			Vector o = Stack.alloc(new Vector(3));
			o.sub(t.origin, center);
			
			// compute inertia tensor in coordinate system of compound shape
			Matrix j = Stack.alloc(new Matrix(3, 3));
			j.transpose(t.basis);
			
			for (int x = 0; x < 3; x++)
			{
				for (int y = 0; y < 3; y++)
				{
					j.mul(x, y, i.get(y));
					
				}
				
			}
			
			j.mul(t.basis, j);
			
			// add inertia tensor
			tensor.add(j);
			
			// compute inertia tensor of pointmass at o
			float o2 = o.lengthSquared();
			j.setRow(0, o2, 0, 0);
			j.setRow(1, 0, o2, 0);
			j.setRow(2, 0, 0, o2);
			
			for (int x = 0; x < 3; x++)
			{
				for (int y = 0; y < 3; y++)
				{
					j.add(x, y, o.get(y) * -o.get(x));
					
				}
				
			}
			/*
			 * j.m00 += o.get(X)* -o.x;
			 * j.m01 += o.get(Y)* -o.x;
			 * j.m02 += o.get(Z)* -o.x;
			 * j.m10 += o.get(X)* -o.y;
			 * j.m11 += o.get(Y)* -o.y;
			 * j.m12 += o.get(Z)* -o.y;
			 * j.m20 += o.get(X)* -o.z;
			 * j.m21 += o.get(Y)* -o.z;
			 * j.m22 += o.get(Z)* -o.z;
			 */
			
			// add inertia tensor of pointmass
			for (int x = 0; x < 3; x++)
			{
				for (int y = 0; y < 3; y++)
				{
					tensor.add(x, y, masses[k] * j.get(x, y));
					
				}
				
			}
			/*
			 * tensor.m00 += masses[k] * j.m00;
			 * tensor.m01 += masses[k] * j.m01;
			 * tensor.m02 += masses[k] * j.m02;
			 * tensor.m10 += masses[k] * j.m10;
			 * tensor.m11 += masses[k] * j.m11;
			 * tensor.m12 += masses[k] * j.m12;
			 * tensor.m20 += masses[k] * j.m20;
			 * tensor.m21 += masses[k] * j.m21;
			 * tensor.m22 += masses[k] * j.m22;
			 */
		}
		
		MatrixUtil.diagonalize(tensor, principal.basis, 0.00001f, 20);
		
		inertia.set(tensor.get(0, 0), tensor.get(1, 1), tensor.get(2, 2));
	}
	
}
