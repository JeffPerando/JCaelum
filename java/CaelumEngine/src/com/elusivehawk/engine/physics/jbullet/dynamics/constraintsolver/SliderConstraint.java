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

/*
Added by Roman Ponomarev (rponom@gmail.com)
April 04, 2008

TODO:
 - add clamping od accumulated impulse to improve stability
 - add conversion for ODE constraint solver
*/

package com.elusivehawk.engine.physics.jbullet.dynamics.constraintsolver;

import static com.elusivehawk.util.math.MathConst.*;
import com.elusivehawk.engine.physics.jbullet.dynamics.RigidBody;
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import com.elusivehawk.util.math.Matrix;
import com.elusivehawk.util.math.Vector;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */

// JAVA NOTE: SliderConstraint from 2.71

/**
 *
 * @author jezek2
 */
public class SliderConstraint extends TypedConstraint {
	
	public static final float SLIDER_CONSTRAINT_DEF_SOFTNESS    = 1.0f;
	public static final float SLIDER_CONSTRAINT_DEF_DAMPING     = 1.0f;
	public static final float SLIDER_CONSTRAINT_DEF_RESTITUTION = 0.7f;
	
	protected final Transform frameInA = new Transform();
	protected final Transform frameInB = new Transform();
	// use frameA fo define limits, if true
	protected boolean useLinearReferenceFrameA;
	// linear limits
	protected float lowerLinLimit;
	protected float upperLinLimit;
	// angular limits
	protected float lowerAngLimit;
	protected float upperAngLimit;
	// softness, restitution and damping for different cases
	// DirLin - moving inside linear limits
	// LimLin - hitting linear limit
	// DirAng - moving inside angular limits
	// LimAng - hitting angular limit
	// OrthoLin, OrthoAng - against constraint axis
	protected float softnessDirLin;
	protected float restitutionDirLin;
	protected float dampingDirLin;
	protected float softnessDirAng;
	protected float restitutionDirAng;
	protected float dampingDirAng;
	protected float softnessLimLin;
	protected float restitutionLimLin;
	protected float dampingLimLin;
	protected float softnessLimAng;
	protected float restitutionLimAng;
	protected float dampingLimAng;
	protected float softnessOrthoLin;
	protected float restitutionOrthoLin;
	protected float dampingOrthoLin;
	protected float softnessOrthoAng;
	protected float restitutionOrthoAng;
	protected float dampingOrthoAng;
	
	// for interlal use
	protected boolean solveLinLim;
	protected boolean solveAngLim;

	protected JacobianEntry[] jacLin = new JacobianEntry[/*3*/] { new JacobianEntry(), new JacobianEntry(), new JacobianEntry() };
	protected float[] jacLinDiagABInv = new float[3];

	protected JacobianEntry[] jacAng = new JacobianEntry[/*3*/] { new JacobianEntry(), new JacobianEntry(), new JacobianEntry() };

	protected float timeStep;
	protected final Transform calculatedTransformA = new Transform();
	protected final Transform calculatedTransformB = new Transform();

	protected final Vector sliderAxis = new Vector();
	protected final Vector realPivotAInW = new Vector();
	protected final Vector realPivotBInW = new Vector();
	protected final Vector projPivotInW = new Vector();
	protected final Vector delta = new Vector();
	protected final Vector depth = new Vector();
	protected final Vector relPosA = new Vector();
	protected final Vector relPosB = new Vector();

	protected float linPos;

	protected float angDepth;
	protected float kAngle;

	protected boolean poweredLinMotor;
	protected float targetLinMotorVelocity;
	protected float maxLinMotorForce;
	protected float accumulatedLinMotorImpulse;
	
	protected boolean poweredAngMotor;
	protected float targetAngMotorVelocity;
	protected float maxAngMotorForce;
	protected float accumulatedAngMotorImpulse;

    @SuppressWarnings("unqualified-field-access")
	public SliderConstraint() {
		super(TypedConstraintType.SLIDER_CONSTRAINT_TYPE);
		useLinearReferenceFrameA = true;
		initParams();
	}

    public SliderConstraint(RigidBody rbA, RigidBody rbB, Transform frameInA, Transform frameInB ,boolean useLinearReferenceFrameA) {
		super(TypedConstraintType.SLIDER_CONSTRAINT_TYPE, rbA, rbB);
        this.frameInA.set(frameInA);
        this.frameInB.set(frameInB);
		this.useLinearReferenceFrameA = useLinearReferenceFrameA;
		initParams();
	}

	protected void initParams() {
		this.lowerLinLimit = 1f;
		this.upperLinLimit = -1f;
		this.lowerAngLimit = 0f;
		this.upperAngLimit = 0f;
		this.softnessDirLin = SLIDER_CONSTRAINT_DEF_SOFTNESS;
		this.restitutionDirLin = SLIDER_CONSTRAINT_DEF_RESTITUTION;
		this.dampingDirLin = 0f;
		this.softnessDirAng = SLIDER_CONSTRAINT_DEF_SOFTNESS;
		this.restitutionDirAng = SLIDER_CONSTRAINT_DEF_RESTITUTION;
		this.dampingDirAng = 0f;
		this.softnessOrthoLin = SLIDER_CONSTRAINT_DEF_SOFTNESS;
		this.restitutionOrthoLin = SLIDER_CONSTRAINT_DEF_RESTITUTION;
		this.dampingOrthoLin = SLIDER_CONSTRAINT_DEF_DAMPING;
		this.softnessOrthoAng = SLIDER_CONSTRAINT_DEF_SOFTNESS;
		this.restitutionOrthoAng = SLIDER_CONSTRAINT_DEF_RESTITUTION;
		this.dampingOrthoAng = SLIDER_CONSTRAINT_DEF_DAMPING;
		this.softnessLimLin = SLIDER_CONSTRAINT_DEF_SOFTNESS;
		this.restitutionLimLin = SLIDER_CONSTRAINT_DEF_RESTITUTION;
		this.dampingLimLin = SLIDER_CONSTRAINT_DEF_DAMPING;
		this.softnessLimAng = SLIDER_CONSTRAINT_DEF_SOFTNESS;
		this.restitutionLimAng = SLIDER_CONSTRAINT_DEF_RESTITUTION;
		this.dampingLimAng = SLIDER_CONSTRAINT_DEF_DAMPING;

		this.poweredLinMotor = false;
		this.targetLinMotorVelocity = 0f;
		this.maxLinMotorForce = 0f;
		this.accumulatedLinMotorImpulse = 0f;

		this.poweredAngMotor = false;
		this.targetAngMotorVelocity = 0f;
		this.maxAngMotorForce = 0f;
		this.accumulatedAngMotorImpulse = 0f;
	}

	@Override
	public void buildJacobian() {
		if (this.useLinearReferenceFrameA) {
			buildJacobianInt(this.rbA, this.rbB, this.frameInA, this.frameInB);
		}
		else {
			buildJacobianInt(this.rbB, this.rbA, this.frameInB, this.frameInA);
		}
	}

	@Override
	public void solveConstraint(float timeStep) {
		this.timeStep = timeStep;
		if (this.useLinearReferenceFrameA) {
			solveConstraintInt(this.rbA, this.rbB);
		}
		else {
			solveConstraintInt(this.rbB, this.rbA);
		}
	}
	
	public Transform getCalculatedTransformA(Transform out) {
		out.set(this.calculatedTransformA);
		return out;
	}
	
	public Transform getCalculatedTransformB(Transform out) {
		out.set(this.calculatedTransformB);
		return out;
	}
	
	public Transform getFrameOffsetA(Transform out) {
		out.set(this.frameInA);
		return out;
	}

	public Transform getFrameOffsetB(Transform out) {
		out.set(this.frameInB);
		return out;
	}
	
	public float getLowerLinLimit() {
		return this.lowerLinLimit;
	}

	public void setLowerLinLimit(float lowerLimit) {
		this.lowerLinLimit = lowerLimit;
	}

	public float getUpperLinLimit() {
		return this.upperLinLimit;
	}

	public void setUpperLinLimit(float upperLimit) {
		this.upperLinLimit = upperLimit;
	}

	public float getLowerAngLimit() {
		return this.lowerAngLimit;
	}

	public void setLowerAngLimit(float lowerLimit) {
		this.lowerAngLimit = lowerLimit;
	}

	public float getUpperAngLimit() {
		return this.upperAngLimit;
	}

	public void setUpperAngLimit(float upperLimit) {
		this.upperAngLimit = upperLimit;
	}

	public boolean getUseLinearReferenceFrameA() {
		return this.useLinearReferenceFrameA;
	}
	
	public float getSoftnessDirLin() {
		return this.softnessDirLin;
	}

	public float getRestitutionDirLin() {
		return this.restitutionDirLin;
	}

	public float getDampingDirLin() {
		return this.dampingDirLin;
	}

	public float getSoftnessDirAng() {
		return this.softnessDirAng;
	}

	public float getRestitutionDirAng() {
		return this.restitutionDirAng;
	}

	public float getDampingDirAng() {
		return this.dampingDirAng;
	}

	public float getSoftnessLimLin() {
		return this.softnessLimLin;
	}

	public float getRestitutionLimLin() {
		return this.restitutionLimLin;
	}

	public float getDampingLimLin() {
		return this.dampingLimLin;
	}

	public float getSoftnessLimAng() {
		return this.softnessLimAng;
	}

	public float getRestitutionLimAng() {
		return this.restitutionLimAng;
	}

	public float getDampingLimAng() {
		return this.dampingLimAng;
	}

	public float getSoftnessOrthoLin() {
		return this.softnessOrthoLin;
	}

	public float getRestitutionOrthoLin() {
		return this.restitutionOrthoLin;
	}

	public float getDampingOrthoLin() {
		return this.dampingOrthoLin;
	}

	public float getSoftnessOrthoAng() {
		return this.softnessOrthoAng;
	}

	public float getRestitutionOrthoAng() {
		return this.restitutionOrthoAng;
	}

	public float getDampingOrthoAng() {
		return this.dampingOrthoAng;
	}
	
	public void setSoftnessDirLin(float softnessDirLin) {
		this.softnessDirLin = softnessDirLin;
	}

	public void setRestitutionDirLin(float restitutionDirLin) {
		this.restitutionDirLin = restitutionDirLin;
	}

	public void setDampingDirLin(float dampingDirLin) {
		this.dampingDirLin = dampingDirLin;
	}

	public void setSoftnessDirAng(float softnessDirAng) {
		this.softnessDirAng = softnessDirAng;
	}

	public void setRestitutionDirAng(float restitutionDirAng) {
		this.restitutionDirAng = restitutionDirAng;
	}

	public void setDampingDirAng(float dampingDirAng) {
		this.dampingDirAng = dampingDirAng;
	}

	public void setSoftnessLimLin(float softnessLimLin) {
		this.softnessLimLin = softnessLimLin;
	}

	public void setRestitutionLimLin(float restitutionLimLin) {
		this.restitutionLimLin = restitutionLimLin;
	}

	public void setDampingLimLin(float dampingLimLin) {
		this.dampingLimLin = dampingLimLin;
	}

	public void setSoftnessLimAng(float softnessLimAng) {
		this.softnessLimAng = softnessLimAng;
	}

	public void setRestitutionLimAng(float restitutionLimAng) {
		this.restitutionLimAng = restitutionLimAng;
	}

	public void setDampingLimAng(float dampingLimAng) {
		this.dampingLimAng = dampingLimAng;
	}

	public void setSoftnessOrthoLin(float softnessOrthoLin) {
		this.softnessOrthoLin = softnessOrthoLin;
	}

	public void setRestitutionOrthoLin(float restitutionOrthoLin) {
		this.restitutionOrthoLin = restitutionOrthoLin;
	}

	public void setDampingOrthoLin(float dampingOrthoLin) {
		this.dampingOrthoLin = dampingOrthoLin;
	}

	public void setSoftnessOrthoAng(float softnessOrthoAng) {
		this.softnessOrthoAng = softnessOrthoAng;
	}

	public void setRestitutionOrthoAng(float restitutionOrthoAng) {
		this.restitutionOrthoAng = restitutionOrthoAng;
	}

	public void setDampingOrthoAng(float dampingOrthoAng) {
		this.dampingOrthoAng = dampingOrthoAng;
	}

	public void setPoweredLinMotor(boolean onOff) {
		this.poweredLinMotor = onOff;
	}

	public boolean getPoweredLinMotor() {
		return this.poweredLinMotor;
	}

	public void setTargetLinMotorVelocity(float targetLinMotorVelocity) {
		this.targetLinMotorVelocity = targetLinMotorVelocity;
	}

	public float getTargetLinMotorVelocity() {
		return this.targetLinMotorVelocity;
	}

	public void setMaxLinMotorForce(float maxLinMotorForce) {
		this.maxLinMotorForce = maxLinMotorForce;
	}

	public float getMaxLinMotorForce() {
		return this.maxLinMotorForce;
	}

	public void setPoweredAngMotor(boolean onOff) {
		this.poweredAngMotor = onOff;
	}

	public boolean getPoweredAngMotor() {
		return this.poweredAngMotor;
	}

	public void setTargetAngMotorVelocity(float targetAngMotorVelocity) {
		this.targetAngMotorVelocity = targetAngMotorVelocity;
	}

	public float getTargetAngMotorVelocity() {
		return this.targetAngMotorVelocity;
	}

	public void setMaxAngMotorForce(float maxAngMotorForce) {
		this.maxAngMotorForce = maxAngMotorForce;
	}

	public float getMaxAngMotorForce() {
		return this.maxAngMotorForce;
	}

	public float getLinearPos() {
		return this.linPos;
	}

	// access for ODE solver

	public boolean getSolveLinLimit() {
		return this.solveLinLim;
	}

	public float getLinDepth() {
		return this.depth.get(X);
	}

	public boolean getSolveAngLimit() {
		return this.solveAngLim;
	}

	public float getAngDepth() {
		return this.angDepth;
	}
	
	// internal
	
	public void buildJacobianInt(RigidBody rbA, RigidBody rbB, Transform frameInA, Transform frameInB) {
		Transform tmpTrans = Stack.alloc(Transform.class);
		Transform tmpTrans1 = Stack.alloc(Transform.class);
		Transform tmpTrans2 = Stack.alloc(Transform.class);
		Vector tmp = Stack.alloc(new Vector(3));
		Vector tmp2 = Stack.alloc(new Vector(3));

		// calculate transforms
		this.calculatedTransformA.mul(rbA.getCenterOfMassTransform(tmpTrans), frameInA);
		this.calculatedTransformB.mul(rbB.getCenterOfMassTransform(tmpTrans), frameInB);
		this.realPivotAInW.set(this.calculatedTransformA.origin);
		this.realPivotBInW.set(this.calculatedTransformB.origin);
		this.calculatedTransformA.basis.getColumn(0, tmp);
		this.sliderAxis.set(tmp); // along X
		this.delta.sub(this.realPivotBInW, this.realPivotAInW);
		this.projPivotInW.scaleAdd(this.sliderAxis.dot(this.delta), this.sliderAxis, this.realPivotAInW);
		this.relPosA.sub(this.projPivotInW, rbA.getCenterOfMassPosition(tmp));
		this.relPosB.sub(this.realPivotBInW, rbB.getCenterOfMassPosition(tmp));
		Vector normalWorld = Stack.alloc(new Vector(3));

		// linear part
		for (int i=0; i<3; i++) {
			this.calculatedTransformA.basis.getColumn(i, normalWorld);

			Matrix mat1 = rbA.getCenterOfMassTransform(tmpTrans1).basis;
			mat1.transpose();

			Matrix mat2 = rbB.getCenterOfMassTransform(tmpTrans2).basis;
			mat2.transpose();

			this.jacLin[i].init(
					mat1,
					mat2,
					this.relPosA,
					this.relPosB,
					normalWorld,
					rbA.getInvInertiaDiagLocal(tmp),
					rbA.getInvMass(),
					rbB.getInvInertiaDiagLocal(tmp2),
					rbB.getInvMass());
			this.jacLinDiagABInv[i] = 1f / this.jacLin[i].getDiagonal();
			this.depth.set(i, this.delta.dot(normalWorld), i == 2);
		}
		testLinLimits();

		// angular part
		for (int i=0; i<3; i++) {
			this.calculatedTransformA.basis.getColumn(i, normalWorld);

			Matrix mat1 = rbA.getCenterOfMassTransform(tmpTrans1).basis;
			mat1.transpose();

			Matrix mat2 = rbB.getCenterOfMassTransform(tmpTrans2).basis;
			mat2.transpose();

			this.jacAng[i].init(
					normalWorld,
					mat1,
					mat2,
					rbA.getInvInertiaDiagLocal(tmp),
					rbB.getInvInertiaDiagLocal(tmp2));
		}
		testAngLimits();

		Vector axisA = Stack.alloc(new Vector(3));
		this.calculatedTransformA.basis.getColumn(0, axisA);
		this.kAngle = 1f / (rbA.computeAngularImpulseDenominator(axisA) + rbB.computeAngularImpulseDenominator(axisA));
		// clear accumulator for motors
		this.accumulatedLinMotorImpulse = 0f;
		this.accumulatedAngMotorImpulse = 0f;
	}
	
	public void solveConstraintInt(RigidBody rbA, RigidBody rbB) {
		Vector tmp = Stack.alloc(new Vector(3));

		// linear
		Vector velA = rbA.getVelocityInLocalPoint(this.relPosA, Stack.alloc(new Vector(3)));
		Vector velB = rbB.getVelocityInLocalPoint(this.relPosB, Stack.alloc(new Vector(3)));
		Vector vel = Stack.alloc(new Vector(3));
		vel.sub(velA, velB);

		Vector impulse_vector = Stack.alloc(new Vector(3));

		for (int i=0; i<3; i++) {
			Vector normal = this.jacLin[i].linearJointAxis;
			float rel_vel = normal.dot(vel);
			// calculate positional error
			float depth = this.depth.get(i);
			// get parameters
			float softness = (i != 0)? this.softnessOrthoLin : (this.solveLinLim? this.softnessLimLin : this.softnessDirLin);
			float restitution = (i != 0)? this.restitutionOrthoLin : (this.solveLinLim? this.restitutionLimLin : this.restitutionDirLin);
			float damping = (i != 0)? this.dampingOrthoLin : (this.solveLinLim? this.dampingLimLin : this.dampingDirLin);
			// calcutate and apply impulse
			float normalImpulse = softness * (restitution * depth / this.timeStep - damping * rel_vel) * this.jacLinDiagABInv[i];
			impulse_vector.scale(normalImpulse, normal);
			rbA.applyImpulse(impulse_vector, this.relPosA);
			tmp.negate(impulse_vector);
			rbB.applyImpulse(tmp, this.relPosB);

			if (this.poweredLinMotor && (i == 0)) {
				// apply linear motor
				if (this.accumulatedLinMotorImpulse < this.maxLinMotorForce) {
					float desiredMotorVel = this.targetLinMotorVelocity;
					float motor_relvel = desiredMotorVel + rel_vel;
					normalImpulse = -motor_relvel * this.jacLinDiagABInv[i];
					// clamp accumulated impulse
					float new_acc = this.accumulatedLinMotorImpulse + Math.abs(normalImpulse);
					if (new_acc > this.maxLinMotorForce) {
						new_acc = this.maxLinMotorForce;
					}
					float del = new_acc - this.accumulatedLinMotorImpulse;
					if (normalImpulse < 0f) {
						normalImpulse = -del;
					}
					else {
						normalImpulse = del;
					}
					this.accumulatedLinMotorImpulse = new_acc;
					// apply clamped impulse
					impulse_vector.scale(normalImpulse, normal);
					rbA.applyImpulse(impulse_vector, this.relPosA);
					tmp.negate(impulse_vector);
					rbB.applyImpulse(tmp, this.relPosB);
				}
			}
		}

		// angular
		// get axes in world space
		Vector axisA = Stack.alloc(new Vector(3));
		this.calculatedTransformA.basis.getColumn(0, axisA);
		Vector axisB = Stack.alloc(new Vector(3));
		this.calculatedTransformB.basis.getColumn(0, axisB);

		Vector angVelA = rbA.getAngularVelocity(Stack.alloc(new Vector(3)));
		Vector angVelB = rbB.getAngularVelocity(Stack.alloc(new Vector(3)));

		Vector angVelAroundAxisA = Stack.alloc(new Vector(3));
		angVelAroundAxisA.scale(axisA.dot(angVelA), axisA);
		Vector angVelAroundAxisB = Stack.alloc(new Vector(3));
		angVelAroundAxisB.scale(axisB.dot(angVelB), axisB);

		Vector angAorthog = Stack.alloc(new Vector(3));
		angAorthog.sub(angVelA, angVelAroundAxisA);
		Vector angBorthog = Stack.alloc(new Vector(3));
		angBorthog.sub(angVelB, angVelAroundAxisB);
		Vector velrelOrthog = Stack.alloc(new Vector(3));
		velrelOrthog.sub(angAorthog, angBorthog);

		// solve orthogonal angular velocity correction
		float len = velrelOrthog.length();
		if (len > 0.00001f) {
			Vector normal = Stack.alloc(new Vector(3));
			normal.normalize(velrelOrthog);
			float denom = rbA.computeAngularImpulseDenominator(normal) + rbB.computeAngularImpulseDenominator(normal);
			velrelOrthog.scale((1f / denom) * this.dampingOrthoAng * this.softnessOrthoAng);
		}

		// solve angular positional correction
		Vector angularError = Stack.alloc(new Vector(3));
		angularError.cross(axisA, axisB);
		angularError.scale(1f / this.timeStep);
		float len2 = angularError.length();
		if (len2 > 0.00001f) {
			Vector normal2 = Stack.alloc(new Vector(3));
			normal2.normalize(angularError);
			float denom2 = rbA.computeAngularImpulseDenominator(normal2) + rbB.computeAngularImpulseDenominator(normal2);
			angularError.scale((1f / denom2) * this.restitutionOrthoAng * this.softnessOrthoAng);
		}

		// apply impulse
		tmp.negate(velrelOrthog);
		tmp.add(angularError);
		rbA.applyTorqueImpulse(tmp);
		tmp.sub(velrelOrthog, angularError);
		rbB.applyTorqueImpulse(tmp);
		float impulseMag;

		// solve angular limits
		if (this.solveAngLim) {
			tmp.sub(angVelB, angVelA);
			impulseMag = tmp.dot(axisA) * this.dampingLimAng + this.angDepth * this.restitutionLimAng / this.timeStep;
			impulseMag *= this.kAngle * this.softnessLimAng;
		}
		else {
			tmp.sub(angVelB, angVelA);
			impulseMag = tmp.dot(axisA) * this.dampingDirAng + this.angDepth * this.restitutionDirAng / this.timeStep;
			impulseMag *= this.kAngle * this.softnessDirAng;
		}
		Vector impulse = Stack.alloc(new Vector(3));
		impulse.scale(impulseMag, axisA);
		rbA.applyTorqueImpulse(impulse);
		tmp.negate(impulse);
		rbB.applyTorqueImpulse(tmp);

		// apply angular motor
		if (this.poweredAngMotor) {
			if (this.accumulatedAngMotorImpulse < this.maxAngMotorForce) {
				Vector velrel = Stack.alloc(new Vector(3));
				velrel.sub(angVelAroundAxisA, angVelAroundAxisB);
				float projRelVel = velrel.dot(axisA);

				float desiredMotorVel = this.targetAngMotorVelocity;
				float motor_relvel = desiredMotorVel - projRelVel;

				float angImpulse = this.kAngle * motor_relvel;
				// clamp accumulated impulse
				float new_acc = this.accumulatedAngMotorImpulse + Math.abs(angImpulse);
				if (new_acc > this.maxAngMotorForce) {
					new_acc = this.maxAngMotorForce;
				}
				float del = new_acc - this.accumulatedAngMotorImpulse;
				if (angImpulse < 0f) {
					angImpulse = -del;
				} else {
					angImpulse = del;
				}
				this.accumulatedAngMotorImpulse = new_acc;

				// apply clamped impulse
				Vector motorImp = Stack.alloc(new Vector(3));
				motorImp.scale(angImpulse, axisA);
				rbA.applyTorqueImpulse(motorImp);
				tmp.negate(motorImp);
				rbB.applyTorqueImpulse(tmp);
			}
		}
	}
	
	// shared code used by ODE solver
	
	public void calculateTransforms() {
		Transform tmpTrans = Stack.alloc(Transform.class);

		if (this.useLinearReferenceFrameA) {
			this.calculatedTransformA.mul(this.rbA.getCenterOfMassTransform(tmpTrans), this.frameInA);
			this.calculatedTransformB.mul(this.rbB.getCenterOfMassTransform(tmpTrans), this.frameInB);
		}
		else {
			this.calculatedTransformA.mul(this.rbB.getCenterOfMassTransform(tmpTrans), this.frameInB);
			this.calculatedTransformB.mul(this.rbA.getCenterOfMassTransform(tmpTrans), this.frameInA);
		}
		this.realPivotAInW.set(this.calculatedTransformA.origin);
		this.realPivotBInW.set(this.calculatedTransformB.origin);
		this.calculatedTransformA.basis.getColumn(0, this.sliderAxis); // along X
		this.delta.sub(this.realPivotBInW, this.realPivotAInW);
		this.projPivotInW.scaleAdd(this.sliderAxis.dot(this.delta), this.sliderAxis, this.realPivotAInW);
		Vector normalWorld = Stack.alloc(new Vector(3));
		// linear part
		for (int i=0; i<3; i++) {
			this.calculatedTransformA.basis.getColumn(i, normalWorld);
			this.depth.set(i, this.delta.dot(normalWorld), i == 2);
		}
	}

	public void testLinLimits() {
		this.solveLinLim = false;
		this.linPos = this.depth.get(X);
		if (this.lowerLinLimit <= this.upperLinLimit) {
			if (this.depth.get(X) > this.upperLinLimit) {
				this.depth.sub(X, this.upperLinLimit, false);
				this.solveLinLim = true;
			}
			else if (this.depth.get(X)< this.lowerLinLimit) {
				this.depth.sub(X, this.lowerLinLimit, false);
				this.solveLinLim = true;
			}
			else {
				this.depth.set(X, 0f);
			}
		}
		else {
			this.depth.set(X, 0f);
		}
	}
	
	public void testAngLimits() {
		this.angDepth = 0f;
		this.solveAngLim = false;
		if (this.lowerAngLimit <= this.upperAngLimit) {
			Vector axisA0 = Stack.alloc(new Vector(3));
			this.calculatedTransformA.basis.getColumn(1, axisA0);
			Vector axisA1 = Stack.alloc(new Vector(3));
			this.calculatedTransformA.basis.getColumn(2, axisA1);
			Vector axisB0 = Stack.alloc(new Vector(3));
			this.calculatedTransformB.basis.getColumn(1, axisB0);

			float rot = (float) Math.atan2(axisB0.dot(axisA1), axisB0.dot(axisA0));
			if (rot < this.lowerAngLimit) {
				this.angDepth = rot - this.lowerAngLimit;
				this.solveAngLim = true;
			}
			else if (rot > this.upperAngLimit) {
				this.angDepth = rot - this.upperAngLimit;
				this.solveAngLim = true;
			}
		}
	}
	
	// access for PE Solver
	
	public Vector getAncorInA(Vector out) {
		Transform tmpTrans = Stack.alloc(Transform.class);

		Vector ancorInA = out;
		ancorInA.scaleAdd((this.lowerLinLimit + this.upperLinLimit) * 0.5f, this.sliderAxis, this.realPivotAInW);
		this.rbA.getCenterOfMassTransform(tmpTrans);
		tmpTrans.inverse();
		tmpTrans.transform(ancorInA);
		return ancorInA;
	}

	public Vector getAncorInB(Vector out) {
		Vector ancorInB = out;
		ancorInB.set(this.frameInB.origin);
		return ancorInB;
	}

}
