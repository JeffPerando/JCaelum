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

package com.elusivehawk.engine.physics.jbullet.dynamics.constraintsolver;

import com.elusivehawk.engine.math.Matrix;
import com.elusivehawk.engine.math.Vector;
import com.elusivehawk.engine.physics.jbullet.dynamics.RigidBody;
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * Point to point constraint between two rigid bodies each with a pivot point that
 * descibes the "ballsocket" location in local space.
 * 
 * @author jezek2
 */
public class Point2PointConstraint extends TypedConstraint {

	private final JacobianEntry[] jac = new JacobianEntry[]/*[3]*/ { new JacobianEntry(), new JacobianEntry(), new JacobianEntry() }; // 3 orthogonal linear constraints
	
	private final Vector pivotInA = new Vector();
	private final Vector pivotInB = new Vector();

	public ConstraintSetting setting = new ConstraintSetting();
	
	public Point2PointConstraint() {
		super(TypedConstraintType.POINT2POINT_CONSTRAINT_TYPE);
	}

	public Point2PointConstraint(RigidBody rbA, RigidBody rbB, Vector pivotInA, Vector pivotInB) {
		super(TypedConstraintType.POINT2POINT_CONSTRAINT_TYPE, rbA, rbB);
		this.pivotInA.set(pivotInA);
		this.pivotInB.set(pivotInB);
	}

	public Point2PointConstraint(RigidBody rbA, Vector pivotInA) {
		super(TypedConstraintType.POINT2POINT_CONSTRAINT_TYPE, rbA);
		this.pivotInA.set(pivotInA);
		this.pivotInB.set(pivotInA);
		rbA.getCenterOfMassTransform(Stack.alloc(Transform.class)).transform(this.pivotInB);
	}

	@Override
	public void buildJacobian() {
		this.appliedImpulse = 0f;

		Vector normal = Stack.alloc(new Vector(3));
		normal.set(0f, 0f, 0f);

		Matrix tmpMat1 = Stack.alloc(new Matrix(3, 3));
		Matrix tmpMat2 = Stack.alloc(new Matrix(3, 3));
		Vector tmp1 = Stack.alloc(new Vector(3));
		Vector tmp2 = Stack.alloc(new Vector(3));
		Vector tmpVec = Stack.alloc(new Vector(3));
		
		Transform centerOfMassA = this.rbA.getCenterOfMassTransform(Stack.alloc(Transform.class));
		Transform centerOfMassB = this.rbB.getCenterOfMassTransform(Stack.alloc(Transform.class));

		for (int i = 0; i < 3; i++) {
			normal.set(i, 1f, false);

			tmpMat1.transpose(centerOfMassA.basis);
			tmpMat2.transpose(centerOfMassB.basis);

			tmp1.set(this.pivotInA);
			centerOfMassA.transform(tmp1);
			tmp1.sub(this.rbA.getCenterOfMassPosition(tmpVec));

			tmp2.set(this.pivotInB);
			centerOfMassB.transform(tmp2);
			tmp2.sub(this.rbB.getCenterOfMassPosition(tmpVec));

			this.jac[i].init(
					tmpMat1,
					tmpMat2,
					tmp1,
					tmp2,
					normal,
					this.rbA.getInvInertiaDiagLocal(Stack.alloc(new Vector(3))),
					this.rbA.getInvMass(),
					this.rbB.getInvInertiaDiagLocal(Stack.alloc(new Vector(3))),
					this.rbB.getInvMass());
			normal.set(i, 0f, i == 2);
		}
	}

	@Override
	public void solveConstraint(float timeStep) {
		Vector tmp = Stack.alloc(new Vector(3));
		Vector tmp2 = Stack.alloc(new Vector(3));
		Vector tmpVec = Stack.alloc(new Vector(3));

		Transform centerOfMassA = this.rbA.getCenterOfMassTransform(Stack.alloc(Transform.class));
		Transform centerOfMassB = this.rbB.getCenterOfMassTransform(Stack.alloc(Transform.class));
		
		Vector pivotAInW = Stack.alloc(this.pivotInA);
		centerOfMassA.transform(pivotAInW);

		Vector pivotBInW = Stack.alloc(this.pivotInB);
		centerOfMassB.transform(pivotBInW);

		Vector normal = Stack.alloc(new Vector(3));
		normal.set(0f, 0f, 0f);

		//btVector3 angvelA = m_rbA.getCenterOfMassTransform().getBasis().transpose() * m_rbA.getAngularVelocity();
		//btVector3 angvelB = m_rbB.getCenterOfMassTransform().getBasis().transpose() * m_rbB.getAngularVelocity();

		for (int i = 0; i < 3; i++) {
			normal.set(i, 1f, false);
			float jacDiagABInv = 1f / this.jac[i].getDiagonal();

			Vector rel_pos1 = Stack.alloc(new Vector(3));
			rel_pos1.sub(pivotAInW, this.rbA.getCenterOfMassPosition(tmpVec));
			Vector rel_pos2 = Stack.alloc(new Vector(3));
			rel_pos2.sub(pivotBInW, this.rbB.getCenterOfMassPosition(tmpVec));
			// this jacobian entry could be re-used for all iterations

			Vector vel1 = this.rbA.getVelocityInLocalPoint(rel_pos1, Stack.alloc(new Vector(3)));
			Vector vel2 = this.rbB.getVelocityInLocalPoint(rel_pos2, Stack.alloc(new Vector(3)));
			Vector vel = Stack.alloc(new Vector(3));
			vel.sub(vel1, vel2);

			float rel_vel;
			rel_vel = normal.dot(vel);

			/*
			//velocity error (first order error)
			btScalar rel_vel = m_jac[i].getRelativeVelocity(m_rbA.getLinearVelocity(),angvelA,
			m_rbB.getLinearVelocity(),angvelB);
			 */

			// positional error (zeroth order error)
			tmp.sub(pivotAInW, pivotBInW);
			float depth = -tmp.dot(normal); //this is the error projected on the normal

			float impulse = depth * this.setting.tau / timeStep * jacDiagABInv - this.setting.damping * rel_vel * jacDiagABInv;

			float impulseClamp = this.setting.impulseClamp;
			if (impulseClamp > 0f) {
				if (impulse < -impulseClamp) {
					impulse = -impulseClamp;
				}
				if (impulse > impulseClamp) {
					impulse = impulseClamp;
				}
			}

			this.appliedImpulse += impulse;
			Vector impulse_vector = Stack.alloc(new Vector(3));
			impulse_vector.scale(impulse, normal);
			tmp.sub(pivotAInW, this.rbA.getCenterOfMassPosition(tmpVec));
			this.rbA.applyImpulse(impulse_vector, tmp);
			tmp.negate(impulse_vector);
			tmp2.sub(pivotBInW, this.rbB.getCenterOfMassPosition(tmpVec));
			this.rbB.applyImpulse(tmp, tmp2);

			normal.set(i, 0f, i == 2);
		}
	}
	
	public void updateRHS(float timeStep) {
	}

	public void setPivotA(Vector pivotA) {
		this.pivotInA.set(pivotA);
	}

	public void setPivotB(Vector pivotB) {
		this.pivotInB.set(pivotB);
	}

	public Vector getPivotInA(Vector out) {
		out.set(this.pivotInA);
		return out;
	}

	public Vector getPivotInB(Vector out) {
		out.set(this.pivotInB);
		return out;
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	public static class ConstraintSetting {
		public float tau = 0.3f;
		public float damping = 1f;
		public float impulseClamp = 0f;
	}
	
}
