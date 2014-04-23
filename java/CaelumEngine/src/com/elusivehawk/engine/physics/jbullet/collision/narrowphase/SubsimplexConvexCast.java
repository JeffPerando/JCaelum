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

package com.elusivehawk.engine.physics.jbullet.collision.narrowphase;

import com.elusivehawk.engine.math.Vector3f;
import com.elusivehawk.engine.physics.jbullet.BulletGlobals;
import com.elusivehawk.engine.physics.jbullet.collision.shapes.ConvexShape;
import com.elusivehawk.engine.physics.jbullet.linearmath.MatrixUtil;
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import com.elusivehawk.engine.physics.jbullet.linearmath.VectorUtil;
import cz.advel.stack.Stack;

/**
 * SubsimplexConvexCast implements Gino van den Bergens' paper
 * "Ray Casting against bteral Convex Objects with Application to Continuous Collision Detection"
 * GJK based Ray Cast, optimized version
 * Objects should not start in overlap, otherwise results are not defined.
 * 
 * @author jezek2
 */
public class SubsimplexConvexCast extends ConvexCast {

	//protected final BulletStack stack = BulletStack.get();
	
	// Typically the conservative advancement reaches solution in a few iterations, clip it to 32 for degenerate cases.
	// See discussion about this here http://www.bulletphysics.com/phpBB2/viewtopic.php?t=565
	//#ifdef BT_USE_DOUBLE_PRECISION
	//#define MAX_ITERATIONS 64
	//#else
	//#define MAX_ITERATIONS 32
	//#endif
	
	private static final int MAX_ITERATIONS = 32;
	
	private SimplexSolverInterface simplexSolver;
	private ConvexShape convexA;
	private ConvexShape convexB;

	public SubsimplexConvexCast(ConvexShape shapeA, ConvexShape shapeB, SimplexSolverInterface simplexSolver) {
		this.convexA = shapeA;
		this.convexB = shapeB;
		this.simplexSolver = simplexSolver;
	}
	
	public boolean calcTimeOfImpact(Transform fromA, Transform toA, Transform fromB, Transform toB, CastResult result) {
		Vector3f tmp = Stack.alloc(Vector3f.class);
		
		simplexSolver.reset();

		Vector3f linVelA = Stack.alloc(Vector3f.class);
		Vector3f linVelB = Stack.alloc(Vector3f.class);
		linVelA.sub(toA.origin, fromA.origin);
		linVelB.sub(toB.origin, fromB.origin);
		
		float lambda = 0f;
		
		Transform interpolatedTransA = Stack.alloc(fromA);
		Transform interpolatedTransB = Stack.alloc(fromB);

		// take relative motion
		Vector3f r = Stack.alloc(Vector3f.class);
		r.sub(linVelA, linVelB);
		
		Vector3f v = Stack.alloc(Vector3f.class);

		tmp.negate(r);
		MatrixUtil.transposeTransform(tmp, tmp, fromA.basis);
		Vector3f supVertexA = convexA.localGetSupportingVertex(tmp, Stack.alloc(Vector3f.class));
		fromA.transform(supVertexA);
		
		MatrixUtil.transposeTransform(tmp, r, fromB.basis);
		Vector3f supVertexB = convexB.localGetSupportingVertex(tmp, Stack.alloc(Vector3f.class));
		fromB.transform(supVertexB);
		
		v.sub(supVertexA, supVertexB);
		
		int maxIter = MAX_ITERATIONS;

		Vector3f n = Stack.alloc(Vector3f.class);
		n.set(0f, 0f, 0f);
		boolean hasResult = false;
		Vector3f c = Stack.alloc(Vector3f.class);

		float lastLambda = lambda;

		float dist2 = v.lengthSquared();
		//#ifdef BT_USE_DOUBLE_PRECISION
		//	btScalar epsilon = btScalar(0.0001);
		//#else
		float epsilon = 0.0001f;
		//#endif
		Vector3f w = Stack.alloc(Vector3f.class), p = Stack.alloc(Vector3f.class);
		float VdotR;

		while ((dist2 > epsilon) && (maxIter--) != 0) {
			tmp.negate(v);
			MatrixUtil.transposeTransform(tmp, tmp, interpolatedTransA.basis);
			convexA.localGetSupportingVertex(tmp, supVertexA);
			interpolatedTransA.transform(supVertexA);
			
			MatrixUtil.transposeTransform(tmp, v, interpolatedTransB.basis);
			convexB.localGetSupportingVertex(tmp, supVertexB);
			interpolatedTransB.transform(supVertexB);
			
			w.sub(supVertexA, supVertexB);

			float VdotW = v.dot(w);

			if (lambda > 1f) {
				return false;
			}
			
			if (VdotW > 0f) {
				VdotR = v.dot(r);

				if (VdotR >= -(BulletGlobals.FLT_EPSILON * BulletGlobals.FLT_EPSILON)) {
					return false;
				}
				else {
					lambda = lambda - VdotW / VdotR;
					
					// interpolate to next lambda
					//	x = s + lambda * r;
					VectorUtil.setInterpolate(interpolatedTransA.origin, fromA.origin, toA.origin, lambda);
					VectorUtil.setInterpolate(interpolatedTransB.origin, fromB.origin, toB.origin, lambda);
					//m_simplexSolver->reset();
					// check next line
					w.sub(supVertexA, supVertexB);
					lastLambda = lambda;
					n.set(v);
					hasResult = true;
				}
			}
			simplexSolver.addVertex(w, supVertexA , supVertexB);
			if (simplexSolver.closest(v)) {
				dist2 = v.lengthSquared();
				hasResult = true;
				// todo: check this normal for validity
				//n.set(v);
				//printf("V=%f , %f, %f\n",v[0],v[1],v[2]);
				//printf("DIST2=%f\n",dist2);
				//printf("numverts = %i\n",m_simplexSolver->numVertices());
			}
			else {
				dist2 = 0f;
			}
		}

		//int numiter = MAX_ITERATIONS - maxIter;
		//	printf("number of iterations: %d", numiter);
	
		// don't report a time of impact when moving 'away' from the hitnormal
		
		result.fraction = lambda;
		if (n.lengthSquared() >= BulletGlobals.SIMD_EPSILON * BulletGlobals.SIMD_EPSILON) {
			result.normal.normalize(n);
		}
		else {
			result.normal.set(0f, 0f, 0f);
		}

		// don't report time of impact for motion away from the contact normal (or causes minor penetration)
		if (result.normal.dot(r) >= -result.allowedPenetration)
			return false;

		Vector3f hitA = Stack.alloc(Vector3f.class);
		Vector3f hitB = Stack.alloc(Vector3f.class);
		simplexSolver.compute_points(hitA,hitB);
		result.hitPoint.set(hitB);
		return true;
	}

}
