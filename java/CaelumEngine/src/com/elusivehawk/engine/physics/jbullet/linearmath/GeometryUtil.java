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

import static com.elusivehawk.util.math.MathConst.W;
import static com.elusivehawk.util.math.MathConst.X;
import static com.elusivehawk.util.math.MathConst.Y;
import static com.elusivehawk.util.math.MathConst.Z;
import com.elusivehawk.engine.physics.jbullet.util.ObjectArrayList;
import com.elusivehawk.util.math.Vector;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * GeometryUtil helper class provides a few methods to convert between plane
 * equations and vertices.
 * 
 * @author jezek2
 */
public class GeometryUtil {

	public static boolean isPointInsidePlanes(ObjectArrayList<Vector> planeEquations, Vector point, float margin) {
		int numbrushes = planeEquations.size();
		for (int i = 0; i < numbrushes; i++) {
			Vector N1 = planeEquations.getQuick(i);
			float dist = VectorUtil.dot3(N1, point) + N1.get(W) - margin;
			if (dist > 0f) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean areVerticesBehindPlane(Vector planeNormal, ObjectArrayList<Vector> vertices, float margin) {
		int numvertices = vertices.size();
		for (int i = 0; i < numvertices; i++) {
			Vector N1 = vertices.getQuick(i);
			float dist = VectorUtil.dot3(planeNormal, N1) + planeNormal.get(W) - margin;
			if (dist > 0f) {
				return false;
			}
		}
		return true;
	}
	
	private static boolean notExist(Vector planeEquation, ObjectArrayList<Vector> planeEquations) {
		int numbrushes = planeEquations.size();
		for (int i = 0; i < numbrushes; i++) {
			Vector N1 = planeEquations.getQuick(i);
			if (VectorUtil.dot3(planeEquation, N1) > 0.999f) {
				return false;
			}
		}
		return true;
	}

	public static void getPlaneEquationsFromVertices(ObjectArrayList<Vector> vertices, ObjectArrayList<Vector> planeEquationsOut) {
		Vector planeEquation = Stack.alloc(Vector.class);
		Vector edge0 = Stack.alloc(new Vector(3)), edge1 = Stack.alloc(new Vector(3));
		Vector tmp = Stack.alloc(new Vector(3));

		int numvertices = vertices.size();
		// brute force:
		for (int i = 0; i < numvertices; i++) {
			Vector N1 = vertices.getQuick(i);

			for (int j = i + 1; j < numvertices; j++) {
				Vector N2 = vertices.getQuick(j);

				for (int k = j + 1; k < numvertices; k++) {
					Vector N3 = vertices.getQuick(k);

					edge0.sub(N2, N1);
					edge1.sub(N3, N1);
					float normalSign = 1f;
					for (int ww = 0; ww < 2; ww++) {
						tmp.cross(edge0, edge1);
						planeEquation.set(X, normalSign * tmp.get(X), false);
						planeEquation.set(Y, normalSign * tmp.get(Y), false);
						planeEquation.set(Z, normalSign * tmp.get(Z), true);

						if (VectorUtil.lengthSquared(planeEquation) > 0.0001f) {
							VectorUtil.normalize3(planeEquation);
							if (notExist(planeEquation, planeEquationsOut)) {
								planeEquation.set(W, -VectorUtil.dot3(planeEquation, N1));

								// check if inside, and replace supportingVertexOut if needed
								if (areVerticesBehindPlane(planeEquation, vertices, 0.01f)) {
									planeEquationsOut.add(new Vector(planeEquation));
								}
							}
						}
						normalSign = -1f;
					}
				}
			}
		}
	}
	
	public static void getVerticesFromPlaneEquations(ObjectArrayList<Vector> planeEquations, ObjectArrayList<Vector> verticesOut) {
		Vector n2n3 = Stack.alloc(new Vector(3));
		Vector n3n1 = Stack.alloc(new Vector(3));
		Vector n1n2 = Stack.alloc(new Vector(3));
		Vector potentialVertex = Stack.alloc(new Vector(3));

		int numbrushes = planeEquations.size();
		// brute force:
		for (int i = 0; i < numbrushes; i++) {
			Vector N1 = planeEquations.getQuick(i);

			for (int j = i + 1; j < numbrushes; j++) {
				Vector N2 = planeEquations.getQuick(j);

				for (int k = j + 1; k < numbrushes; k++) {
					Vector N3 = planeEquations.getQuick(k);

					VectorUtil.cross3(n2n3, N2, N3);
					VectorUtil.cross3(n3n1, N3, N1);
					VectorUtil.cross3(n1n2, N1, N2);

					if ((n2n3.lengthSquared() > 0.0001f) &&
							(n3n1.lengthSquared() > 0.0001f) &&
							(n1n2.lengthSquared() > 0.0001f)) {
						// point P out of 3 plane equations:

						// 	     d1 ( N2 * N3 ) + d2 ( N3 * N1 ) + d3 ( N1 * N2 )  
						// P =  -------------------------------------------------------------------------  
						//    N1 . ( N2 * N3 )  

						float quotient = VectorUtil.dot3(N1, n2n3);
						if (Math.abs(quotient) > 0.000001f) {
							quotient = -1f / quotient;
							n2n3.mulAll(N1.get(W));
							n3n1.mulAll(N2.get(W));
							n1n2.mulAll(N3.get(W));
							potentialVertex.set(n2n3);
							potentialVertex.add(n3n1);
							potentialVertex.add(n1n2);
							potentialVertex.mulAll(quotient);

							// check if inside, and replace supportingVertexOut if needed
							if (isPointInsidePlanes(planeEquations, potentialVertex, 0.01f)) {
								verticesOut.add(new Vector(potentialVertex));
							}
						}
					}
				}
			}
		}
	}
	
}
