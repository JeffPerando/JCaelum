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
2007-09-09
btGeneric6DofConstraint Refactored by Francisco Le�n
email: projectileman@yahoo.com
http://gimpact.sf.net
*/

package com.elusivehawk.engine.physics.jbullet.dynamics.constraintsolver;

import com.elusivehawk.engine.physics.jbullet.dynamics.RigidBody;
import com.elusivehawk.util.math.Vector;
import cz.advel.stack.Stack;
import cz.advel.stack.StaticAlloc;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 *
 * @author jezek2
 */
public class TranslationalLimitMotor {
	
	//protected final BulletStack stack = BulletStack.get();
	
	public final Vector lowerLimit = new Vector(); //!< the constraint lower limits
	public final Vector upperLimit = new Vector(); //!< the constraint upper limits
	public final Vector accumulatedImpulse = new Vector();
	
	public float limitSoftness; //!< Softness for linear limit
	public float damping; //!< Damping for linear limit
	public float restitution; //! Bounce parameter for linear limit

	@SuppressWarnings("unqualified-field-access")
	public TranslationalLimitMotor() {
		lowerLimit.set(0f, 0f, 0f);
		upperLimit.set(0f, 0f, 0f);
		accumulatedImpulse.set(0f, 0f, 0f);

		limitSoftness = 0.7f;
		damping = 1.0f;
		restitution = 0.5f;
	}

	@SuppressWarnings("unqualified-field-access")
	public TranslationalLimitMotor(TranslationalLimitMotor other) {
		lowerLimit.set(other.lowerLimit);
		upperLimit.set(other.upperLimit);
		accumulatedImpulse.set(other.accumulatedImpulse);

		limitSoftness = other.limitSoftness;
		damping = other.damping;
		restitution = other.restitution;
	}

	/**
	 * Test limit.<p>
	 * - free means upper &lt; lower,<br>
	 * - locked means upper == lower<br>
	 * - limited means upper &gt; lower<br>
	 * - limitIndex: first 3 are linear, next 3 are angular
	 */
	public boolean isLimited(int limitIndex) {
		return (this.upperLimit.get(limitIndex) >= this.lowerLimit.get(limitIndex));
	}

	@StaticAlloc
	public float solveLinearAxis(float timeStep, float jacDiagABInv, RigidBody body1, Vector pointInA, RigidBody body2, Vector pointInB, int limit_index, Vector axis_normal_on_a, Vector anchorPos) {
		Vector tmp = Stack.alloc(new Vector(3));
		Vector tmpVec = Stack.alloc(new Vector(3));
		
		// find relative velocity
		Vector rel_pos1 = Stack.alloc(new Vector(3));
		//rel_pos1.sub(pointInA, body1.getCenterOfMassPosition(tmpVec));
		rel_pos1.sub(anchorPos, body1.getCenterOfMassPosition(tmpVec));

		Vector rel_pos2 = Stack.alloc(new Vector(3));
		//rel_pos2.sub(pointInB, body2.getCenterOfMassPosition(tmpVec));
		rel_pos2.sub(anchorPos, body2.getCenterOfMassPosition(tmpVec));

		Vector vel1 = body1.getVelocityInLocalPoint(rel_pos1, Stack.alloc(new Vector(3)));
		Vector vel2 = body2.getVelocityInLocalPoint(rel_pos2, Stack.alloc(new Vector(3)));
		Vector vel = Stack.alloc(new Vector(3));
		vel.sub(vel1, vel2);

		float rel_vel = axis_normal_on_a.dot(vel);

		// apply displacement correction

		// positional error (zeroth order error)
		tmp.sub(pointInA, pointInB);
		float depth = -(tmp).dot(axis_normal_on_a);
		float lo = -1e30f;
		float hi = 1e30f;

		float minLimit = this.lowerLimit.get(limit_index);
		float maxLimit = this.upperLimit.get(limit_index);

		// handle the limits
		if (minLimit < maxLimit) {
			{
				if (depth > maxLimit) {
					depth -= maxLimit;
					lo = 0f;

				}
				else {
					if (depth < minLimit) {
						depth -= minLimit;
						hi = 0f;
					}
					else {
						return 0.0f;
					}
				}
			}
		}

		float normalImpulse = this.limitSoftness * (this.restitution * depth / timeStep - this.damping * rel_vel) * jacDiagABInv;

		float oldNormalImpulse = this.accumulatedImpulse.get(limit_index);
		float sum = oldNormalImpulse + normalImpulse;
		this.accumulatedImpulse.set(limit_index, sum > hi ? 0f : sum < lo ? 0f : sum);
		normalImpulse = this.accumulatedImpulse.get(limit_index) - oldNormalImpulse;

		Vector impulse_vector = Stack.alloc(new Vector(3));
		impulse_vector.scale(normalImpulse, axis_normal_on_a);
		body1.applyImpulse(impulse_vector, rel_pos1);

		tmp.negate(impulse_vector);
		body2.applyImpulse(tmp, rel_pos2);
		return normalImpulse;
	}
	
}
