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
import com.elusivehawk.engine.physics.jbullet.BulletGlobals;
import com.elusivehawk.util.math.Quaternion;
import com.elusivehawk.util.math.Vector;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * Utility functions for quaternions.
 * 
 * @author jezek2
 */
public class QuaternionUtil {

	public static float getAngle(Quaternion q) {
		float s = 2f * (float) Math.acos(q.get(W));
		return s;
	}
	
	public static void setRotation(Quaternion q, Vector axis, float angle) {
		float d = axis.length();
		assert (d != 0f);
		float s = (float)Math.sin(angle * 0.5f) / d;
		
		for (int c = 0; c < 3; c++)
		{
			q.set(c, axis.get(c) * s, false);
			
		}
		q.set(3, (float)Math.cos(angle * 0.5f));
	}
	
	// Game Programming Gems 2.10. make sure v0,v1 are normalized
	public static Quaternion shortestArcQuat(Vector v0, Vector v1, Quaternion out) {
		Vector c = Stack.alloc(new Vector(3));
		c.cross(v0, v1);
		float d = v0.dot(v1);

		if (d < -1.0 + BulletGlobals.FLT_EPSILON) {
			// just pick any vector
			out.set(new Vector(0.0f, 1.0f, 0.0f, 0.0f));
			return out;
		}

		float s = (float)Math.sqrt((1.0f + d) * 2.0f);
		float rs = 1.0f / s;
		
		for (int count = 0; count < 3; count++)
		{
			out.set(count, c.get(count) * rs, false);
			
		}
		out.set(3, s * 0.5f, false);
		return out;
	}
	
	public static void mul(Quaternion q, Vector v) {
		float x = q.get(X),
				y = q.get(Y),
				z = q.get(Z),
				w = q.get(W);
		
		float rx = w * v.get(X)+ y * v.get(Z)- z * v.get(Y);
		float ry = w * v.get(Y)+ z * v.get(X)- x * v.get(Z);
		float rz = w * v.get(Z)+ x * v.get(Y)- y * v.get(X);
		float rw = -x * v.get(X)- y * v.get(Y)- z * v.get(Z);
		
		q.set(0, rx, false);
		q.set(1, ry, false);
		q.set(2, rz, false);
		q.set(3, rw);
		
	}
	
	public static Vector quatRotate(Quaternion rotation, Vector v, Vector out) {
		Quaternion q = Stack.alloc(rotation);
		mul(q, v);

		Quaternion tmp = Stack.alloc(Quaternion.class);
		inverse(tmp, rotation);
		q.mul(tmp);
		
		for (int c = 0; c < 3; c++)
		{
			out.set(c, q.get(c), c == 2);
			
		}
		//out.set(q.x, q.y, q.z);
		return out;
	}
	
	public static void inverse(Quaternion q)
	{
		for (int c = 0; c < q.getSize(); c++)
		{
			q.set(c, -q.get(c), false);
			
		}
		
		q.onChanged();
	}
	
	public static void inverse(Quaternion q, Quaternion src)
	{
		q.set(3, src.get(3), false);
		
		for (int c = 0; c < 3; c++)
		{
			q.set(c, -src.get(c), false);
			
		}
		
		q.onChanged();
		/*q.set(X, -src.x;
		q.set(Y, -src.y;
		q.get(Z)= -src.z;
		q.get(W)= src.w;*/
	}

	public static void setEuler(Quaternion q, float yaw, float pitch, float roll) {
		float halfYaw = yaw * 0.5f;
		float halfPitch = pitch * 0.5f;
		float halfRoll = roll * 0.5f;
		float cosYaw = (float)Math.cos(halfYaw);
		float sinYaw = (float)Math.sin(halfYaw);
		float cosPitch = (float)Math.cos(halfPitch);
		float sinPitch = (float)Math.sin(halfPitch);
		float cosRoll = (float)Math.cos(halfRoll);
		float sinRoll = (float)Math.sin(halfRoll);
		q.set(0, cosRoll * sinPitch * cosYaw + sinRoll * cosPitch * sinYaw, false);
		q.set(1, cosRoll * cosPitch * sinYaw - sinRoll * sinPitch * cosYaw, false);
		q.set(2, sinRoll * cosPitch * cosYaw - cosRoll * sinPitch * sinYaw, false);
		q.set(3, cosRoll * cosPitch * cosYaw + sinRoll * sinPitch * sinYaw, true);
	}

}
