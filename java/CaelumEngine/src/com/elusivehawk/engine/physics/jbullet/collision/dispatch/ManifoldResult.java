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

package com.elusivehawk.engine.physics.jbullet.collision.dispatch;

import com.elusivehawk.engine.physics.jbullet.BulletGlobals;
import com.elusivehawk.engine.physics.jbullet.collision.narrowphase.DiscreteCollisionDetectorInterface;
import com.elusivehawk.engine.physics.jbullet.collision.narrowphase.ManifoldPoint;
import com.elusivehawk.engine.physics.jbullet.collision.narrowphase.PersistentManifold;
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import com.elusivehawk.engine.physics.jbullet.util.ObjectPool;
import com.elusivehawk.util.math.Vector;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * ManifoldResult is helper class to manage contact results.
 * 
 * @author jezek2
 */
public class ManifoldResult extends DiscreteCollisionDetectorInterface.Result {

	//protected final BulletStack stack = BulletStack.get();
	protected final ObjectPool<ManifoldPoint> pointsPool = ObjectPool.get(ManifoldPoint.class);
	
	private PersistentManifold manifoldPtr;

	// we need this for compounds
	private final Transform rootTransA = new Transform();
	private final Transform rootTransB = new Transform();
	private CollisionObject body0;
	private CollisionObject body1;
	private int partId0;
	private int partId1;
	private int index0;
	private int index1;

	public ManifoldResult() {
	}

	public ManifoldResult(CollisionObject body0, CollisionObject body1) {
		init(body0, body1);
	}

	public void init(CollisionObject body0, CollisionObject body1) {
		this.body0 = body0;
		this.body1 = body1;
		body0.getWorldTransform(this.rootTransA);
		body1.getWorldTransform(this.rootTransB);
	}

	public PersistentManifold getPersistentManifold() {
		return manifoldPtr;
	}

	public void setPersistentManifold(PersistentManifold manifoldPtr) {
		this.manifoldPtr = manifoldPtr;
	}

	public void setShapeIdentifiers(int partId0, int index0, int partId1, int index1) {
		this.partId0 = partId0;
		this.partId1 = partId1;
		this.index0 = index0;
		this.index1 = index1;
	}

	public void addContactPoint(Vector normalOnBInWorld, Vector pointInWorld, float depth) {
		assert (manifoldPtr != null);
		//order in manifold needs to match

		if (depth > manifoldPtr.getContactBreakingThreshold()) {
			return;
		}

		boolean isSwapped = manifoldPtr.getBody0() != body0;

		Vector pointA = Stack.alloc(new Vector(3));
		pointA.scaleAdd(depth, normalOnBInWorld, pointInWorld);

		Vector localA = Stack.alloc(new Vector(3));
		Vector localB = Stack.alloc(new Vector(3));

		if (isSwapped) {
			rootTransB.invXform(pointA, localA);
			rootTransA.invXform(pointInWorld, localB);
		}
		else {
			rootTransA.invXform(pointA, localA);
			rootTransB.invXform(pointInWorld, localB);
		}

		ManifoldPoint newPt = pointsPool.get();
		newPt.init(localA, localB, normalOnBInWorld, depth);

		newPt.positionWorldOnA.set(pointA);
		newPt.positionWorldOnB.set(pointInWorld);

		int insertIndex = manifoldPtr.getCacheEntry(newPt);

		newPt.combinedFriction = calculateCombinedFriction(body0, body1);
		newPt.combinedRestitution = calculateCombinedRestitution(body0, body1);

		// BP mod, store contact triangles.
		newPt.partId0 = partId0;
		newPt.partId1 = partId1;
		newPt.index0 = index0;
		newPt.index1 = index1;

		/// todo, check this for any side effects
		if (insertIndex >= 0) {
			//const btManifoldPoint& oldPoint = m_manifoldPtr->getContactPoint(insertIndex);
			manifoldPtr.replaceContactPoint(newPt, insertIndex);
		}
		else {
			insertIndex = manifoldPtr.addManifoldPoint(newPt);
		}

		// User can override friction and/or restitution
		if (BulletGlobals.getContactAddedCallback() != null &&
				// and if either of the two bodies requires custom material
				((body0.getCollisionFlags() & CollisionFlags.CUSTOM_MATERIAL_CALLBACK) != 0 ||
				(body1.getCollisionFlags() & CollisionFlags.CUSTOM_MATERIAL_CALLBACK) != 0)) {
			//experimental feature info, for per-triangle material etc.
			CollisionObject obj0 = isSwapped ? body1 : body0;
			CollisionObject obj1 = isSwapped ? body0 : body1;
			BulletGlobals.getContactAddedCallback().contactAdded(manifoldPtr.getContactPoint(insertIndex), obj0, partId0, index0, obj1, partId1, index1);
		}

		pointsPool.release(newPt);
	}

	///User can override this material combiner by implementing gContactAddedCallback and setting body0->m_collisionFlags |= btCollisionObject::customMaterialCallback;
	private static float calculateCombinedFriction(CollisionObject body0, CollisionObject body1) {
		float friction = body0.getFriction() * body1.getFriction();

		float MAX_FRICTION = 10f;
		if (friction < -MAX_FRICTION) {
			friction = -MAX_FRICTION;
		}
		if (friction > MAX_FRICTION) {
			friction = MAX_FRICTION;
		}
		return friction;
	}

	private static float calculateCombinedRestitution(CollisionObject body0, CollisionObject body1) {
		return body0.getRestitution() * body1.getRestitution();
	}

	public void refreshContactPoints() {
		assert (manifoldPtr != null);
		if (manifoldPtr.getNumContacts() == 0) {
			return;
		}

		boolean isSwapped = manifoldPtr.getBody0() != body0;

		if (isSwapped) {
			manifoldPtr.refreshContactPoints(rootTransB, rootTransA);
		}
		else {
			manifoldPtr.refreshContactPoints(rootTransA, rootTransB);
		}
	}
}
