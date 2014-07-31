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

package com.elusivehawk.engine.physics.jbullet.linearmath;

import static com.elusivehawk.util.math.MathConst.X;
import static com.elusivehawk.util.math.MathConst.Y;
import static com.elusivehawk.util.math.MathConst.Z;
import com.elusivehawk.engine.physics.jbullet.BulletGlobals;
import com.elusivehawk.util.math.Matrix;
import com.elusivehawk.util.math.Quaternion;
import com.elusivehawk.util.math.Vector;
import cz.advel.stack.Stack;
import cz.advel.stack.StaticAlloc;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * Utility functions for transforms.
 * 
 * @author jezek2
 */
public class TransformUtil {
	
	public static final float SIMDSQRT12 = 0.7071067811865475244008443621048490f;
	public static final float ANGULAR_MOTION_THRESHOLD = 0.5f*BulletGlobals.SIMD_HALF_PI;
	
	public static float recipSqrt(float x) {
		return 1f / (float)Math.sqrt(x);  /* reciprocal square root */
	}

	public static void planeSpace1(Vector n, Vector p, Vector q) {
		if (Math.abs(n.get(Z)) > SIMDSQRT12) {
			// choose p in y-z plane
			float a = n.get(Y) * n.get(Y) + n.get(Z) * n.get(Z);
			float k = recipSqrt(a);
			p.set(0, -n.get(Z) * k, n.get(Y) * k);
			// set q = n x p
			q.set(a * k, -n.get(X) * p.get(Z), n.get(X) * p.get(Y));
		}
		else {
			// choose p in x-y plane
			float a = n.get(X) * n.get(X) + n.get(Y) * n.get(Y);
			float k = recipSqrt(a);
			p.set(-n.get(Y) * k, n.get(X) * k, 0);
			// set q = n x p
			q.set(-n.get(Z) * p.get(Y), n.get(Z) * p.get(X), a * k);
		}
	}
	
	@StaticAlloc
	public static void integrateTransform(Transform curTrans, Vector linvel, Vector angvel, float timeStep, Transform predictedTransform) {
		predictedTransform.origin.scaleAdd(timeStep, linvel, curTrans.origin);
//	//#define QUATERNION_DERIVATIVE
//	#ifdef QUATERNION_DERIVATIVE
//		btQuaternion predictedOrn = curTrans.getRotation();
//		predictedOrn += (angvel * predictedOrn) * (timeStep * btScalar(0.5));
//		predictedOrn.normalize();
//	#else
		// Exponential map
		// google for "Practical Parameterization of Rotations Using the Exponential Map", F. Sebastian Grassia
		
		Vector axis = Stack.alloc(new Vector(3));
		float fAngle = angvel.length();

		// limit the angular motion
		if (fAngle * timeStep > ANGULAR_MOTION_THRESHOLD) {
			fAngle = ANGULAR_MOTION_THRESHOLD / timeStep;
		}

		if (fAngle < 0.001f) {
			// use Taylor's expansions of sync function
			axis.scale(0.5f * timeStep - (timeStep * timeStep * timeStep) * (0.020833333333f) * fAngle * fAngle, angvel);
		}
		else {
			// sync(fAngle) = sin(c*fAngle)/t
			axis.scale((float) Math.sin(0.5f * fAngle * timeStep) / fAngle, angvel);
		}
		Quaternion dorn = Stack.alloc(Quaternion.class);
		for (int c = 0; c < 3; c++)
		{
			dorn.set(c, axis.get(c), false);
		}
		dorn.set(3, (float)Math.cos(fAngle * timeStep * 0.5f));
		//dorn.set(axis.get(X), axis.get(Y), axis.get(Z), (float) Math.cos(fAngle * timeStep * 0.5f));
		Quaternion orn0 = curTrans.getRotation(Stack.alloc(Quaternion.class));

		Quaternion predictedOrn = Stack.alloc(Quaternion.class);
		predictedOrn.mul(dorn, orn0);
		predictedOrn.normalize();
//  #endif
		predictedTransform.setRotation(predictedOrn);
	}

	public static void calculateVelocity(Transform transform0, Transform transform1, float timeStep, Vector linVel, Vector angVel) {
		linVel.sub(transform1.origin, transform0.origin);
		linVel.scale(1f / timeStep);

		Vector axis = Stack.alloc(new Vector(3));
		float[] angle = new float[1];
		calculateDiffAxisAngle(transform0, transform1, axis, angle);
		angVel.scale(angle[0] / timeStep, axis);
	}
	
	public static void calculateDiffAxisAngle(Transform transform0, Transform transform1, Vector axis, float[] angle) {
// #ifdef USE_QUATERNION_DIFF
//		btQuaternion orn0 = transform0.getRotation();
//		btQuaternion orn1a = transform1.getRotation();
//		btQuaternion orn1 = orn0.farthest(orn1a);
//		btQuaternion dorn = orn1 * orn0.inverse();
// #else
		Matrix tmp = Stack.alloc(new Matrix(3, 3));
		tmp.set(transform0.basis);
		MatrixUtil.invert(tmp);

		Matrix dmat = Stack.alloc(new Matrix(3, 3));
		dmat.mul(transform1.basis, tmp);

		Quaternion dorn = Stack.alloc(Quaternion.class);
		MatrixUtil.getRotation(dmat, dorn);
// #endif

		// floating point inaccuracy can lead to w component > 1..., which breaks 

		dorn.normalize();

		angle[0] = QuaternionUtil.getAngle(dorn);
		axis.set(dorn);
		// TODO: probably not needed
		//axis[3] = btScalar(0.);

		// check for axis length
		float len = axis.lengthSquared();
		if (len < BulletGlobals.FLT_EPSILON * BulletGlobals.FLT_EPSILON) {
			axis.set(1f, 0f, 0f);
		}
		else {
			axis.scale(1f / (float) Math.sqrt(len));
		}
	}
	
}
