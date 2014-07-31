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
import com.elusivehawk.engine.physics.jbullet.linearmath.AabbUtil2;
import com.elusivehawk.engine.physics.jbullet.linearmath.MatrixUtil;
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import com.elusivehawk.util.math.Matrix;
import com.elusivehawk.util.math.Vector;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * Concave triangle mesh abstract class. Use {@link BvhTriangleMeshShape} as concrete
 * implementation.
 * 
 * @author jezek2
 */
public abstract class TriangleMeshShape extends ConcaveShape {

	protected final Vector localAabbMin = new Vector(3);
	protected final Vector localAabbMax = new Vector(3);
	protected StridingMeshInterface meshInterface;

	/**
	 * TriangleMeshShape constructor has been disabled/protected, so that users will not mistakenly use this class.
	 * Don't use btTriangleMeshShape but use btBvhTriangleMeshShape instead!
	 */
	protected TriangleMeshShape(StridingMeshInterface meshInterface) {
		this.meshInterface = meshInterface;
		
		// JAVA NOTE: moved to BvhTriangleMeshShape
		//recalcLocalAabb();
	}
	
	public Vector localGetSupportingVertex(Vector vec, Vector out) {
		Vector tmp = Stack.alloc(new Vector(3));

		Vector supportVertex = out;

		Transform ident = Stack.alloc(Transform.class);
		ident.setIdentity();

		SupportVertexCallback supportCallback = new SupportVertexCallback(vec, ident);

		Vector aabbMax = Stack.alloc(new Vector(3));
		aabbMax.set(1e30f, 1e30f, 1e30f);
		tmp.negate(aabbMax);

		processAllTriangles(supportCallback, tmp, aabbMax);

		supportCallback.getSupportVertexLocal(supportVertex);

		return out;
	}

	public Vector localGetSupportingVertexWithoutMargin(Vector vec, Vector out) {
		assert (false);
		return localGetSupportingVertex(vec, out);
	}

	public void recalcLocalAabb() {
		for (int i = 0; i < 3; i++) {
			Vector vec = Stack.alloc(new Vector(3));
			vec.set(0f, 0f, 0f);
			vec.set(i, 1f);
			Vector tmp = localGetSupportingVertex(vec, Stack.alloc(new Vector(3)));
			localAabbMax.set(i, tmp.get(i) + collisionMargin, i == 2);
			vec.set(i, -1f);
			localGetSupportingVertex(vec, tmp);
			localAabbMin.set(i, tmp.get(i) - collisionMargin, i == 2);
		}
	}

	@Override
	public void getAabb(Transform trans, Vector aabbMin, Vector aabbMax) {
		Vector tmp = Stack.alloc(new Vector(3));

		Vector localHalfExtents = Stack.alloc(new Vector(3));
		localHalfExtents.sub(localAabbMax, localAabbMin);
		localHalfExtents.scale(0.5f);

		Vector localCenter = Stack.alloc(new Vector(3));
		localCenter.add(localAabbMax, localAabbMin);
		localCenter.scale(0.5f);

		Matrix abs_b = Stack.alloc(trans.basis);
		MatrixUtil.absolute(abs_b);

		Vector center = Stack.alloc(localCenter);
		trans.transform(center);

		Vector extent = Stack.alloc(new Vector(3));
		abs_b.getRow(0, tmp);
		extent.set(X, tmp.dot(localHalfExtents));
		abs_b.getRow(1, tmp);
		extent.set(X, tmp.dot(localHalfExtents));
		abs_b.getRow(2, tmp);
		extent.set(X, tmp.dot(localHalfExtents));

		Vector margin = Stack.alloc(new Vector(3));
		margin.set(getMargin(), getMargin(), getMargin());
		extent.add(margin);

		aabbMin.sub(center, extent);
		aabbMax.add(center, extent);
	}

	@Override
	public void processAllTriangles(TriangleCallback callback, Vector aabbMin, Vector aabbMax) {
		FilteredCallback filterCallback = new FilteredCallback(callback, aabbMin, aabbMax);

		meshInterface.internalProcessAllTriangles(filterCallback, aabbMin, aabbMax);
	}

	@Override
	public void calculateLocalInertia(float mass, Vector inertia) {
		// moving concave objects not supported
		assert (false);
		inertia.set(0f, 0f, 0f);
	}


	@Override
	public void setLocalScaling(Vector scaling) {
		meshInterface.setScaling(scaling);
		recalcLocalAabb();
	}

	@Override
	public Vector getLocalScaling(Vector out) {
		return meshInterface.getScaling(out);
	}
	
	public StridingMeshInterface getMeshInterface() {
		return meshInterface;
	}

	public Vector getLocalAabbMin(Vector out) {
		out.set(localAabbMin);
		return out;
	}

	public Vector getLocalAabbMax(Vector out) {
		out.set(localAabbMax);
		return out;
	}

	@Override
	public String getName() {
		return "TRIANGLEMESH";
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	private class SupportVertexCallback extends TriangleCallback {
		private final Vector supportVertexLocal = new Vector(0f, 0f, 0f);
		public final Transform worldTrans = new Transform();
		public float maxDot = -1e30f;
		public final Vector supportVecLocal = new Vector(3);

		public SupportVertexCallback(Vector supportVecWorld,Transform trans) {
			this.worldTrans.set(trans);
			MatrixUtil.transposeTransform(supportVecLocal, supportVecWorld, worldTrans.basis);
		}
		
		public void processTriangle(Vector[] triangle, int partId, int triangleIndex) {
			for (int i = 0; i < 3; i++) {
				float dot = supportVecLocal.dot(triangle[i]);
				if (dot > maxDot) {
					maxDot = dot;
					supportVertexLocal.set(triangle[i]);
				}
			}
		}

		public Vector getSupportVertexWorldSpace(Vector out) {
			out.set(supportVertexLocal);
			worldTrans.transform(out);
			return out;
		}

		public Vector getSupportVertexLocal(Vector out) {
			out.set(supportVertexLocal);
			return out;
		}
	}
	
	private static class FilteredCallback extends InternalTriangleIndexCallback {
		public TriangleCallback callback;
		public final Vector aabbMin = new Vector();
		public final Vector aabbMax = new Vector();

		public FilteredCallback(TriangleCallback callback, Vector aabbMin, Vector aabbMax) {
			this.callback = callback;
			this.aabbMin.set(aabbMin);
			this.aabbMax.set(aabbMax);
		}

		public void internalProcessTriangleIndex(Vector[] triangle, int partId, int triangleIndex) {
			if (AabbUtil2.testTriangleAgainstAabb2(triangle, aabbMin, aabbMax)) {
				// check aabb in triangle-space, before doing this
				callback.processTriangle(triangle, partId, triangleIndex);
			}
		}
	}

}
