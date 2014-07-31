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

import com.elusivehawk.engine.physics.jbullet.dynamics.RigidBody;
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import com.elusivehawk.engine.physics.jbullet.linearmath.TransformUtil;
import com.elusivehawk.util.math.Vector;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * SolverBody is an internal data structure for the constraint solver. Only necessary
 * data is packed to increase cache coherence/performance.
 * 
 * @author jezek2
 */
public class SolverBody {
	
	//protected final BulletStack stack = BulletStack.get();

	public final Vector angularVelocity = new Vector();
	public float angularFactor;
	public float invMass;
	public float friction;
	public RigidBody originalBody;
	public final Vector linearVelocity = new Vector();
	public final Vector centerOfMassPosition = new Vector();

	public final Vector pushVelocity = new Vector();
	public final Vector turnVelocity = new Vector();
	
	public void getVelocityInLocalPoint(Vector rel_pos, Vector velocity) {
		Vector tmp = Stack.alloc(new Vector(3));
		tmp.cross(this.angularVelocity, rel_pos);
		velocity.add(this.linearVelocity, tmp);
	}

	/**
	 * Optimization for the iterative solver: avoid calculating constant terms involving inertia, normal, relative position.
	 */
	public void internalApplyImpulse(Vector linearComponent, Vector angularComponent, float impulseMagnitude) {
		if (this.invMass != 0f) {
			this.linearVelocity.scaleAdd(impulseMagnitude, linearComponent, this.linearVelocity);
			this.angularVelocity.scaleAdd(impulseMagnitude * this.angularFactor, angularComponent, this.angularVelocity);
		}
	}

	public void internalApplyPushImpulse(Vector linearComponent, Vector angularComponent, float impulseMagnitude) {
		if (this.invMass != 0f) {
			this.pushVelocity.scaleAdd(impulseMagnitude, linearComponent, this.pushVelocity);
			this.turnVelocity.scaleAdd(impulseMagnitude * this.angularFactor, angularComponent, this.turnVelocity);
		}
	}
	
	public void writebackVelocity() {
		if (this.invMass != 0f) {
			this.originalBody.setLinearVelocity(this.linearVelocity);
			this.originalBody.setAngularVelocity(this.angularVelocity);
			//m_originalBody->setCompanionId(-1);
		}
	}

	public void writebackVelocity(float timeStep) {
		if (this.invMass != 0f) {
			this.originalBody.setLinearVelocity(this.linearVelocity);
			this.originalBody.setAngularVelocity(this.angularVelocity);

			// correct the position/orientation based on push/turn recovery
			Transform newTransform = Stack.alloc(Transform.class);
			Transform curTrans = this.originalBody.getWorldTransform(Stack.alloc(Transform.class));
			TransformUtil.integrateTransform(curTrans, this.pushVelocity, this.turnVelocity, timeStep, newTransform);
			this.originalBody.setWorldTransform(newTransform);

			//m_originalBody->setCompanionId(-1);
		}
	}
	
	public void readVelocity() {
		if (this.invMass != 0f) {
			this.originalBody.getLinearVelocity(this.linearVelocity);
			this.originalBody.getAngularVelocity(this.angularVelocity);
		}
	}
	
}
