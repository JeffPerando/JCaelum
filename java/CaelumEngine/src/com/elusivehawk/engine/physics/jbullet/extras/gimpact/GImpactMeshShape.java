/*
 * Java port of Bullet (c) 2008 Martin Dvorak <jezek2@advel.cz>
 *
 * This source file is part of GIMPACT Library.
 *
 * For the latest info, see http://gimpact.sourceforge.net/
 *
 * Copyright (c) 2007 Francisco Leon Najera. C.C. 80087371.
 * email: projectileman@yahoo.com
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

package com.elusivehawk.engine.physics.jbullet.extras.gimpact;

import com.elusivehawk.engine.physics.jbullet.collision.dispatch.CollisionWorld.RayResultCallback;
import com.elusivehawk.engine.physics.jbullet.collision.shapes.CollisionShape;
import com.elusivehawk.engine.physics.jbullet.collision.shapes.StridingMeshInterface;
import com.elusivehawk.engine.physics.jbullet.collision.shapes.TriangleCallback;
import com.elusivehawk.engine.physics.jbullet.extras.gimpact.BoxCollision.AABB;
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import com.elusivehawk.engine.physics.jbullet.util.ObjectArrayList;
import com.elusivehawk.util.math.Vector;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 *
 * @author jezek2
 */
public class GImpactMeshShape extends GImpactShapeInterface {
	
	protected ObjectArrayList<GImpactMeshShapePart> mesh_parts = new ObjectArrayList<GImpactMeshShapePart>();

	public GImpactMeshShape(StridingMeshInterface meshInterface) {
		buildMeshParts(meshInterface);
	}
	
	public int getMeshPartCount() {
		return this.mesh_parts.size();
	}

	public GImpactMeshShapePart getMeshPart(int index) {
		return this.mesh_parts.getQuick(index);
	}

	@Override
	public void setLocalScaling(Vector scaling) {
		this.localScaling.set(scaling);

		int i = this.mesh_parts.size();
		while ((i--) != 0) {
			GImpactMeshShapePart part = this.mesh_parts.getQuick(i);
			part.setLocalScaling(scaling);
		}

		this.needs_update = true;
	}

	@Override
	public void setMargin(float margin) {
		this.collisionMargin = margin;

		int i = this.mesh_parts.size();
		while ((i--) != 0) {
			GImpactMeshShapePart part = this.mesh_parts.getQuick(i);
			part.setMargin(margin);
		}

		this.needs_update = true;
	}

	@Override
	public void postUpdate() {
		int i = this.mesh_parts.size();
		while ((i--) != 0) {
			GImpactMeshShapePart part = this.mesh_parts.getQuick(i);
			part.postUpdate();
		}

		this.needs_update = true;
	}

	@Override
	public void calculateLocalInertia(float mass, Vector inertia) {
		//#ifdef CALC_EXACT_INERTIA
		inertia.set(0f, 0f, 0f);

		int i = getMeshPartCount();
		float partmass = mass / i;

		Vector partinertia = Stack.alloc(new Vector(3));

		while ((i--) != 0) {
			getMeshPart(i).calculateLocalInertia(partmass, partinertia);
			inertia.add(partinertia);
		}

		////#else
		//
		//// Calc box inertia
		//
		//btScalar lx= m_localAABB.m_max[0] - m_localAABB.m_min[0];
		//btScalar ly= m_localAABB.m_max[1] - m_localAABB.m_min[1];
		//btScalar lz= m_localAABB.m_max[2] - m_localAABB.m_min[2];
		//const btScalar x2 = lx*lx;
		//const btScalar y2 = ly*ly;
		//const btScalar z2 = lz*lz;
		//const btScalar scaledmass = mass * btScalar(0.08333333);
		//
		//inertia = scaledmass * (btVector3(y2+z2,x2+z2,x2+y2));
		////#endif
	}
	
	@Override
	PrimitiveManagerBase getPrimitiveManager() {
		assert (false);
		return null;
	}

	@Override
	public int getNumChildShapes() {
		assert (false);
		return 0;
	}

	@Override
	public boolean childrenHasTransform() {
		assert (false);
		return false;
	}

	@Override
	public boolean needsRetrieveTriangles() {
		assert (false);
		return false;
	}

	@Override
	public boolean needsRetrieveTetrahedrons() {
		assert (false);
		return false;
	}

	@Override
	public void getBulletTriangle(int prim_index, TriangleShapeEx triangle) {
		assert (false);
	}

	@Override
	void getBulletTetrahedron(int prim_index, TetrahedronShapeEx tetrahedron) {
		assert (false);
	}

	@Override
	public void lockChildShapes() {
		assert (false);
	}

	@Override
	public void unlockChildShapes() {
		assert (false);
	}

	@Override
	public void getChildAabb(int child_index, Transform t, Vector aabbMin, Vector aabbMax) {
		assert (false);
	}

	@Override
	public CollisionShape getChildShape(int index) {
		assert (false);
		return null;
	}

	@Override
	public Transform getChildTransform(int index) {
		assert (false);
		return null;
	}

	@Override
	public void setChildTransform(int index, Transform transform) {
		assert (false);
	}

	@Override
	ShapeType getGImpactShapeType() {
		return ShapeType.TRIMESH_SHAPE;
	}

	@Override
	public String getName() {
		return "GImpactMesh";
	}

	@Override
	public void rayTest(Vector rayFrom, Vector rayTo, RayResultCallback resultCallback) {
	}

	@Override
	public void processAllTriangles(TriangleCallback callback, Vector aabbMin, Vector aabbMax) {
		int i = this.mesh_parts.size();
		while ((i--) != 0) {
			this.mesh_parts.getQuick(i).processAllTriangles(callback, aabbMin, aabbMax);
		}
	}
	
	protected void buildMeshParts(StridingMeshInterface meshInterface) {
		for (int i=0; i<meshInterface.getNumSubParts(); i++) {
			GImpactMeshShapePart newpart = new GImpactMeshShapePart(meshInterface, i);
			this.mesh_parts.add(newpart);
		}
	}

	@Override
	protected void calcLocalAABB() {
		AABB tmpAABB = Stack.alloc(AABB.class);

		this.localAABB.invalidate();
		int i = this.mesh_parts.size();
		while ((i--) != 0) {
			this.mesh_parts.getQuick(i).updateBound();
			this.localAABB.merge(this.mesh_parts.getQuick(i).getLocalBox(tmpAABB));
		}
	}

}
