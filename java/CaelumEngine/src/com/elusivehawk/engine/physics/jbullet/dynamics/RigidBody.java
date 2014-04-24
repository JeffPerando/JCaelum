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

package com.elusivehawk.engine.physics.jbullet.dynamics;

import static com.elusivehawk.engine.math.MathConst.X;
import static com.elusivehawk.engine.math.MathConst.Y;
import static com.elusivehawk.engine.math.MathConst.Z;
import com.elusivehawk.engine.math.Matrix;
import com.elusivehawk.engine.math.Quaternion;
import com.elusivehawk.engine.math.Vector;
import com.elusivehawk.engine.physics.jbullet.BulletGlobals;
import com.elusivehawk.engine.physics.jbullet.collision.broadphase.BroadphaseProxy;
import com.elusivehawk.engine.physics.jbullet.collision.dispatch.CollisionFlags;
import com.elusivehawk.engine.physics.jbullet.collision.dispatch.CollisionObject;
import com.elusivehawk.engine.physics.jbullet.collision.dispatch.CollisionObjectType;
import com.elusivehawk.engine.physics.jbullet.collision.shapes.CollisionShape;
import com.elusivehawk.engine.physics.jbullet.dynamics.constraintsolver.TypedConstraint;
import com.elusivehawk.engine.physics.jbullet.linearmath.MatrixUtil;
import com.elusivehawk.engine.physics.jbullet.linearmath.MiscUtil;
import com.elusivehawk.engine.physics.jbullet.linearmath.MotionState;
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import com.elusivehawk.engine.physics.jbullet.linearmath.TransformUtil;
import com.elusivehawk.engine.physics.jbullet.util.ObjectArrayList;
import cz.advel.stack.Stack;
import cz.advel.stack.StaticAlloc;

/**
 * RigidBody is the main class for rigid body objects. It is derived from
 * {@link CollisionObject}, so it keeps reference to {@link CollisionShape}.<p>
 * 
 * It is recommended for performance and memory use to share {@link CollisionShape}
 * objects whenever possible.<p>
 * 
 * There are 3 types of rigid bodies:<br>
 * <ol>
 * <li>Dynamic rigid bodies, with positive mass. Motion is controlled by rigid body dynamics.</li>
 * <li>Fixed objects with zero mass. They are not moving (basically collision objects).</li>
 * <li>Kinematic objects, which are objects without mass, but the user can move them. There
 *     is on-way interaction, and Bullet calculates a velocity based on the timestep and
 *     previous and current world transform.</li>
 * </ol>
 * 
 * Bullet automatically deactivates dynamic rigid bodies, when the velocity is below
 * a threshold for a given time.<p>
 * 
 * Deactivated (sleeping) rigid bodies don't take any processing time, except a minor
 * broadphase collision detection impact (to allow active objects to activate/wake up
 * sleeping objects).
 * 
 * @author jezek2
 */
public class RigidBody extends CollisionObject {

	private static final float MAX_ANGVEL = BulletGlobals.SIMD_HALF_PI;
	
	private final Matrix invInertiaTensorWorld = new Matrix(3, 3);
	private final Vector linearVelocity = new Vector(3);
	private final Vector angularVelocity = new Vector(3);
	private float inverseMass;
	private float angularFactor;

	private final Vector gravity = new Vector(3);
	private final Vector invInertiaLocal = new Vector(3);
	private final Vector totalForce = new Vector(3);
	private final Vector totalTorque = new Vector(3);
	
	private float linearDamping;
	private float angularDamping;

	private boolean additionalDamping;
	private float additionalDampingFactor;
	private float additionalLinearDampingThresholdSqr;
	private float additionalAngularDampingThresholdSqr;
	private float additionalAngularDampingFactor;

	private float linearSleepingThreshold;
	private float angularSleepingThreshold;

	// optionalMotionState allows to automatic synchronize the world transform for active objects
	private MotionState optionalMotionState;

	// keep track of typed constraints referencing this rigid body
	private final ObjectArrayList<TypedConstraint> constraintRefs = new ObjectArrayList<TypedConstraint>();

	// for experimental overriding of friction/contact solver func
	public int contactSolverType;
	public int frictionSolverType;
	
	private static int uniqueId = 0;
	public int debugBodyId;
	
	public RigidBody(RigidBodyConstructionInfo constructionInfo) {
		setupRigidBody(constructionInfo);
	}

	public RigidBody(float mass, MotionState motionState, CollisionShape collisionShape) {
		this(mass, motionState, collisionShape, new Vector(0f, 0f, 0f));
	}
	
	public RigidBody(float mass, MotionState motionState, CollisionShape collisionShape, Vector localInertia) {
		RigidBodyConstructionInfo cinfo = new RigidBodyConstructionInfo(mass, motionState, collisionShape, localInertia);
		setupRigidBody(cinfo);
	}
	
	private void setupRigidBody(RigidBodyConstructionInfo constructionInfo) {
		internalType = CollisionObjectType.RIGID_BODY;
		
		linearVelocity.set(0f, 0f, 0f);
		angularVelocity.set(0f, 0f, 0f);
		angularFactor = 1f;
		gravity.set(0f, 0f, 0f);
		totalForce.set(0f, 0f, 0f);
		totalTorque.set(0f, 0f, 0f);
		linearDamping = 0f;
		angularDamping = 0.5f;
		linearSleepingThreshold = constructionInfo.linearSleepingThreshold;
		angularSleepingThreshold = constructionInfo.angularSleepingThreshold;
		optionalMotionState = constructionInfo.motionState;
		contactSolverType = 0;
		frictionSolverType = 0;
		additionalDamping = constructionInfo.additionalDamping;
		additionalDampingFactor = constructionInfo.additionalDampingFactor;
		additionalLinearDampingThresholdSqr = constructionInfo.additionalLinearDampingThresholdSqr;
		additionalAngularDampingThresholdSqr = constructionInfo.additionalAngularDampingThresholdSqr;
		additionalAngularDampingFactor = constructionInfo.additionalAngularDampingFactor;

		if (optionalMotionState != null)
		{
			optionalMotionState.getWorldTransform(worldTransform);
		} else
		{
			worldTransform.set(constructionInfo.startWorldTransform);
		}

		interpolationWorldTransform.set(worldTransform);
		interpolationLinearVelocity.set(0f, 0f, 0f);
		interpolationAngularVelocity.set(0f, 0f, 0f);

		// moved to CollisionObject
		friction = constructionInfo.friction;
		restitution = constructionInfo.restitution;

		setCollisionShape(constructionInfo.collisionShape);
		debugBodyId = uniqueId++;

		setMassProps(constructionInfo.mass, constructionInfo.localInertia);
		setDamping(constructionInfo.linearDamping, constructionInfo.angularDamping);
		updateInertiaTensor();
	}
	
	public void destroy() {
		// No constraints should point to this rigidbody
		// Remove constraints from the dynamics world before you delete the related rigidbodies. 
		assert (constraintRefs.size() == 0);
	}

	public void proceedToTransform(Transform newTrans) {
		setCenterOfMassTransform(newTrans);
	}
	
	/**
	 * To keep collision detection and dynamics separate we don't store a rigidbody pointer,
	 * but a rigidbody is derived from CollisionObject, so we can safely perform an upcast.
	 */
	public static RigidBody upcast(CollisionObject colObj) {
		if (colObj.getInternalType() == CollisionObjectType.RIGID_BODY) {
			return (RigidBody)colObj;
		}
		return null;
	}

	/**
	 * Continuous collision detection needs prediction.
	 */
	public void predictIntegratedTransform(float timeStep, Transform predictedTransform) {
		TransformUtil.integrateTransform(worldTransform, linearVelocity, angularVelocity, timeStep, predictedTransform);
	}
	
	public void saveKinematicState(float timeStep) {
		//todo: clamp to some (user definable) safe minimum timestep, to limit maximum angular/linear velocities
		if (timeStep != 0f) {
			//if we use motionstate to synchronize world transforms, get the new kinematic/animated world transform
			if (getMotionState() != null) {
				getMotionState().getWorldTransform(worldTransform);
			}
			//Vector linVel = new Vector(3), angVel = new Vector(3);

			TransformUtil.calculateVelocity(interpolationWorldTransform, worldTransform, timeStep, linearVelocity, angularVelocity);
			interpolationLinearVelocity.set(linearVelocity);
			interpolationAngularVelocity.set(angularVelocity);
			interpolationWorldTransform.set(worldTransform);
		//printf("angular = %f %f %f\n",m_angularVelocity.getX(),m_angularVelocity.getY(),m_angularVelocity.getZ());
		}
	}
	
	public void applyGravity() {
		if (isStaticOrKinematicObject())
			return;

		applyCentralForce(gravity);
	}
	
	public void setGravity(Vector acceleration) {
		if (inverseMass != 0f) {
			gravity.scale(1f / inverseMass, acceleration);
		}
	}

	public Vector getGravity(Vector out) {
		out.set(gravity);
		return out;
	}

	public void setDamping(float lin_damping, float ang_damping) {
		linearDamping = MiscUtil.GEN_clamped(lin_damping, 0f, 1f);
		angularDamping = MiscUtil.GEN_clamped(ang_damping, 0f, 1f);
	}

	public float getLinearDamping() {
		return linearDamping;
	}

	public float getAngularDamping() {
		return angularDamping;
	}

	public float getLinearSleepingThreshold() {
		return linearSleepingThreshold;
	}

	public float getAngularSleepingThreshold() {
		return angularSleepingThreshold;
	}

	/**
	 * Damps the velocity, using the given linearDamping and angularDamping.
	 */
	public void applyDamping(float timeStep) {
		// On new damping: see discussion/issue report here: http://code.google.com/p/bullet/issues/detail?id=74
		// todo: do some performance comparisons (but other parts of the engine are probably bottleneck anyway

		//#define USE_OLD_DAMPING_METHOD 1
		//#ifdef USE_OLD_DAMPING_METHOD
		//linearVelocity.scale(MiscUtil.GEN_clamped((1f - timeStep * linearDamping), 0f, 1f));
		//angularVelocity.scale(MiscUtil.GEN_clamped((1f - timeStep * angularDamping), 0f, 1f));
		//#else
		linearVelocity.scale((float)Math.pow(1f - linearDamping, timeStep));
		angularVelocity.scale((float)Math.pow(1f - angularDamping, timeStep));
		//#endif

		if (additionalDamping) {
			// Additional damping can help avoiding lowpass jitter motion, help stability for ragdolls etc.
			// Such damping is undesirable, so once the overall simulation quality of the rigid body dynamics system has improved, this should become obsolete
			if ((angularVelocity.lengthSquared() < additionalAngularDampingThresholdSqr) &&
					(linearVelocity.lengthSquared() < additionalLinearDampingThresholdSqr)) {
				angularVelocity.scale(additionalDampingFactor);
				linearVelocity.scale(additionalDampingFactor);
			}

			float speed = linearVelocity.length();
			if (speed < linearDamping) {
				float dampVel = 0.005f;
				if (speed > dampVel) {
					Vector dir = Stack.alloc(linearVelocity);
					dir.normalize();
					dir.scale(dampVel);
					linearVelocity.sub(dir);
				}
				else {
					linearVelocity.set(0f, 0f, 0f);
				}
			}

			float angSpeed = angularVelocity.length();
			if (angSpeed < angularDamping) {
				float angDampVel = 0.005f;
				if (angSpeed > angDampVel) {
					Vector dir = Stack.alloc(angularVelocity);
					dir.normalize();
					dir.scale(angDampVel);
					angularVelocity.sub(dir);
				}
				else {
					angularVelocity.set(0f, 0f, 0f);
				}
			}
		}
	}

	public void setMassProps(float mass, Vector inertia) {
		if (mass == 0f) {
			collisionFlags |= CollisionFlags.STATIC_OBJECT;
			inverseMass = 0f;
		}
		else {
			collisionFlags &= (~CollisionFlags.STATIC_OBJECT);
			inverseMass = 1f / mass;
		}

		invInertiaLocal.set(inertia.get(X) != 0f ? 1f / inertia.get(X) : 0f,
				inertia.get(Y) != 0f ? 1f / inertia.get(Y) : 0f,
				inertia.get(Z) != 0f ? 1f / inertia.get(Z) : 0f);
	}

	public float getInvMass() {
		return inverseMass;
	}

	public Matrix getInvInertiaTensorWorld(Matrix out) {
		out.set(invInertiaTensorWorld);
		return out;
	}
	
	public void integrateVelocities(float step) {
		if (isStaticOrKinematicObject()) {
			return;
		}

		linearVelocity.scaleAdd(inverseMass * step, totalForce, linearVelocity);
		Vector tmp = Stack.alloc(totalTorque);
		invInertiaTensorWorld.transform(tmp);
		angularVelocity.scaleAdd(step, tmp, angularVelocity);

		// clamp angular velocity. collision calculations will fail on higher angular velocities	
		float angvel = angularVelocity.length();
		if (angvel * step > MAX_ANGVEL) {
			angularVelocity.scale((MAX_ANGVEL / step) / angvel);
		}
	}

	public void setCenterOfMassTransform(Transform xform) {
		if (isStaticOrKinematicObject()) {
			interpolationWorldTransform.set(worldTransform);
		}
		else {
			interpolationWorldTransform.set(xform);
		}
		getLinearVelocity(interpolationLinearVelocity);
		getAngularVelocity(interpolationAngularVelocity);
		worldTransform.set(xform);
		updateInertiaTensor();
	}

	public void applyCentralForce(Vector force) {
		totalForce.add(force);
	}
	
	public Vector getInvInertiaDiagLocal(Vector out) {
		out.set(invInertiaLocal);
		return out;
	}

	public void setInvInertiaDiagLocal(Vector diagInvInertia) {
		invInertiaLocal.set(diagInvInertia);
	}

	public void setSleepingThresholds(float linear, float angular) {
		linearSleepingThreshold = linear;
		angularSleepingThreshold = angular;
	}

	public void applyTorque(Vector torque) {
		totalTorque.add(torque);
	}

	public void applyForce(Vector force, Vector rel_pos) {
		applyCentralForce(force);
		
		Vector tmp = Stack.alloc(new Vector(3));
		tmp.cross(rel_pos, force);
		tmp.scale(angularFactor);
		applyTorque(tmp);
	}

	public void applyCentralImpulse(Vector impulse) {
		linearVelocity.scaleAdd(inverseMass, impulse, linearVelocity);
	}
	
	@StaticAlloc
	public void applyTorqueImpulse(Vector torque) {
		Vector tmp = Stack.alloc(torque);
		invInertiaTensorWorld.transform(tmp);
		angularVelocity.add(tmp);
	}

	@StaticAlloc
	public void applyImpulse(Vector impulse, Vector rel_pos) {
		if (inverseMass != 0f) {
			applyCentralImpulse(impulse);
			if (angularFactor != 0f) {
				Vector tmp = Stack.alloc(new Vector(3));
				tmp.cross(rel_pos, impulse);
				tmp.scale(angularFactor);
				applyTorqueImpulse(tmp);
			}
		}
	}

	/**
	 * Optimization for the iterative solver: avoid calculating constant terms involving inertia, normal, relative position.
	 */
	public void internalApplyImpulse(Vector linearComponent, Vector angularComponent, float impulseMagnitude) {
		if (inverseMass != 0f) {
			linearVelocity.scaleAdd(impulseMagnitude, linearComponent, linearVelocity);
			if (angularFactor != 0f) {
				angularVelocity.scaleAdd(impulseMagnitude * angularFactor, angularComponent, angularVelocity);
			}
		}
	}

	public void clearForces() {
		totalForce.set(0f, 0f, 0f);
		totalTorque.set(0f, 0f, 0f);
	}
	
	public void updateInertiaTensor() {
		Matrix mat1 = Stack.alloc(new Matrix(3, 3));
		MatrixUtil.scale(mat1, worldTransform.basis, invInertiaLocal);

		Matrix mat2 = Stack.alloc(worldTransform.basis);
		mat2.transpose();

		invInertiaTensorWorld.mul(mat1, mat2);
	}
	
	public Vector getCenterOfMassPosition(Vector out) {
		out.set(worldTransform.origin);
		return out;
	}

	public Quaternion getOrientation(Quaternion out) {
		MatrixUtil.getRotation(worldTransform.basis, out);
		return out;
	}
	
	public Transform getCenterOfMassTransform(Transform out) {
		out.set(worldTransform);
		return out;
	}

	public Vector getLinearVelocity(Vector out) {
		out.set(linearVelocity);
		return out;
	}

	public Vector getAngularVelocity(Vector out) {
		out.set(angularVelocity);
		return out;
	}

	public void setLinearVelocity(Vector lin_vel) {
		assert (collisionFlags != CollisionFlags.STATIC_OBJECT);
		linearVelocity.set(lin_vel);
	}

	public void setAngularVelocity(Vector ang_vel) {
		assert (collisionFlags != CollisionFlags.STATIC_OBJECT);
		angularVelocity.set(ang_vel);
	}

	public Vector getVelocityInLocalPoint(Vector rel_pos, Vector out) {
		// we also calculate lin/ang velocity for kinematic objects
		Vector vec = out;
		vec.cross(angularVelocity, rel_pos);
		vec.add(linearVelocity);
		return out;

		//for kinematic objects, we could also use use:
		//		return 	(m_worldTransform(rel_pos) - m_interpolationWorldTransform(rel_pos)) / m_kinematicTimeStep;
	}

	public void translate(Vector v) {
		worldTransform.origin.add(v);
	}
	
	public void getAabb(Vector aabbMin, Vector aabbMax) {
		getCollisionShape().getAabb(worldTransform, aabbMin, aabbMax);
	}

	public float computeImpulseDenominator(Vector pos, Vector normal) {
		Vector r0 = Stack.alloc(new Vector(3));
		r0.sub(pos, getCenterOfMassPosition(Stack.alloc(new Vector(3))));

		Vector c0 = Stack.alloc(new Vector(3));
		c0.cross(r0, normal);

		Vector tmp = Stack.alloc(new Vector(3));
		MatrixUtil.transposeTransform(tmp, c0, getInvInertiaTensorWorld(Stack.alloc(new Matrix(3, 3))));

		Vector vec = Stack.alloc(new Vector(3));
		vec.cross(tmp, r0);

		return inverseMass + normal.dot(vec);
	}

	public float computeAngularImpulseDenominator(Vector axis) {
		Vector vec = Stack.alloc(new Vector(3));
		MatrixUtil.transposeTransform(vec, axis, getInvInertiaTensorWorld(Stack.alloc(new Matrix(3, 3))));
		return axis.dot(vec);
	}

	public void updateDeactivation(float timeStep) {
		if ((getActivationState() == ISLAND_SLEEPING) || (getActivationState() == DISABLE_DEACTIVATION)) {
			return;
		}

		if ((getLinearVelocity(Stack.alloc(new Vector(3))).lengthSquared() < linearSleepingThreshold * linearSleepingThreshold) &&
				(getAngularVelocity(Stack.alloc(new Vector(3))).lengthSquared() < angularSleepingThreshold * angularSleepingThreshold)) {
			deactivationTime += timeStep;
		}
		else {
			deactivationTime = 0f;
			setActivationState(0);
		}
	}

	public boolean wantsSleeping() {
		if (getActivationState() == DISABLE_DEACTIVATION) {
			return false;
		}

		// disable deactivation
		if (BulletGlobals.isDeactivationDisabled() || (BulletGlobals.getDeactivationTime() == 0f)) {
			return false;
		}

		if ((getActivationState() == ISLAND_SLEEPING) || (getActivationState() == WANTS_DEACTIVATION)) {
			return true;
		}

		if (deactivationTime > BulletGlobals.getDeactivationTime()) {
			return true;
		}
		return false;
	}
	
	public BroadphaseProxy getBroadphaseProxy() {
		return broadphaseHandle;
	}

	public void setNewBroadphaseProxy(BroadphaseProxy broadphaseProxy) {
		this.broadphaseHandle = broadphaseProxy;
	}

	public MotionState getMotionState() {
		return optionalMotionState;
	}

	public void setMotionState(MotionState motionState) {
		this.optionalMotionState = motionState;
		if (optionalMotionState != null) {
			motionState.getWorldTransform(worldTransform);
		}
	}

	public void setAngularFactor(float angFac) {
		angularFactor = angFac;
	}

	public float getAngularFactor() {
		return angularFactor;
	}

	/**
	 * Is this rigidbody added to a CollisionWorld/DynamicsWorld/Broadphase?
	 */
	public boolean isInWorld() {
		return (getBroadphaseProxy() != null);
	}

	@Override
	public boolean checkCollideWithOverride(CollisionObject co) {
		// TODO: change to cast
		RigidBody otherRb = RigidBody.upcast(co);
		if (otherRb == null) {
			return true;
		}

		for (int i = 0; i < constraintRefs.size(); ++i) {
			TypedConstraint c = constraintRefs.getQuick(i);
			if (c.getRigidBodyA() == otherRb || c.getRigidBodyB() == otherRb) {
				return false;
			}
		}

		return true;
	}

	public void addConstraintRef(TypedConstraint c) {
		int index = constraintRefs.indexOf(c);
		if (index == -1) {
			constraintRefs.add(c);
		}

		checkCollideWith = true;
	}
	
	public void removeConstraintRef(TypedConstraint c) {
		constraintRefs.remove(c);
		checkCollideWith = (constraintRefs.size() > 0);
	}

	public TypedConstraint getConstraintRef(int index) {
		return constraintRefs.getQuick(index);
	}

	public int getNumConstraintRefs() {
		return constraintRefs.size();
	}
	
}
