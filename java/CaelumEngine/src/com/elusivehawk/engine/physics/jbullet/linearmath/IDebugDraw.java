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

import static com.elusivehawk.engine.math.MathConst.X;
import static com.elusivehawk.engine.math.MathConst.Y;
import static com.elusivehawk.engine.math.MathConst.Z;
import com.elusivehawk.engine.math.Vector;
import com.elusivehawk.engine.physics.jbullet.collision.dispatch.CollisionWorld;
import com.elusivehawk.engine.physics.jbullet.dynamics.DynamicsWorld;
import cz.advel.stack.Stack;

/**
 * IDebugDraw interface class allows hooking up a debug renderer to visually debug
 * simulations.<p>
 * 
 * Typical use case: create a debug drawer object, and assign it to a {@link CollisionWorld}
 * or {@link DynamicsWorld} using setDebugDrawer and call debugDrawWorld.<p>
 * 
 * A class that implements the IDebugDraw interface has to implement the drawLine
 * method at a minimum.
 * 
 * @author jezek2
 */
public abstract class IDebugDraw {
	
	//protected final BulletStack stack = BulletStack.get();

	public abstract void drawLine(Vector from, Vector to, Vector color);
	
	public void drawTriangle(Vector v0, Vector v1, Vector v2, Vector n0, Vector n1, Vector n2, Vector color, float alpha) {
		drawTriangle(v0, v1, v2, color, alpha);
	}
	
	public void drawTriangle(Vector v0, Vector v1, Vector v2, Vector color, float alpha) {
		drawLine(v0, v1, color);
		drawLine(v1, v2, color);
		drawLine(v2, v0, color);
	}

	public abstract void drawContactPoint(Vector PointOnB, Vector normalOnB, float distance, int lifeTime, Vector color);

	public abstract void reportErrorWarning(String warningString);

	public abstract void draw3dText(Vector location, String textString);

	public abstract void setDebugMode(int debugMode);

	public abstract int getDebugMode();

	public void drawAabb(Vector from, Vector to, Vector color) {
		Vector halfExtents = Stack.alloc(to);
		halfExtents.sub(from);
		halfExtents.scale(0.5f);

		Vector center = Stack.alloc(to);
		center.add(from);
		center.scale(0.5f);

		int i, j;

		Vector edgecoord = Stack.alloc(new Vector(3));
		edgecoord.set(1f, 1f, 1f);
		Vector pa = Stack.alloc(new Vector(3)), pb = Stack.alloc(new Vector(3));
		for (i = 0; i < 4; i++) {
			for (j = 0; j < 3; j++) {
				pa.set(edgecoord.get(X) * halfExtents.get(X), edgecoord.get(Y) * halfExtents.get(Y), edgecoord.get(Z) * halfExtents.get(Z));
				pa.add(center);

				int othercoord = j % 3;

				edgecoord.mul(othercoord, -1f, false);
				pb.set(edgecoord.get(X) * halfExtents.get(X), edgecoord.get(Y) * halfExtents.get(Y), edgecoord.get(Z) * halfExtents.get(Z));
				pb.add(center);

				drawLine(pa, pb, color);
			}
			edgecoord.set(-1f, -1f, -1f);
			if (i < 3) {
				edgecoord.mul(i, -1f, false);
			}
		}
	}
}
