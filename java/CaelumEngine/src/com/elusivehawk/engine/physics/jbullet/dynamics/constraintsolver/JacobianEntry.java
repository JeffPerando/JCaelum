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

import static com.elusivehawk.util.math.MathConst.*;
import com.elusivehawk.engine.physics.jbullet.BulletGlobals;
import com.elusivehawk.engine.physics.jbullet.linearmath.VectorUtil;
import com.elusivehawk.util.math.Matrix;
import com.elusivehawk.util.math.Vector;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
//notes:
// Another memory optimization would be to store m_1MinvJt in the remaining 3 w components
// which makes the btJacobianEntry memory layout 16 bytes
// if you only are interested in angular part, just feed massInvA and massInvB zero

/**
 * Jacobian entry is an abstraction that allows to describe constraints.
 * It can be used in combination with a constraint solver.
 * Can be used to relate the effect of an impulse to the constraint error.
 * 
 * @author jezek2
 */
public class JacobianEntry {
	
	//protected final BulletStack stack = BulletStack.get();
	
	public final Vector linearJointAxis = new Vector();
	public final Vector aJ = new Vector();
	public final Vector bJ = new Vector();
	public final Vector m_0MinvJt = new Vector();
	public final Vector m_1MinvJt = new Vector();
	// Optimization: can be stored in the w/last component of one of the vectors
	public float Adiag;

	public JacobianEntry() {
	}

	/**
	 * Constraint between two different rigidbodies.
	 */
	public void init(Matrix world2A,
			Matrix world2B,
			Vector rel_pos1, Vector rel_pos2,
			Vector jointAxis,
			Vector inertiaInvA,
			float massInvA,
			Vector inertiaInvB,
			float massInvB)
	{
		linearJointAxis.set(jointAxis);

		aJ.cross(rel_pos1, linearJointAxis);
		world2A.transform(aJ);

		bJ.set(linearJointAxis);
		bJ.negate();
		bJ.cross(rel_pos2, bJ);
		world2B.transform(bJ);

		VectorUtil.mul(m_0MinvJt, inertiaInvA, aJ);
		VectorUtil.mul(m_1MinvJt, inertiaInvB, bJ);
		Adiag = massInvA + m_0MinvJt.dot(aJ) + massInvB + m_1MinvJt.dot(bJ);

		assert (Adiag > 0f);
	}

	/**
	 * Angular constraint between two different rigidbodies.
	 */
	public void init(Vector jointAxis,
		Matrix world2A,
		Matrix world2B,
		Vector inertiaInvA,
		Vector inertiaInvB)
	{
		linearJointAxis.set(0f, 0f, 0f);

		aJ.set(jointAxis);
		world2A.transform(aJ);

		bJ.set(jointAxis);
		bJ.negate();
		world2B.transform(bJ);

		VectorUtil.mul(m_0MinvJt, inertiaInvA, aJ);
		VectorUtil.mul(m_1MinvJt, inertiaInvB, bJ);
		Adiag = m_0MinvJt.dot(aJ) + m_1MinvJt.dot(bJ);

		assert (Adiag > 0f);
	}

	/**
	 * Angular constraint between two different rigidbodies.
	 */
	public void init(Vector axisInA,
		Vector axisInB,
		Vector inertiaInvA,
		Vector inertiaInvB)
	{
		linearJointAxis.set(0f, 0f, 0f);
		aJ.set(axisInA);

		bJ.set(axisInB);
		bJ.negate();

		VectorUtil.mul(m_0MinvJt, inertiaInvA, aJ);
		VectorUtil.mul(m_1MinvJt, inertiaInvB, bJ);
		Adiag = m_0MinvJt.dot(aJ) + m_1MinvJt.dot(bJ);

		assert (Adiag > 0f);
	}

	/**
	 * Constraint on one rigidbody.
	 */
	public void init(
		Matrix world2A,
		Vector rel_pos1, Vector rel_pos2,
		Vector jointAxis,
		Vector inertiaInvA, 
		float massInvA)
	{
		linearJointAxis.set(jointAxis);

		aJ.cross(rel_pos1, jointAxis);
		world2A.transform(aJ);

		bJ.set(jointAxis);
		bJ.negate();
		bJ.cross(rel_pos2, bJ);
		world2A.transform(bJ);

		VectorUtil.mul(m_0MinvJt, inertiaInvA, aJ);
		m_1MinvJt.set(0f, 0f, 0f);
		Adiag = massInvA + m_0MinvJt.dot(aJ);

		assert (Adiag > 0f);
	}

	public float getDiagonal() { return Adiag; }

	/**
	 * For two constraints on the same rigidbody (for example vehicle friction).
	 */
	public float getNonDiagonal(JacobianEntry jacB, float massInvA) {
		JacobianEntry jacA = this;
		float lin = massInvA * jacA.linearJointAxis.dot(jacB.linearJointAxis);
		float ang = jacA.m_0MinvJt.dot(jacB.aJ);
		return lin + ang;
	}

	/**
	 * For two constraints on sharing two same rigidbodies (for example two contact points between two rigidbodies).
	 */
	public float getNonDiagonal(JacobianEntry jacB, float massInvA, float massInvB) {
		JacobianEntry jacA = this;

		Vector lin = Stack.alloc(new Vector(3));
		VectorUtil.mul(lin, jacA.linearJointAxis, jacB.linearJointAxis);

		Vector ang0 = Stack.alloc(new Vector(3));
		VectorUtil.mul(ang0, jacA.m_0MinvJt, jacB.aJ);

		Vector ang1 = Stack.alloc(new Vector(3));
		VectorUtil.mul(ang1, jacA.m_1MinvJt, jacB.bJ);

		Vector lin0 = Stack.alloc(new Vector(3));
		lin0.scale(massInvA, lin);

		Vector lin1 = Stack.alloc(new Vector(3));
		lin1.scale(massInvB, lin);

		Vector sum = Stack.alloc(new Vector(3));
		VectorUtil.add(sum, ang0, ang1, lin0, lin1);

		return sum.get(X)+ sum.get(Y)+ sum.get(Z);
	}

	public float getRelativeVelocity(Vector linvelA, Vector angvelA, Vector linvelB, Vector angvelB) {
		Vector linrel = Stack.alloc(new Vector(3));
		linrel.sub(linvelA, linvelB);

		Vector angvela = Stack.alloc(new Vector(3));
		VectorUtil.mul(angvela, angvelA, aJ);

		Vector angvelb = Stack.alloc(new Vector(3));
		VectorUtil.mul(angvelb, angvelB, bJ);

		VectorUtil.mul(linrel, linrel, linearJointAxis);

		angvela.add(angvelb);
		angvela.add(linrel);

		float rel_vel2 = angvela.get(X)+ angvela.get(Y)+ angvela.get(Z);
		return rel_vel2 + BulletGlobals.FLT_EPSILON;
	}
	
}
