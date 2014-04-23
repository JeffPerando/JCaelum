/*
 * Java port of Bullet (c) 2008 Martin Dvorak <jezek2@advel.cz>
 *
 * Bullet Continuous Collision Detection and Physics Library
 * btConeTwistConstraint is Copyright (c) 2007 Starbreeze Studios
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
 * 
 * Written by: Marcus Hennix
 */

package com.elusivehawk.engine.physics.jbullet.dynamics.constraintsolver;

import com.elusivehawk.engine.math.Matrix;
import com.elusivehawk.engine.math.Quaternion;
import com.elusivehawk.engine.math.Vector3f;
import com.elusivehawk.engine.physics.jbullet.BulletGlobals;
import com.elusivehawk.engine.physics.jbullet.dynamics.RigidBody;
import com.elusivehawk.engine.physics.jbullet.linearmath.QuaternionUtil;
import com.elusivehawk.engine.physics.jbullet.linearmath.ScalarUtil;
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import com.elusivehawk.engine.physics.jbullet.linearmath.TransformUtil;
import cz.advel.stack.Stack;

/**
 * ConeTwistConstraint can be used to simulate ragdoll joints (upper arm, leg etc).
 * 
 * @author jezek2
 */
public class ConeTwistConstraint extends TypedConstraint {

	private JacobianEntry[] jac/*[3]*/ = new JacobianEntry[] { new JacobianEntry(), new JacobianEntry(), new JacobianEntry() }; //3 orthogonal linear constraints

	private final Transform rbAFrame = new Transform();
	private final Transform rbBFrame = new Transform();

	private float limitSoftness;
	private float biasFactor;
	private float relaxationFactor;

	private float swingSpan1;
	private float swingSpan2;
	private float twistSpan;

	private final Vector3f swingAxis = new Vector3f();
	private final Vector3f twistAxis = new Vector3f();

	private float kSwing;
	private float kTwist;

	private float twistLimitSign;
	private float swingCorrection;
	private float twistCorrection;

	private float accSwingLimitImpulse;
	private float accTwistLimitImpulse;

	private boolean angularOnly = false;
	private boolean solveTwistLimit;
	private boolean solveSwingLimit;
	
	public ConeTwistConstraint() {
		super(TypedConstraintType.CONETWIST_CONSTRAINT_TYPE);
	}

	@SuppressWarnings("unqualified-field-access")
	public ConeTwistConstraint(RigidBody rbA, RigidBody rbB, Transform rbAFrame, Transform rbBFrame) {
		super(TypedConstraintType.CONETWIST_CONSTRAINT_TYPE, rbA, rbB);
		
		rbAFrame.set(rbAFrame);
		rbBFrame.set(rbBFrame);

		swingSpan1 = 1e30f;
		swingSpan2 = 1e30f;
		twistSpan = 1e30f;
		biasFactor = 0.3f;
		relaxationFactor = 1.0f;

		solveTwistLimit = false;
		solveSwingLimit = false;
	}

	@SuppressWarnings("unqualified-field-access")
	public ConeTwistConstraint(RigidBody rbA, Transform rbAFrame) {
		super(TypedConstraintType.CONETWIST_CONSTRAINT_TYPE, rbA);
		
		rbAFrame.set(rbAFrame);
		rbBFrame.set(rbAFrame);

		swingSpan1 = 1e30f;
		swingSpan2 = 1e30f;
		twistSpan = 1e30f;
		biasFactor = 0.3f;
		relaxationFactor = 1.0f;

		solveTwistLimit = false;
		solveSwingLimit = false;
	}
	
	@Override
	public void buildJacobian() {
		Vector3f tmp = Stack.alloc(Vector3f.class);
		Vector3f tmp1 = Stack.alloc(Vector3f.class);
		Vector3f tmp2 = Stack.alloc(Vector3f.class);

		Transform tmpTrans = Stack.alloc(Transform.class);

		this.appliedImpulse = 0f;

		// set bias, sign, clear accumulator
		this.swingCorrection = 0f;
		this.twistLimitSign = 0f;
		this.solveTwistLimit = false;
		this.solveSwingLimit = false;
		this.accTwistLimitImpulse = 0f;
		this.accSwingLimitImpulse = 0f;

		if (!this.angularOnly) {
			Vector3f pivotAInW = Stack.alloc(this.rbAFrame.origin);
			this.rbA.getCenterOfMassTransform(tmpTrans).transform(pivotAInW);

			Vector3f pivotBInW = Stack.alloc(this.rbBFrame.origin);
			this.rbB.getCenterOfMassTransform(tmpTrans).transform(pivotBInW);

			Vector3f relPos = Stack.alloc(Vector3f.class);
			relPos.sub(pivotBInW, pivotAInW);

			// TODO: stack
			Vector3f[] normal/*[3]*/ = new Vector3f[]{Stack.alloc(Vector3f.class), Stack.alloc(Vector3f.class), Stack.alloc(Vector3f.class)};
			if (relPos.lengthSquared() > BulletGlobals.FLT_EPSILON) {
				normal[0].normalize(relPos);
			}
			else {
				normal[0].set(1f, 0f, 0f);
			}

			TransformUtil.planeSpace1(normal[0], normal[1], normal[2]);

			for (int i = 0; i < 3; i++) {
				Matrix mat1 = this.rbA.getCenterOfMassTransform(Stack.alloc(Transform.class)).basis;
				mat1.transpose();

				Matrix mat2 = this.rbB.getCenterOfMassTransform(Stack.alloc(Transform.class)).basis;
				mat2.transpose();

				tmp1.sub(pivotAInW, this.rbA.getCenterOfMassPosition(tmp));
				tmp2.sub(pivotBInW, this.rbB.getCenterOfMassPosition(tmp));

				this.jac[i].init(
						mat1,
						mat2,
						tmp1,
						tmp2,
						normal[i],
						this.rbA.getInvInertiaDiagLocal(Stack.alloc(Vector3f.class)),
						this.rbA.getInvMass(),
						this.rbB.getInvInertiaDiagLocal(Stack.alloc(Vector3f.class)),
						this.rbB.getInvMass());
			}
		}

		Vector3f b1Axis1 = Stack.alloc(Vector3f.class), b1Axis2 = Stack.alloc(Vector3f.class), b1Axis3 = Stack.alloc(Vector3f.class);
		Vector3f b2Axis1 = Stack.alloc(Vector3f.class), b2Axis2 = Stack.alloc(Vector3f.class);

		this.rbAFrame.basis.getColumn(0, b1Axis1);
		getRigidBodyA().getCenterOfMassTransform(tmpTrans).basis.transform(b1Axis1);

		this.rbBFrame.basis.getColumn(0, b2Axis1);
		getRigidBodyB().getCenterOfMassTransform(tmpTrans).basis.transform(b2Axis1);

		float swing1 = 0f, swing2 = 0f;

		float swx = 0f, swy = 0f;
		float thresh = 10f;
		float fact;

		// Get Frame into world space
		if (this.swingSpan1 >= 0.05f) {
			this.rbAFrame.basis.getColumn(1, b1Axis2);
			getRigidBodyA().getCenterOfMassTransform(tmpTrans).basis.transform(b1Axis2);
//			swing1 = ScalarUtil.atan2Fast(b2Axis1.dot(b1Axis2), b2Axis1.dot(b1Axis1));
			swx = b2Axis1.dot(b1Axis1);
			swy = b2Axis1.dot(b1Axis2);
			swing1 = ScalarUtil.atan2Fast(swy, swx);
			fact = (swy*swy + swx*swx) * thresh * thresh;
			fact = fact / (fact + 1f);
			swing1 *= fact;
		}

		if (this.swingSpan2 >= 0.05f) {
			this.rbAFrame.basis.getColumn(2, b1Axis3);
			getRigidBodyA().getCenterOfMassTransform(tmpTrans).basis.transform(b1Axis3);
//			swing2 = ScalarUtil.atan2Fast(b2Axis1.dot(b1Axis3), b2Axis1.dot(b1Axis1));
			swx = b2Axis1.dot(b1Axis1);
			swy = b2Axis1.dot(b1Axis3);
			swing2 = ScalarUtil.atan2Fast(swy, swx);
			fact = (swy*swy + swx*swx) * thresh * thresh;
			fact = fact / (fact + 1f);
			swing2 *= fact;
		}

		float RMaxAngle1Sq = 1.0f / (this.swingSpan1 * this.swingSpan1);
		float RMaxAngle2Sq = 1.0f / (this.swingSpan2 * this.swingSpan2);
		float EllipseAngle = Math.abs(swing1*swing1) * RMaxAngle1Sq + Math.abs(swing2*swing2) * RMaxAngle2Sq;

		if (EllipseAngle > 1.0f) {
			this.swingCorrection = EllipseAngle - 1.0f;
			this.solveSwingLimit = true;

			// Calculate necessary axis & factors
			tmp1.scale(b2Axis1.dot(b1Axis2), b1Axis2);
			tmp2.scale(b2Axis1.dot(b1Axis3), b1Axis3);
			tmp.add(tmp1, tmp2);
			this.swingAxis.cross(b2Axis1, tmp);
			this.swingAxis.normalize();

			float swingAxisSign = (b2Axis1.dot(b1Axis1) >= 0.0f) ? 1.0f : -1.0f;
			this.swingAxis.scale(swingAxisSign);

			this.kSwing = 1f / (getRigidBodyA().computeAngularImpulseDenominator(this.swingAxis) +
					getRigidBodyB().computeAngularImpulseDenominator(this.swingAxis));

		}

		// Twist limits
		if (this.twistSpan >= 0f) {
			//Vector3f b2Axis2 = Stack.alloc(Vector3f.class);
			this.rbBFrame.basis.getColumn(1, b2Axis2);
			getRigidBodyB().getCenterOfMassTransform(tmpTrans).basis.transform(b2Axis2);

			Quaternion rotationArc = QuaternionUtil.shortestArcQuat(b2Axis1, b1Axis1, Stack.alloc(Quaternion.class));
			Vector3f TwistRef = QuaternionUtil.quatRotate(rotationArc, b2Axis2, Stack.alloc(Vector3f.class));
			float twist = ScalarUtil.atan2Fast(TwistRef.dot(b1Axis3), TwistRef.dot(b1Axis2));

			float lockedFreeFactor = (this.twistSpan > 0.05f) ? this.limitSoftness : 0f;
			if (twist <= -this.twistSpan * lockedFreeFactor) {
				this.twistCorrection = -(twist + this.twistSpan);
				this.solveTwistLimit = true;

				this.twistAxis.add(b2Axis1, b1Axis1);
				this.twistAxis.scale(0.5f);
				this.twistAxis.normalize();
				this.twistAxis.scale(-1.0f);

				this.kTwist = 1f / (getRigidBodyA().computeAngularImpulseDenominator(this.twistAxis) +
						getRigidBodyB().computeAngularImpulseDenominator(this.twistAxis));

			}
			else if (twist > this.twistSpan * lockedFreeFactor) {
				this.twistCorrection = (twist - this.twistSpan);
				this.solveTwistLimit = true;

				this.twistAxis.add(b2Axis1, b1Axis1);
				this.twistAxis.scale(0.5f);
				this.twistAxis.normalize();

				this.kTwist = 1f / (getRigidBodyA().computeAngularImpulseDenominator(this.twistAxis) +
						getRigidBodyB().computeAngularImpulseDenominator(this.twistAxis));
			}
		}
	}

	@Override
	public void solveConstraint(float timeStep) {
		Vector3f tmp = Stack.alloc(Vector3f.class);
		Vector3f tmp2 = Stack.alloc(Vector3f.class);

		Vector3f tmpVec = Stack.alloc(Vector3f.class);
		Transform tmpTrans = Stack.alloc(Transform.class);

		Vector3f pivotAInW = Stack.alloc(this.rbAFrame.origin);
		this.rbA.getCenterOfMassTransform(tmpTrans).transform(pivotAInW);

		Vector3f pivotBInW = Stack.alloc(this.rbBFrame.origin);
		this.rbB.getCenterOfMassTransform(tmpTrans).transform(pivotBInW);

		float tau = 0.3f;

		// linear part
		if (!this.angularOnly) {
			Vector3f rel_pos1 = Stack.alloc(Vector3f.class);
			rel_pos1.sub(pivotAInW, this.rbA.getCenterOfMassPosition(tmpVec));

			Vector3f rel_pos2 = Stack.alloc(Vector3f.class);
			rel_pos2.sub(pivotBInW, this.rbB.getCenterOfMassPosition(tmpVec));

			Vector3f vel1 = this.rbA.getVelocityInLocalPoint(rel_pos1, Stack.alloc(Vector3f.class));
			Vector3f vel2 = this.rbB.getVelocityInLocalPoint(rel_pos2, Stack.alloc(Vector3f.class));
			Vector3f vel = Stack.alloc(Vector3f.class);
			vel.sub(vel1, vel2);

			for (int i = 0; i < 3; i++) {
				Vector3f normal = this.jac[i].linearJointAxis;
				float jacDiagABInv = 1f / this.jac[i].getDiagonal();

				float rel_vel;
				rel_vel = normal.dot(vel);
				// positional error (zeroth order error)
				tmp.sub(pivotAInW, pivotBInW);
				float depth = -(tmp).dot(normal); // this is the error projected on the normal
				float impulse = depth * tau / timeStep * jacDiagABInv - rel_vel * jacDiagABInv;
				this.appliedImpulse += impulse;
				Vector3f impulse_vector = Stack.alloc(Vector3f.class);
				impulse_vector.scale(impulse, normal);

				tmp.sub(pivotAInW, this.rbA.getCenterOfMassPosition(tmpVec));
				this.rbA.applyImpulse(impulse_vector, tmp);

				tmp.negate(impulse_vector);
				tmp2.sub(pivotBInW, this.rbB.getCenterOfMassPosition(tmpVec));
				this.rbB.applyImpulse(tmp, tmp2);
			}
		}

		{
			// solve angular part
			Vector3f angVelA = getRigidBodyA().getAngularVelocity(Stack.alloc(Vector3f.class));
			Vector3f angVelB = getRigidBodyB().getAngularVelocity(Stack.alloc(Vector3f.class));

			// solve swing limit
			if (this.solveSwingLimit) {
				tmp.sub(angVelB, angVelA);
				float amplitude = ((tmp).dot(this.swingAxis) * this.relaxationFactor * this.relaxationFactor + this.swingCorrection * (1f / timeStep) * this.biasFactor);
				float impulseMag = amplitude * this.kSwing;

				// Clamp the accumulated impulse
				float temp = this.accSwingLimitImpulse;
				this.accSwingLimitImpulse = Math.max(this.accSwingLimitImpulse + impulseMag, 0.0f);
				impulseMag = this.accSwingLimitImpulse - temp;

				Vector3f impulse = Stack.alloc(Vector3f.class);
				impulse.scale(impulseMag, this.swingAxis);

				this.rbA.applyTorqueImpulse(impulse);

				tmp.negate(impulse);
				this.rbB.applyTorqueImpulse(tmp);
			}

			// solve twist limit
			if (this.solveTwistLimit) {
				tmp.sub(angVelB, angVelA);
				float amplitude = ((tmp).dot(this.twistAxis) * this.relaxationFactor * this.relaxationFactor + this.twistCorrection * (1f / timeStep) * this.biasFactor);
				float impulseMag = amplitude * this.kTwist;

				// Clamp the accumulated impulse
				float temp = this.accTwistLimitImpulse;
				this.accTwistLimitImpulse = Math.max(this.accTwistLimitImpulse + impulseMag, 0.0f);
				impulseMag = this.accTwistLimitImpulse - temp;

				Vector3f impulse = Stack.alloc(Vector3f.class);
				impulse.scale(impulseMag, this.twistAxis);

				this.rbA.applyTorqueImpulse(impulse);

				tmp.negate(impulse);
				this.rbB.applyTorqueImpulse(tmp);
			}
		}
	}

	public void updateRHS(float timeStep) {
	}

	public void setAngularOnly(boolean angularOnly) {
		this.angularOnly = angularOnly;
	}

	public void setLimit(float _swingSpan1, float _swingSpan2, float _twistSpan) {
		setLimit(_swingSpan1, _swingSpan2, _twistSpan, 0.8f, 0.3f, 1.0f);
	}

	public void setLimit(float _swingSpan1, float _swingSpan2, float _twistSpan, float _softness, float _biasFactor, float _relaxationFactor) {
		this.swingSpan1 = _swingSpan1;
		this.swingSpan2 = _swingSpan2;
		this.twistSpan = _twistSpan;

		this.limitSoftness = _softness;
		this.biasFactor = _biasFactor;
		this.relaxationFactor = _relaxationFactor;
	}

	public Transform getAFrame(Transform out) {
		out.set(this.rbAFrame);
		return out;
	}

	public Transform getBFrame(Transform out) {
		out.set(this.rbBFrame);
		return out;
	}

	public boolean getSolveTwistLimit() {
		return this.solveTwistLimit;
	}

	public boolean getSolveSwingLimit() {
		return this.solveTwistLimit;
	}

	public float getTwistLimitSign() {
		return this.twistLimitSign;
	}
	
}
