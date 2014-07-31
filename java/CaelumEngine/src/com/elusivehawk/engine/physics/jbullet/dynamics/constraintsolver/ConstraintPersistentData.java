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

import com.elusivehawk.util.math.Vector;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * Stores some extra information to each contact point. It is not in the contact
 * point, because that want to keep the collision detection independent from the
 * constraint solver.
 * 
 * @author jezek2
 */
public class ConstraintPersistentData {
	
	/** total applied impulse during most recent frame */
	public float appliedImpulse = 0f;
	public float prevAppliedImpulse = 0f;
	public float accumulatedTangentImpulse0 = 0f;
	public float accumulatedTangentImpulse1 = 0f;

	public float jacDiagABInv = 0f;
	public float jacDiagABInvTangent0;
	public float jacDiagABInvTangent1;
	public int persistentLifeTime = 0;
	public float restitution = 0f;
	public float friction = 0f;
	public float penetration = 0f;
	public final Vector frictionWorldTangential0 = new Vector();
	public final Vector frictionWorldTangential1 = new Vector();

	public final Vector frictionAngularComponent0A = new Vector();
	public final Vector frictionAngularComponent0B = new Vector();
	public final Vector frictionAngularComponent1A = new Vector();
	public final Vector frictionAngularComponent1B = new Vector();

	//some data doesn't need to be persistent over frames: todo: clean/reuse this
	public final Vector angularComponentA = new Vector();
	public final Vector angularComponentB = new Vector();

	public ContactSolverFunc contactSolverFunc = null;
	public ContactSolverFunc frictionSolverFunc = null;
	
	public void reset() {
		this.appliedImpulse = 0f;
		this.prevAppliedImpulse = 0f;
		this.accumulatedTangentImpulse0 = 0f;
		this.accumulatedTangentImpulse1 = 0f;

		this.jacDiagABInv = 0f;
		this.jacDiagABInvTangent0 = 0f;
		this.jacDiagABInvTangent1 = 0f;
		this.persistentLifeTime = 0;
		this.restitution = 0f;
		this.friction = 0f;
		this.penetration = 0f;
		this.frictionWorldTangential0.set(0f, 0f, 0f);
		this.frictionWorldTangential1.set(0f, 0f, 0f);

		this.frictionAngularComponent0A.set(0f, 0f, 0f);
		this.frictionAngularComponent0B.set(0f, 0f, 0f);
		this.frictionAngularComponent1A.set(0f, 0f, 0f);
		this.frictionAngularComponent1B.set(0f, 0f, 0f);

		this.angularComponentA.set(0f, 0f, 0f);
		this.angularComponentB.set(0f, 0f, 0f);

		this.contactSolverFunc = null;
		this.frictionSolverFunc = null;
	}
	
}
