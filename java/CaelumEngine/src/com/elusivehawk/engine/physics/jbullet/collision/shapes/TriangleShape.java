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
import com.elusivehawk.engine.physics.jbullet.linearmath.VectorUtil;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * Single triangle shape.
 * 
 * @author jezek2
 */
public class TriangleShape extends PolyhedralConvexShape {
	
	public final Vector[] vertices1/*[3]*/ = new Vector[]{new Vector(3), new Vector(3), new Vector(3)};

	// JAVA NOTE: added
	public TriangleShape() {
	}
	
	public TriangleShape(Vector p0, Vector p1, Vector p2) {
		vertices1[0].set(p0);
		vertices1[1].set(p1);
		vertices1[2].set(p2);
	}
	
	// JAVA NOTE: added
	public void init(Vector p0, Vector p1, Vector p2) {
		vertices1[0].set(p0);
		vertices1[1].set(p1);
		vertices1[2].set(p2);
	}

	@Override
	public int getNumVertices() {
		return 3;
	}

	public Vector getVertexPtr(int index) {
		return vertices1[index];
	}
	
	@Override
	public void getVertex(int index, Vector vert) {
		vert.set(vertices1[index]);
	}

	@Override
	public BroadphaseNativeType getShapeType() {
		return BroadphaseNativeType.TRIANGLE_SHAPE_PROXYTYPE;
	}

	@Override
	public int getNumEdges() {
		return 3;
	}

	@Override
	public void getEdge(int i, Vector pa, Vector pb) {
		getVertex(i, pa);
		getVertex((i + 1) % 3, pb);
	}

	@Override
	public void getAabb(Transform t, Vector aabbMin, Vector aabbMax) {
//		btAssert(0);
		getAabbSlow(t, aabbMin, aabbMax);
	}

	@Override
	public Vector localGetSupportingVertexWithoutMargin(Vector dir, Vector out) {
		Vector dots = Stack.alloc(new Vector(3));
		dots.set(dir.dot(vertices1[0]), dir.dot(vertices1[1]), dir.dot(vertices1[2]));
		out.set(vertices1[VectorUtil.maxAxis(dots)]);
		return out;
	}

	@Override
	public void batchedUnitVectorGetSupportingVertexWithoutMargin(Vector[] vectors, Vector[] supportVerticesOut, int numVectors) {
		Vector dots = Stack.alloc(new Vector(3));

		for (int i = 0; i < numVectors; i++) {
			Vector dir = vectors[i];
			dots.set(dir.dot(vertices1[0]), dir.dot(vertices1[1]), dir.dot(vertices1[2]));
			supportVerticesOut[i].set(vertices1[VectorUtil.maxAxis(dots)]);
		}
	}

	@Override
	public void getPlane(Vector planeNormal, Vector planeSupport, int i) {
		getPlaneEquation(i,planeNormal,planeSupport);
	}

	@Override
	public int getNumPlanes() {
		return 1;
	}

	public void calcNormal(Vector normal) {
		Vector tmp1 = Stack.alloc(new Vector(3));
		Vector tmp2 = Stack.alloc(new Vector(3));

		tmp1.sub(vertices1[1], vertices1[0]);
		tmp2.sub(vertices1[2], vertices1[0]);

		normal.cross(tmp1, tmp2);
		normal.normalize();
	}

	public void getPlaneEquation(int i, Vector planeNormal, Vector planeSupport) {
		calcNormal(planeNormal);
		planeSupport.set(vertices1[0]);
	}

	@Override
	public void calculateLocalInertia(float mass, Vector inertia) {
		assert (false);
		inertia.set(0f, 0f, 0f);
	}
	
	@Override
	public boolean isInside(Vector pt, float tolerance) {
		Vector normal = Stack.alloc(new Vector(3));
		calcNormal(normal);
		// distance to plane
		float dist = pt.dot(normal);
		float planeconst = vertices1[0].dot(normal);
		dist -= planeconst;
		if (dist >= -tolerance && dist <= tolerance) {
			// inside check on edge-planes
			int i;
			for (i = 0; i < 3; i++) {
				Vector pa = Stack.alloc(new Vector(3)), pb = Stack.alloc(new Vector(3));
				getEdge(i, pa, pb);
				Vector edge = Stack.alloc(new Vector(3));
				edge.sub(pb, pa);
				Vector edgeNormal = Stack.alloc(new Vector(3));
				edgeNormal.cross(edge, normal);
				edgeNormal.normalize();
				/*float*/ dist = pt.dot(edgeNormal);
				float edgeConst = pa.dot(edgeNormal);
				dist -= edgeConst;
				if (dist < -tolerance) {
					return false;
				}
			}

			return true;
		}

		return false;
	}

	@Override
	public String getName() {
		return "Triangle";
	}

	@Override
	public int getNumPreferredPenetrationDirections() {
		return 2;
	}

	@Override
	public void getPreferredPenetrationDirection(int index, Vector penetrationVector) {
		calcNormal(penetrationVector);
		if (index != 0) {
			penetrationVector.scale(-1f);
		}
	}

}
