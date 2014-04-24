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
import com.elusivehawk.engine.math.Matrix;
import com.elusivehawk.engine.math.Vector;
import com.elusivehawk.engine.physics.jbullet.collision.broadphase.BroadphaseNativeType;
import com.elusivehawk.engine.physics.jbullet.linearmath.MatrixUtil;
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import com.elusivehawk.engine.physics.jbullet.linearmath.VectorUtil;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */

// JAVA NOTE: ScaledBvhTriangleMeshShape from 2.73 SP1

/**
 * The ScaledBvhTriangleMeshShape allows to instance a scaled version of an existing
 * {@link BvhTriangleMeshShape}. Note that each {@link BvhTriangleMeshShape} still can
 * have its own local scaling, independent from this ScaledBvhTriangleMeshShape 'localScaling'.
 *
 * @author jezek2
 */
public class ScaledBvhTriangleMeshShape extends ConcaveShape {

	protected final Vector localScaling = new Vector();
	protected BvhTriangleMeshShape bvhTriMeshShape;

	public ScaledBvhTriangleMeshShape(BvhTriangleMeshShape childShape, Vector localScaling) {
		this.localScaling.set(localScaling);
		this.bvhTriMeshShape = childShape;
	}

	public BvhTriangleMeshShape getChildShape() {
		return this.bvhTriMeshShape;
	}

	@Override
	public void processAllTriangles(TriangleCallback callback, Vector aabbMin, Vector aabbMax) {
		ScaledTriangleCallback scaledCallback = new ScaledTriangleCallback(callback, this.localScaling);

		Vector invLocalScaling = Stack.alloc(new Vector(3));
		invLocalScaling.set(1.f / this.localScaling.get(X), 1.f / this.localScaling.get(Y), 1.f / this.localScaling.get(Z));

		Vector scaledAabbMin = Stack.alloc(new Vector(3));
		Vector scaledAabbMax = Stack.alloc(new Vector(3));

		// support negative scaling
		scaledAabbMin.set(X, this.localScaling.get(X)>= 0f ? aabbMin.get(X)* invLocalScaling.get(X): aabbMax.get(X)* invLocalScaling.get(X));
		scaledAabbMin.set(Y, this.localScaling.get(Y)>= 0f ? aabbMin.get(Y)* invLocalScaling.get(Y): aabbMax.get(Y)* invLocalScaling.get(Y));
		scaledAabbMin.set(Z, this.localScaling.get(Z)>= 0f ? aabbMin.get(Z)* invLocalScaling.get(Z): aabbMax.get(Z)* invLocalScaling.get(Z));

		scaledAabbMax.set(X, this.localScaling.get(X)<= 0f ? aabbMin.get(X)* invLocalScaling.get(X): aabbMax.get(X)* invLocalScaling.get(X));
		scaledAabbMax.set(Y, this.localScaling.get(Y)<= 0f ? aabbMin.get(Y)* invLocalScaling.get(Y): aabbMax.get(Y)* invLocalScaling.get(Y));
		scaledAabbMax.set(Z, this.localScaling.get(Z)<= 0f ? aabbMin.get(Z)* invLocalScaling.get(Z): aabbMax.get(Z)* invLocalScaling.get(Z));

		this.bvhTriMeshShape.processAllTriangles(scaledCallback, scaledAabbMin, scaledAabbMax);
	}

	@Override
	public void getAabb(Transform trans, Vector aabbMin, Vector aabbMax) {
		Vector localAabbMin = this.bvhTriMeshShape.getLocalAabbMin(Stack.alloc(new Vector(3)));
		Vector localAabbMax = this.bvhTriMeshShape.getLocalAabbMax(Stack.alloc(new Vector(3)));

		Vector tmpLocalAabbMin = Stack.alloc(new Vector(3));
		Vector tmpLocalAabbMax = Stack.alloc(new Vector(3));
		VectorUtil.mul(tmpLocalAabbMin, localAabbMin, this.localScaling);
		VectorUtil.mul(tmpLocalAabbMax, localAabbMax, this.localScaling);

		localAabbMin.set(X, (this.localScaling.get(X)>= 0f) ? tmpLocalAabbMin.get(X): tmpLocalAabbMax.get(X));
		localAabbMin.set(Y, (this.localScaling.get(Y)>= 0f) ? tmpLocalAabbMin.get(Y): tmpLocalAabbMax.get(Y));
		localAabbMin.set(Z, (this.localScaling.get(Z)>= 0f) ? tmpLocalAabbMin.get(Z): tmpLocalAabbMax.get(Z));
		localAabbMax.set(X, (this.localScaling.get(X)<= 0f) ? tmpLocalAabbMin.get(X): tmpLocalAabbMax.get(X));
		localAabbMax.set(Y, (this.localScaling.get(Y)<= 0f) ? tmpLocalAabbMin.get(Y): tmpLocalAabbMax.get(Y));
		localAabbMax.set(Z, (this.localScaling.get(Z)<= 0f) ? tmpLocalAabbMin.get(Z): tmpLocalAabbMax.get(Z));

		Vector localHalfExtents = Stack.alloc(new Vector(3));
		localHalfExtents.sub(localAabbMax, localAabbMin);
		localHalfExtents.scale(0.5f);

		float margin = this.bvhTriMeshShape.getMargin();
		localHalfExtents.addAll(margin);

		Vector localCenter = Stack.alloc(new Vector(3));
		localCenter.add(localAabbMax, localAabbMin);
		localCenter.scale(0.5f);

		Matrix abs_b = Stack.alloc(trans.basis);
		MatrixUtil.absolute(abs_b);

		Vector center = Stack.alloc(localCenter);
		trans.transform(center);

		Vector extent = Stack.alloc(new Vector(3));
		Vector tmp = Stack.alloc(new Vector(3));
		
		for (int c = 0; c < 3; c++)
		{
			abs_b.getRow(c, tmp);
			extent.set(c, tmp.dot(localHalfExtents));
			
		}
		
		aabbMin.sub(center, extent);
		aabbMax.add(center, extent);
	}

	@Override
	public BroadphaseNativeType getShapeType() {
		return BroadphaseNativeType.SCALED_TRIANGLE_MESH_SHAPE_PROXYTYPE;
	}

	@Override
	public void setLocalScaling(Vector scaling) {
		this.localScaling.set(scaling);
	}

	@Override
	public Vector getLocalScaling(Vector out) {
		out.set(this.localScaling);
		return out;
	}

	@Override
	public void calculateLocalInertia(float mass, Vector inertia) {
	}

	@Override
	public String getName() {
		return "SCALEDBVHTRIANGLEMESH";
	}

	////////////////////////////////////////////////////////////////////////////

	private static class ScaledTriangleCallback extends TriangleCallback {
		private TriangleCallback originalCallback;
		private Vector localScaling;
		private Vector[] newTriangle = new Vector[3];

		public ScaledTriangleCallback(TriangleCallback originalCallback, Vector localScaling) {
			this.originalCallback = originalCallback;
			this.localScaling = localScaling;
			
			for (int i=0; i<this.newTriangle.length; i++) {
				this.newTriangle[i] = new Vector();
			}
		}

		@Override
		public void processTriangle(Vector[] triangle, int partId, int triangleIndex) {
			VectorUtil.mul(this.newTriangle[0], triangle[0], this.localScaling);
			VectorUtil.mul(this.newTriangle[1], triangle[1], this.localScaling);
			VectorUtil.mul(this.newTriangle[2], triangle[2], this.localScaling);
			this.originalCallback.processTriangle(this.newTriangle, partId, triangleIndex);
		}
	}

}
