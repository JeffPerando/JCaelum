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
import com.elusivehawk.engine.physics.jbullet.BulletStats;
import com.elusivehawk.engine.physics.jbullet.collision.broadphase.BroadphaseInterface;
import com.elusivehawk.engine.physics.jbullet.collision.broadphase.BroadphaseNativeType;
import com.elusivehawk.engine.physics.jbullet.collision.broadphase.BroadphaseProxy;
import com.elusivehawk.engine.physics.jbullet.collision.broadphase.CollisionFilterGroups;
import com.elusivehawk.engine.physics.jbullet.collision.broadphase.Dispatcher;
import com.elusivehawk.engine.physics.jbullet.collision.broadphase.DispatcherInfo;
import com.elusivehawk.engine.physics.jbullet.collision.broadphase.OverlappingPairCache;
import com.elusivehawk.engine.physics.jbullet.collision.narrowphase.ConvexCast;
import com.elusivehawk.engine.physics.jbullet.collision.narrowphase.ConvexCast.CastResult;
import com.elusivehawk.engine.physics.jbullet.collision.narrowphase.GjkConvexCast;
import com.elusivehawk.engine.physics.jbullet.collision.narrowphase.GjkEpaPenetrationDepthSolver;
import com.elusivehawk.engine.physics.jbullet.collision.narrowphase.SubsimplexConvexCast;
import com.elusivehawk.engine.physics.jbullet.collision.narrowphase.TriangleConvexcastCallback;
import com.elusivehawk.engine.physics.jbullet.collision.narrowphase.TriangleRaycastCallback;
import com.elusivehawk.engine.physics.jbullet.collision.narrowphase.VoronoiSimplexSolver;
import com.elusivehawk.engine.physics.jbullet.collision.shapes.BvhTriangleMeshShape;
import com.elusivehawk.engine.physics.jbullet.collision.shapes.CollisionShape;
import com.elusivehawk.engine.physics.jbullet.collision.shapes.CompoundShape;
import com.elusivehawk.engine.physics.jbullet.collision.shapes.ConcaveShape;
import com.elusivehawk.engine.physics.jbullet.collision.shapes.ConvexShape;
import com.elusivehawk.engine.physics.jbullet.collision.shapes.SphereShape;
import com.elusivehawk.engine.physics.jbullet.collision.shapes.TriangleMeshShape;
import com.elusivehawk.engine.physics.jbullet.linearmath.AabbUtil2;
import com.elusivehawk.engine.physics.jbullet.linearmath.IDebugDraw;
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import com.elusivehawk.engine.physics.jbullet.linearmath.TransformUtil;
import com.elusivehawk.engine.physics.jbullet.linearmath.VectorUtil;
import com.elusivehawk.engine.physics.jbullet.util.ObjectArrayList;
import com.elusivehawk.util.math.Matrix;
import com.elusivehawk.util.math.Quaternion;
import com.elusivehawk.util.math.Vector;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * CollisionWorld is interface and container for the collision detection.
 * 
 * @author jezek2
 */
public class CollisionWorld
{
	//protected final BulletStack stack = BulletStack.get();
	
	protected ObjectArrayList<CollisionObject> collisionObjects = new ObjectArrayList<CollisionObject>();
	protected Dispatcher dispatcher1;
	protected DispatcherInfo dispatchInfo = new DispatcherInfo();
	//protected btStackAlloc*	m_stackAlloc;
	protected BroadphaseInterface broadphasePairCache;
	protected IDebugDraw debugDrawer;
	
	/**
	 * This constructor doesn't own the dispatcher and paircache/broadphase.
	 */
	public CollisionWorld(Dispatcher dispatcher,BroadphaseInterface broadphasePairCache, CollisionConfiguration collisionConfiguration) {
		this.dispatcher1 = dispatcher;
		this.broadphasePairCache = broadphasePairCache;
	}
	
	public void destroy() {
		// clean up remaining objects
		for (int i = 0; i < this.collisionObjects.size(); i++) {
			CollisionObject collisionObject = this.collisionObjects.getQuick(i);

			BroadphaseProxy bp = collisionObject.getBroadphaseHandle();
			if (bp != null) {
				//
				// only clear the cached algorithms
				//
				getBroadphase().getOverlappingPairCache().cleanProxyFromPairs(bp, this.dispatcher1);
				getBroadphase().destroyProxy(bp, this.dispatcher1);
			}
		}
	}
	
	public void addCollisionObject(CollisionObject collisionObject) {
		addCollisionObject(collisionObject, CollisionFilterGroups.DEFAULT_FILTER, CollisionFilterGroups.ALL_FILTER);
	}

	public void addCollisionObject(CollisionObject collisionObject, short collisionFilterGroup, short collisionFilterMask) {
		// check that the object isn't already added
		assert (!this.collisionObjects.contains(collisionObject));

		this.collisionObjects.add(collisionObject);

		// calculate new AABB
		// TODO: check if it's overwritten or not
		Transform trans = collisionObject.getWorldTransform(Stack.alloc(Transform.class));

		Vector minAabb = Stack.alloc(new Vector(3));
		Vector maxAabb = Stack.alloc(new Vector(3));
		collisionObject.getCollisionShape().getAabb(trans, minAabb, maxAabb);

		BroadphaseNativeType type = collisionObject.getCollisionShape().getShapeType();
		collisionObject.setBroadphaseHandle(getBroadphase().createProxy(
				minAabb,
				maxAabb,
				type,
				collisionObject,
				collisionFilterGroup,
				collisionFilterMask,
				this.dispatcher1, null));
	}

	public void performDiscreteCollisionDetection() {
		BulletStats.pushProfile("performDiscreteCollisionDetection");
		try {
			//DispatcherInfo dispatchInfo = getDispatchInfo();

			updateAabbs();

			BulletStats.pushProfile("calculateOverlappingPairs");
			try {
				this.broadphasePairCache.calculateOverlappingPairs(this.dispatcher1);
			}
			finally {
				BulletStats.popProfile();
			}

			Dispatcher dispatcher = getDispatcher();
			{
				BulletStats.pushProfile("dispatchAllCollisionPairs");
				try {
					if (dispatcher != null) {
						dispatcher.dispatchAllCollisionPairs(this.broadphasePairCache.getOverlappingPairCache(), this.dispatchInfo, this.dispatcher1);
					}
				}
				finally {
					BulletStats.popProfile();
				}
			}
		}
		finally {
			BulletStats.popProfile();
		}
	}
	
	public void removeCollisionObject(CollisionObject collisionObject) {
		//bool removeFromBroadphase = false;

		{
			BroadphaseProxy bp = collisionObject.getBroadphaseHandle();
			if (bp != null) {
				//
				// only clear the cached algorithms
				//
				getBroadphase().getOverlappingPairCache().cleanProxyFromPairs(bp, this.dispatcher1);
				getBroadphase().destroyProxy(bp, this.dispatcher1);
				collisionObject.setBroadphaseHandle(null);
			}
		}

		//swapremove
		this.collisionObjects.remove(collisionObject);
	}

	public void setBroadphase(BroadphaseInterface pairCache) {
		this.broadphasePairCache = pairCache;
	}
	
	public BroadphaseInterface getBroadphase() {
		return this.broadphasePairCache;
	}
	
	public OverlappingPairCache getPairCache() {
		return this.broadphasePairCache.getOverlappingPairCache();
	}

	public Dispatcher getDispatcher() {
		return this.dispatcher1;
	}

	public DispatcherInfo getDispatchInfo() {
		return this.dispatchInfo;
	}
	
	private static boolean updateAabbs_reportMe = true;

	// JAVA NOTE: ported from 2.74, missing contact threshold stuff
	public void updateSingleAabb(CollisionObject colObj) {
		Vector minAabb = Stack.alloc(new Vector(3)), maxAabb = Stack.alloc(new Vector(3));
		Vector tmp = Stack.alloc(new Vector(3));
		Transform tmpTrans = Stack.alloc(Transform.class);

		colObj.getCollisionShape().getAabb(colObj.getWorldTransform(tmpTrans), minAabb, maxAabb);
		// need to increase the aabb for contact thresholds
		Vector contactThreshold = Stack.alloc(new Vector(3));
		contactThreshold.set(BulletGlobals.getContactBreakingThreshold(), BulletGlobals.getContactBreakingThreshold(), BulletGlobals.getContactBreakingThreshold());
		minAabb.sub(contactThreshold);
		maxAabb.add(contactThreshold);

		BroadphaseInterface bp = this.broadphasePairCache;

		// moving objects should be moderately sized, probably something wrong if not
		tmp.sub(maxAabb, minAabb); // TODO: optimize
		if (colObj.isStaticObject() || (tmp.lengthSquared() < 1e12f)) {
			bp.setAabb(colObj.getBroadphaseHandle(), minAabb, maxAabb, this.dispatcher1);
		}
		else {
			// something went wrong, investigate
			// this assert is unwanted in 3D modelers (danger of loosing work)
			colObj.setActivationState(CollisionObject.DISABLE_SIMULATION);

			if (updateAabbs_reportMe && this.debugDrawer != null) {
				updateAabbs_reportMe = false;
				this.debugDrawer.reportErrorWarning("Overflow in AABB, object removed from simulation");
				this.debugDrawer.reportErrorWarning("If you can reproduce this, please email bugs@continuousphysics.com\n");
				this.debugDrawer.reportErrorWarning("Please include above information, your Platform, version of OS.\n");
				this.debugDrawer.reportErrorWarning("Thanks.\n");
			}
		}
	}

	public void updateAabbs() {
		BulletStats.pushProfile("updateAabbs");
		try {
			for (int i=0; i<this.collisionObjects.size(); i++) {
				CollisionObject colObj = this.collisionObjects.getQuick(i);

				// only update aabb of active objects
				if (colObj.isActive()) {
					updateSingleAabb(colObj);
				}
			}
		}
		finally {
			BulletStats.popProfile();
		}
	}

	public IDebugDraw getDebugDrawer() {
		return this.debugDrawer;
	}

	public void setDebugDrawer(IDebugDraw debugDrawer) {
		this.debugDrawer = debugDrawer;
	}
	
	public int getNumCollisionObjects() {
		return this.collisionObjects.size();
	}

	// TODO
	public static void rayTestSingle(Transform rayFromTrans, Transform rayToTrans,
			CollisionObject collisionObject,
			CollisionShape collisionShape,
			Transform colObjWorldTransform,
			RayResultCallback resultCallback) {
		SphereShape pointShape = new SphereShape(0f);
		pointShape.setMargin(0f);
		ConvexShape castShape = pointShape;

		if (collisionShape.isConvex()) {
			CastResult castResult = new CastResult();
			castResult.fraction = resultCallback.closestHitFraction;

			ConvexShape convexShape = (ConvexShape) collisionShape;
			VoronoiSimplexSolver simplexSolver = new VoronoiSimplexSolver();

			//#define USE_SUBSIMPLEX_CONVEX_CAST 1
			//#ifdef USE_SUBSIMPLEX_CONVEX_CAST
			SubsimplexConvexCast convexCaster = new SubsimplexConvexCast(castShape, convexShape, simplexSolver);
			//#else
			//btGjkConvexCast	convexCaster(castShape,convexShape,&simplexSolver);
			//btContinuousConvexCollision convexCaster(castShape,convexShape,&simplexSolver,0);
			//#endif //#USE_SUBSIMPLEX_CONVEX_CAST

			if (convexCaster.calcTimeOfImpact(rayFromTrans, rayToTrans, colObjWorldTransform, colObjWorldTransform, castResult)) {
				//add hit
				if (castResult.normal.lengthSquared() > 0.0001f) {
					if (castResult.fraction < resultCallback.closestHitFraction) {
						//#ifdef USE_SUBSIMPLEX_CONVEX_CAST
						//rotate normal into worldspace
						rayFromTrans.basis.transform(castResult.normal);
						//#endif //USE_SUBSIMPLEX_CONVEX_CAST

						castResult.normal.normalize();
						LocalRayResult localRayResult = new LocalRayResult(
								collisionObject,
								null,
								castResult.normal,
								castResult.fraction);

						boolean normalInWorldSpace = true;
						resultCallback.addSingleResult(localRayResult, normalInWorldSpace);
					}
				}
			}
		}
		else {
			if (collisionShape.isConcave()) {
				if (collisionShape.getShapeType() == BroadphaseNativeType.TRIANGLE_MESH_SHAPE_PROXYTYPE) {
					// optimized version for BvhTriangleMeshShape
					BvhTriangleMeshShape triangleMesh = (BvhTriangleMeshShape)collisionShape;
					Transform worldTocollisionObject = Stack.alloc(Transform.class);
					worldTocollisionObject.inverse(colObjWorldTransform);
					Vector rayFromLocal = Stack.alloc(rayFromTrans.origin);
					worldTocollisionObject.transform(rayFromLocal);
					Vector rayToLocal = Stack.alloc(rayToTrans.origin);
					worldTocollisionObject.transform(rayToLocal);

					BridgeTriangleRaycastCallback rcb = new BridgeTriangleRaycastCallback(rayFromLocal, rayToLocal, resultCallback, collisionObject, triangleMesh);
					rcb.hitFraction = resultCallback.closestHitFraction;
					triangleMesh.performRaycast(rcb, rayFromLocal, rayToLocal);
				}
				else {
					ConcaveShape triangleMesh = (ConcaveShape)collisionShape;

					Transform worldTocollisionObject = Stack.alloc(Transform.class);
					worldTocollisionObject.inverse(colObjWorldTransform);

					Vector rayFromLocal = Stack.alloc(rayFromTrans.origin);
					worldTocollisionObject.transform(rayFromLocal);
					Vector rayToLocal = Stack.alloc(rayToTrans.origin);
					worldTocollisionObject.transform(rayToLocal);

					BridgeTriangleRaycastCallback rcb = new BridgeTriangleRaycastCallback(rayFromLocal, rayToLocal, resultCallback, collisionObject, triangleMesh);
					rcb.hitFraction = resultCallback.closestHitFraction;

					Vector rayAabbMinLocal = Stack.alloc(rayFromLocal);
					VectorUtil.setMin(rayAabbMinLocal, rayToLocal);
					Vector rayAabbMaxLocal = Stack.alloc(rayFromLocal);
					VectorUtil.setMax(rayAabbMaxLocal, rayToLocal);

					triangleMesh.processAllTriangles(rcb, rayAabbMinLocal, rayAabbMaxLocal);
				}
			}
			else {
				// todo: use AABB tree or other BVH acceleration structure!
				if (collisionShape.isCompound()) {
					CompoundShape compoundShape = (CompoundShape) collisionShape;
					int i = 0;
					Transform childTrans = Stack.alloc(Transform.class);
					for (i = 0; i < compoundShape.getNumChildShapes(); i++) {
						compoundShape.getChildTransform(i, childTrans);
						CollisionShape childCollisionShape = compoundShape.getChildShape(i);
						Transform childWorldTrans = Stack.alloc(colObjWorldTransform);
						childWorldTrans.mul(childTrans);
						// replace collision shape so that callback can determine the triangle
						CollisionShape saveCollisionShape = collisionObject.getCollisionShape();
						collisionObject.internalSetTemporaryCollisionShape(childCollisionShape);
						rayTestSingle(rayFromTrans, rayToTrans,
								collisionObject,
								childCollisionShape,
								childWorldTrans,
								resultCallback);
						// restore
						collisionObject.internalSetTemporaryCollisionShape(saveCollisionShape);
					}
				}
			}
		}
	}

	private static class BridgeTriangleConvexcastCallback extends TriangleConvexcastCallback {
		public ConvexResultCallback resultCallback;
		public CollisionObject collisionObject;
		public TriangleMeshShape triangleMesh;
		public boolean normalInWorldSpace;

		public BridgeTriangleConvexcastCallback(ConvexShape castShape, Transform from, Transform to, ConvexResultCallback resultCallback, CollisionObject collisionObject, TriangleMeshShape triangleMesh, Transform triangleToWorld) {
			super(castShape, from, to, triangleToWorld, triangleMesh.getMargin());
			this.resultCallback = resultCallback;
			this.collisionObject = collisionObject;
			this.triangleMesh = triangleMesh;
		}

		@Override
		public float reportHit(Vector hitNormalLocal, Vector hitPointLocal, float hitFraction, int partId, int triangleIndex)
		{
			LocalShapeInfo shapeInfo = new LocalShapeInfo();
			shapeInfo.shapePart = partId;
			shapeInfo.triangleIndex = triangleIndex;
			if (hitFraction <= this.resultCallback.closestHitFraction) {
				LocalConvexResult convexResult = new LocalConvexResult(this.collisionObject, shapeInfo, hitNormalLocal, hitPointLocal, hitFraction);
				return this.resultCallback.addSingleResult(convexResult, this.normalInWorldSpace);
			}
			return hitFraction;
		}
	}

	/**
	 * objectQuerySingle performs a collision detection query and calls the resultCallback. It is used internally by rayTest.
	 */
	public static void objectQuerySingle(ConvexShape castShape, Transform convexFromTrans, Transform convexToTrans, CollisionObject collisionObject, CollisionShape collisionShape, Transform colObjWorldTransform, ConvexResultCallback resultCallback, float allowedPenetration) {
		if (collisionShape.isConvex()) {
			CastResult castResult = new CastResult();
			castResult.allowedPenetration = allowedPenetration;
			castResult.fraction = 1f; // ??

			ConvexShape convexShape = (ConvexShape) collisionShape;
			VoronoiSimplexSolver simplexSolver = new VoronoiSimplexSolver();
			GjkEpaPenetrationDepthSolver gjkEpaPenetrationSolver = new GjkEpaPenetrationDepthSolver();

			// JAVA TODO: should be convexCaster1
			//ContinuousConvexCollision convexCaster1(castShape,convexShape,&simplexSolver,&gjkEpaPenetrationSolver);
			GjkConvexCast convexCaster2 = new GjkConvexCast(castShape, convexShape, simplexSolver);
			//btSubsimplexConvexCast convexCaster3(castShape,convexShape,&simplexSolver);

			ConvexCast castPtr = convexCaster2;

			if (castPtr.calcTimeOfImpact(convexFromTrans, convexToTrans, colObjWorldTransform, colObjWorldTransform, castResult)) {
				// add hit
				if (castResult.normal.lengthSquared() > 0.0001f) {
					if (castResult.fraction < resultCallback.closestHitFraction) {
						castResult.normal.normalize();
						LocalConvexResult localConvexResult = new LocalConvexResult(collisionObject, null, castResult.normal, castResult.hitPoint, castResult.fraction);

						boolean normalInWorldSpace = true;
						resultCallback.addSingleResult(localConvexResult, normalInWorldSpace);
					}
				}
			}
		}
		else {
			if (collisionShape.isConcave()) {
				if (collisionShape.getShapeType() == BroadphaseNativeType.TRIANGLE_MESH_SHAPE_PROXYTYPE) {
					BvhTriangleMeshShape triangleMesh = (BvhTriangleMeshShape)collisionShape;
					Transform worldTocollisionObject = Stack.alloc(Transform.class);
					worldTocollisionObject.inverse(colObjWorldTransform);

					Vector convexFromLocal = Stack.alloc(new Vector(3));
					convexFromLocal.set(convexFromTrans.origin);
					worldTocollisionObject.transform(convexFromLocal);

					Vector convexToLocal = Stack.alloc(new Vector(3));
					convexToLocal.set(convexToTrans.origin);
					worldTocollisionObject.transform(convexToLocal);

					// rotation of box in local mesh space = MeshRotation^-1 * ConvexToRotation
					Transform rotationXform = Stack.alloc(Transform.class);
					Matrix tmpMat = Stack.alloc(new Matrix(3, 3));
					tmpMat.mul(worldTocollisionObject.basis, convexToTrans.basis);
					rotationXform.set(tmpMat);

					BridgeTriangleConvexcastCallback tccb = new BridgeTriangleConvexcastCallback(castShape, convexFromTrans, convexToTrans, resultCallback, collisionObject, triangleMesh, colObjWorldTransform);
					tccb.hitFraction = resultCallback.closestHitFraction;
					tccb.normalInWorldSpace = true;
					
					Vector boxMinLocal = Stack.alloc(new Vector(3));
					Vector boxMaxLocal = Stack.alloc(new Vector(3));
					castShape.getAabb(rotationXform, boxMinLocal, boxMaxLocal);
					triangleMesh.performConvexcast(tccb, convexFromLocal, convexToLocal, boxMinLocal, boxMaxLocal);
				}
				else {
					BvhTriangleMeshShape triangleMesh = (BvhTriangleMeshShape)collisionShape;
					Transform worldTocollisionObject = Stack.alloc(Transform.class);
					worldTocollisionObject.inverse(colObjWorldTransform);

					Vector convexFromLocal = Stack.alloc(new Vector(3));
					convexFromLocal.set(convexFromTrans.origin);
					worldTocollisionObject.transform(convexFromLocal);

					Vector convexToLocal = Stack.alloc(new Vector(3));
					convexToLocal.set(convexToTrans.origin);
					worldTocollisionObject.transform(convexToLocal);

					// rotation of box in local mesh space = MeshRotation^-1 * ConvexToRotation
					Transform rotationXform = Stack.alloc(Transform.class);
					Matrix tmpMat = Stack.alloc(new Matrix(3, 3));
					tmpMat.mul(worldTocollisionObject.basis, convexToTrans.basis);
					rotationXform.set(tmpMat);

					BridgeTriangleConvexcastCallback tccb = new BridgeTriangleConvexcastCallback(castShape, convexFromTrans, convexToTrans, resultCallback, collisionObject, triangleMesh, colObjWorldTransform);
					tccb.hitFraction = resultCallback.closestHitFraction;
					tccb.normalInWorldSpace = false;
					Vector boxMinLocal = Stack.alloc(new Vector(3));
					Vector boxMaxLocal = Stack.alloc(new Vector(3));
					castShape.getAabb(rotationXform, boxMinLocal, boxMaxLocal);

					Vector rayAabbMinLocal = Stack.alloc(convexFromLocal);
					VectorUtil.setMin(rayAabbMinLocal, convexToLocal);
					Vector rayAabbMaxLocal = Stack.alloc(convexFromLocal);
					VectorUtil.setMax(rayAabbMaxLocal, convexToLocal);
					rayAabbMinLocal.add(boxMinLocal);
					rayAabbMaxLocal.add(boxMaxLocal);
					triangleMesh.processAllTriangles(tccb, rayAabbMinLocal, rayAabbMaxLocal);
				}
			}
			else {
				// todo: use AABB tree or other BVH acceleration structure!
				if (collisionShape.isCompound()) {
					CompoundShape compoundShape = (CompoundShape) collisionShape;
					for (int i = 0; i < compoundShape.getNumChildShapes(); i++) {
						Transform childTrans = compoundShape.getChildTransform(i, Stack.alloc(Transform.class));
						CollisionShape childCollisionShape = compoundShape.getChildShape(i);
						Transform childWorldTrans = Stack.alloc(Transform.class);
						childWorldTrans.mul(colObjWorldTransform, childTrans);
						// replace collision shape so that callback can determine the triangle
						CollisionShape saveCollisionShape = collisionObject.getCollisionShape();
						collisionObject.internalSetTemporaryCollisionShape(childCollisionShape);
						objectQuerySingle(castShape, convexFromTrans, convexToTrans,
						                  collisionObject,
						                  childCollisionShape,
						                  childWorldTrans,
						                  resultCallback, allowedPenetration);
						// restore
						collisionObject.internalSetTemporaryCollisionShape(saveCollisionShape);
					}
				}
			}
		}
	}

	/**
	 * rayTest performs a raycast on all objects in the CollisionWorld, and calls the resultCallback.
	 * This allows for several queries: first hit, all hits, any hit, dependent on the value returned by the callback.
	 */
	public void rayTest(Vector rayFromWorld, Vector rayToWorld, RayResultCallback resultCallback) {
		Transform rayFromTrans = Stack.alloc(Transform.class), rayToTrans = Stack.alloc(Transform.class);
		rayFromTrans.setIdentity();
		rayFromTrans.origin.set(rayFromWorld);
		rayToTrans.setIdentity();

		rayToTrans.origin.set(rayToWorld);

		// go over all objects, and if the ray intersects their aabb, do a ray-shape query using convexCaster (CCD)
		Vector collisionObjectAabbMin = Stack.alloc(new Vector(3)), collisionObjectAabbMax = Stack.alloc(new Vector(3));
		float[] hitLambda = new float[1];

		Transform tmpTrans = Stack.alloc(Transform.class);
		
		for (int i = 0; i < this.collisionObjects.size(); i++) {
			// terminate further ray tests, once the closestHitFraction reached zero
			if (resultCallback.closestHitFraction == 0f) {
				break;
			}

			CollisionObject collisionObject = this.collisionObjects.getQuick(i);
			// only perform raycast if filterMask matches
			if (resultCallback.needsCollision(collisionObject.getBroadphaseHandle())) {
				//RigidcollisionObject* collisionObject = ctrl->GetRigidcollisionObject();
				collisionObject.getCollisionShape().getAabb(collisionObject.getWorldTransform(tmpTrans), collisionObjectAabbMin, collisionObjectAabbMax);

				hitLambda[0] = resultCallback.closestHitFraction;
				Vector hitNormal = Stack.alloc(new Vector(3));
				if (AabbUtil2.rayAabb(rayFromWorld, rayToWorld, collisionObjectAabbMin, collisionObjectAabbMax, hitLambda, hitNormal)) {
					rayTestSingle(rayFromTrans, rayToTrans,
							collisionObject,
							collisionObject.getCollisionShape(),
							collisionObject.getWorldTransform(tmpTrans),
							resultCallback);
				}
			}

		}
	}

	/**
	 * convexTest performs a swept convex cast on all objects in the {@link CollisionWorld}, and calls the resultCallback
	 * This allows for several queries: first hit, all hits, any hit, dependent on the value return by the callback.
	 */
	public void convexSweepTest(ConvexShape castShape, Transform convexFromWorld, Transform convexToWorld, ConvexResultCallback resultCallback) {
		Transform convexFromTrans = Stack.alloc(Transform.class);
		Transform convexToTrans = Stack.alloc(Transform.class);

		convexFromTrans.set(convexFromWorld);
		convexToTrans.set(convexToWorld);

		Vector castShapeAabbMin = Stack.alloc(new Vector(3));
		Vector castShapeAabbMax = Stack.alloc(new Vector(3));

		// Compute AABB that encompasses angular movement
		{
			Vector linVel = Stack.alloc(new Vector(3));
			Vector angVel = Stack.alloc(new Vector(3));
			TransformUtil.calculateVelocity(convexFromTrans, convexToTrans, 1f, linVel, angVel);
			Transform R = Stack.alloc(Transform.class);
			R.setIdentity();
			R.setRotation(convexFromTrans.getRotation(Stack.alloc(Quaternion.class)));
			castShape.calculateTemporalAabb(R, linVel, angVel, 1f, castShapeAabbMin, castShapeAabbMax);
		}

		Transform tmpTrans = Stack.alloc(Transform.class);
		Vector collisionObjectAabbMin = Stack.alloc(new Vector(3));
		Vector collisionObjectAabbMax = Stack.alloc(new Vector(3));
		float[] hitLambda = new float[1];

		// go over all objects, and if the ray intersects their aabb + cast shape aabb,
		// do a ray-shape query using convexCaster (CCD)
		for (int i = 0; i < this.collisionObjects.size(); i++) {
			CollisionObject collisionObject = this.collisionObjects.getQuick(i);

			// only perform raycast if filterMask matches
			if (resultCallback.needsCollision(collisionObject.getBroadphaseHandle())) {
				//RigidcollisionObject* collisionObject = ctrl->GetRigidcollisionObject();
				collisionObject.getWorldTransform(tmpTrans);
				collisionObject.getCollisionShape().getAabb(tmpTrans, collisionObjectAabbMin, collisionObjectAabbMax);
				AabbUtil2.aabbExpand(collisionObjectAabbMin, collisionObjectAabbMax, castShapeAabbMin, castShapeAabbMax);
				hitLambda[0] = 1f; // could use resultCallback.closestHitFraction, but needs testing
				Vector hitNormal = Stack.alloc(new Vector(3));
				if (AabbUtil2.rayAabb(convexFromWorld.origin, convexToWorld.origin, collisionObjectAabbMin, collisionObjectAabbMax, hitLambda, hitNormal)) {
					objectQuerySingle(castShape, convexFromTrans, convexToTrans,
					                  collisionObject,
					                  collisionObject.getCollisionShape(),
					                  tmpTrans,
					                  resultCallback,
					                  getDispatchInfo().allowedCcdPenetration);
				}
			}
		}
	}

	public ObjectArrayList<CollisionObject> getCollisionObjectArray() {
		return this.collisionObjects;
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	/**
	 * LocalShapeInfo gives extra information for complex shapes.
	 * Currently, only btTriangleMeshShape is available, so it just contains triangleIndex and subpart.
	 */
	public static class LocalShapeInfo {
		public int shapePart;
		public int triangleIndex;
		//const btCollisionShape*	m_shapeTemp;
		//const btTransform*	m_shapeLocalTransform;
	}
	
	public static class LocalRayResult {
		public CollisionObject collisionObject;
		public LocalShapeInfo localShapeInfo;
		public final Vector hitNormalLocal = new Vector(3);
		public float hitFraction;

		public LocalRayResult(CollisionObject collisionObject, LocalShapeInfo localShapeInfo, Vector hitNormalLocal, float hitFraction) {
			this.collisionObject = collisionObject;
			this.localShapeInfo = localShapeInfo;
			this.hitNormalLocal.set(hitNormalLocal);
			this.hitFraction = hitFraction;
		}
	}
	
	/**
	 * RayResultCallback is used to report new raycast results.
	 */
	public static abstract class RayResultCallback {
		public float closestHitFraction = 1f;
		public CollisionObject collisionObject;
		public short collisionFilterGroup = CollisionFilterGroups.DEFAULT_FILTER;
		public short collisionFilterMask = CollisionFilterGroups.ALL_FILTER;
		
		public boolean hasHit() {
			return (this.collisionObject != null);
		}

		public boolean needsCollision(BroadphaseProxy proxy0) {
			boolean collides = ((proxy0.collisionFilterGroup & this.collisionFilterMask) & 0xFFFF) != 0;
			collides = collides && ((this.collisionFilterGroup & proxy0.collisionFilterMask) & 0xFFFF) != 0;
			return collides;
		}
		
		public abstract float addSingleResult(LocalRayResult rayResult, boolean normalInWorldSpace);
	}
	
	public static class ClosestRayResultCallback extends RayResultCallback {
		public final Vector rayFromWorld = new Vector(3); //used to calculate hitPointWorld from hitFraction
		public final Vector rayToWorld = new Vector(3);

		public final Vector hitNormalWorld = new Vector(3);
		public final Vector hitPointWorld = new Vector(3);
		
		public ClosestRayResultCallback(Vector rayFromWorld, Vector rayToWorld) {
			this.rayFromWorld.set(rayFromWorld);
			this.rayToWorld.set(rayToWorld);
		}
		
		@Override
		public float addSingleResult(LocalRayResult rayResult, boolean normalInWorldSpace) {
			// caller already does the filter on the closestHitFraction
			assert (rayResult.hitFraction <= this.closestHitFraction);

			this.closestHitFraction = rayResult.hitFraction;
			this.collisionObject = rayResult.collisionObject;
			if (normalInWorldSpace) {
				this.hitNormalWorld.set(rayResult.hitNormalLocal);
			}
			else {
				// need to transform normal into worldspace
				this.hitNormalWorld.set(rayResult.hitNormalLocal);
				this.collisionObject.getWorldTransform(Stack.alloc(Transform.class)).basis.transform(this.hitNormalWorld);
			}

			VectorUtil.setInterpolate(this.hitPointWorld, this.rayFromWorld, this.rayToWorld, rayResult.hitFraction);
			return rayResult.hitFraction;
		}
	}
	
	public static class LocalConvexResult {
		public CollisionObject hitCollisionObject;
		public LocalShapeInfo localShapeInfo;
		public final Vector hitNormalLocal = new Vector(3);
		public final Vector hitPointLocal = new Vector(3);
		public float hitFraction;

		public LocalConvexResult(CollisionObject hitCollisionObject, LocalShapeInfo localShapeInfo, Vector hitNormalLocal, Vector hitPointLocal, float hitFraction) {
			this.hitCollisionObject = hitCollisionObject;
			this.localShapeInfo = localShapeInfo;
			this.hitNormalLocal.set(hitNormalLocal);
			this.hitPointLocal.set(hitPointLocal);
			this.hitFraction = hitFraction;
		}
	}
	
	public static abstract class ConvexResultCallback {
		public float closestHitFraction = 1f;
		public short collisionFilterGroup = CollisionFilterGroups.DEFAULT_FILTER;
		public short collisionFilterMask = CollisionFilterGroups.ALL_FILTER;
		
		public boolean hasHit() {
			return (this.closestHitFraction < 1f);
		}
		
		public boolean needsCollision(BroadphaseProxy proxy0) {
			boolean collides = ((proxy0.collisionFilterGroup & this.collisionFilterMask) & 0xFFFF) != 0;
			collides = collides && ((this.collisionFilterGroup & proxy0.collisionFilterMask) & 0xFFFF) != 0;
			return collides;
		}
		
		public abstract float addSingleResult(LocalConvexResult convexResult, boolean normalInWorldSpace);
	}
	
	public static class ClosestConvexResultCallback extends ConvexResultCallback {
		public final Vector convexFromWorld = new Vector(3); // used to calculate hitPointWorld from hitFraction
		public final Vector convexToWorld = new Vector(3);
		public final Vector hitNormalWorld = new Vector(3);
		public final Vector hitPointWorld = new Vector(3);
		public CollisionObject hitCollisionObject;

		public ClosestConvexResultCallback(Vector convexFromWorld, Vector convexToWorld) {
			this.convexFromWorld.set(convexFromWorld);
			this.convexToWorld.set(convexToWorld);
			this.hitCollisionObject = null;
		}

		@Override
		public float addSingleResult(LocalConvexResult convexResult, boolean normalInWorldSpace) {
			// caller already does the filter on the m_closestHitFraction
			assert (convexResult.hitFraction <= this.closestHitFraction);

			this.closestHitFraction = convexResult.hitFraction;
			this.hitCollisionObject = convexResult.hitCollisionObject;
			if (normalInWorldSpace) {
				this.hitNormalWorld.set(convexResult.hitNormalLocal);
				if (this.hitNormalWorld.length() > 2) {
					System.out.println("CollisionWorld.addSingleResult world " + this.hitNormalWorld);
				}
			}
			else {
				// need to transform normal into worldspace
				this.hitNormalWorld.set(convexResult.hitNormalLocal);
				this.hitCollisionObject.getWorldTransform(Stack.alloc(Transform.class)).basis.transform(this.hitNormalWorld);
				if (this.hitNormalWorld.length() > 2) {
					System.out.println("CollisionWorld.addSingleResult world " + this.hitNormalWorld);
				}
			}

			this.hitPointWorld.set(convexResult.hitPointLocal);
			return convexResult.hitFraction;
		}
	}
	
	private static class BridgeTriangleRaycastCallback extends TriangleRaycastCallback {
		public RayResultCallback resultCallback;
		public CollisionObject collisionObject;
		public ConcaveShape triangleMesh;

		public BridgeTriangleRaycastCallback(Vector from, Vector to, RayResultCallback resultCallback, CollisionObject collisionObject, ConcaveShape triangleMesh) {
			super(from, to);
			this.resultCallback = resultCallback;
			this.collisionObject = collisionObject;
			this.triangleMesh = triangleMesh;
		}
	
		public float reportHit(Vector hitNormalLocal, float hitFraction, int partId, int triangleIndex) {
			LocalShapeInfo shapeInfo = new LocalShapeInfo();
			shapeInfo.shapePart = partId;
			shapeInfo.triangleIndex = triangleIndex;

			LocalRayResult rayResult = new LocalRayResult(this.collisionObject, shapeInfo, hitNormalLocal, hitFraction);

			boolean normalInWorldSpace = false;
			return this.resultCallback.addSingleResult(rayResult, normalInWorldSpace);
		}
	}
	
}
