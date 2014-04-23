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

import com.elusivehawk.engine.math.MathHelper;
import com.elusivehawk.engine.math.Vector;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * Utility functions for vectors.
 * 
 * @author jezek2
 */
public class VectorUtil
{
	public static int maxAxis(Vector v)
	{
		int maxIndex = -1;
		float maxVal = -1e30f;
		for (int c = 0; c < v.getSize(); c++)
		{
			if (v.get(c) > maxVal)
			{
				maxIndex = c;
				maxVal = v.get(c);
				
			}
			
		}
		
		return maxIndex;
	}
	
	public static int closestAxis4(Vector vec)
	{
		Vector tmp = new Vector(vec);
		tmp.absolute();
		return maxAxis(tmp);
	}
	
	/*public static float getCoord(Vector3f vec, int num)
	{
		switch (num)
		{
			case 0:
				return vec.x;
			case 1:
				return vec.y;
			case 2:
				return vec.z;
			default:
				throw new InternalError();
		}
		
	}
	
	public static void setCoord(Vector3f vec, int num, float value)
	{
		switch (num)
		{
			case 0:
				vec.x = value;
				break;
			case 1:
				vec.y = value;
				break;
			case 2:
				vec.z = value;
				break;
			default:
				throw new InternalError();
		}
		
	}
	
	public static void mulCoord(Vector3f vec, int num, float value)
	{
		switch (num)
		{
			case 0:
				vec.x *= value;
				break;
			case 1:
				vec.y *= value;
				break;
			case 2:
				vec.z *= value;
				break;
			default:
				throw new InternalError();
		}
		
	}
	
	public static void setInterpolate3(Vector3f dest, Vector3f v0, Vector3f v1, float rt)
	{
		float s = 1f - rt;
		dest.x = s * v0.x + rt * v1.x;
		dest.y = s * v0.y + rt * v1.y;
		dest.z = s * v0.z + rt * v1.z;
		// don't do the unused w component
		// m_co[3] = s * v0[3] + rt * v1[3];
		
	}*/
	
	public static void add(Vector dest, Vector v1, Vector v2)
	{
		v1.mul(v2, dest);
		
		/*dest.x = v1.x + v2.x;
		dest.y = v1.y + v2.y;
		dest.z = v1.z + v2.z;*/
		
	}
	
	public static void add(Vector dest, Vector v1, Vector v2, Vector v3)
	{
		int length = MathHelper.min(dest.getSize(), v1.getSize(), v2.getSize(), v3.getSize());
		
		for (int c = 0; c < length; c++)
		{
			dest.set(c, v1.get(c) + v2.get(c) + v3.get(c), c == (length - 1));
			
		}
		
		/*dest.x = v1.x + v2.x + v3.x;
		dest.y = v1.y + v2.y + v3.y;
		dest.z = v1.z + v2.z + v3.z;*/
		
	}
	
	public static void add(Vector dest, Vector v1, Vector v2, Vector v3, Vector v4)
	{
		int length = MathHelper.min(dest.getSize(), v1.getSize(), v2.getSize(), v3.getSize(), v4.getSize());
		
		for (int c = 0; c < length; c++)
		{
			dest.set(c, v1.get(c) + v2.get(c) + v3.get(c) + v4.get(c), c == (length - 1));
			
		}
		
		/*dest.x = v1.x + v2.x + v3.x + v4.x;
		dest.y = v1.y + v2.y + v3.y + v4.y;
		dest.z = v1.z + v2.z + v3.z + v4.z;*/
		
	}
	
	public static void mul(Vector dest, Vector v1, Vector v2)
	{
		v1.mul(v2, dest);
		
		/*dest.x = v1.x * v2.x;
		dest.y = v1.y * v2.y;
		dest.z = v1.z * v2.z;*/
		
	}
	
	public static void div(Vector dest, Vector v1, Vector v2)
	{
		v1.div(v2, dest);
		
		/*dest.x = v1.x / v2.x;
		dest.y = v1.y / v2.y;
		dest.z = v1.z / v2.z;*/
		
	}
	
	public static void setMin(Vector a, Vector b)
	{
		int length = Math.min(a.getSize(), b.getSize());
		
		for (int c = 0; c < length; c++)
		{
			a.set(c, Math.min(a.get(c), b.get(c)), c == (length - 1));
		}
		
		/*a.x = Math.min(a.x, b.x);
		a.y = Math.min(a.y, b.y);
		a.z = Math.min(a.z, b.z);*/
		
	}
	
	public static void setMax(Vector a, Vector b)
	{
		int length = Math.min(a.getSize(), b.getSize());
		
		for (int c = 0; c < length; c++)
		{
			a.set(c, Math.max(a.get(c), b.get(c)), c == (length - 1));
		}
		
		/*a.x = Math.max(a.x, b.x);
		a.y = Math.max(a.y, b.y);
		a.z = Math.max(a.z, b.z);*/
		
	}
	
	public static float dot3(Vector v0, Vector v1)
	{
		return dot(v0, v1);
	}
	
	public static float dot(Vector v0, Vector v1)
	{
		return MathHelper.dot(v0, v1);//(v0.x * v1.x + v0.y * v1.y + v0.z * v1.z);
	}
	
	public static float lengthSquared(Vector v)
	{
		return MathHelper.length(v);//(v.x * v.x + v.y * v.y + v.z * v.z);
	}
	
	public static void normalize3(Vector v)
	{
		v.normalize();
		/*float norm = (float)(1.0 / Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z));
		v.x *= norm;
		v.y *= norm;
		v.z *= norm;*/
		
	}
	
	public static void cross3(Vector dest, Vector v1, Vector v2)
	{
		v1.cross(v2, dest);
		/*float x, y;
		x = v1.y * v2.z - v1.z * v2.y;
		y = v2.x * v1.z - v2.z * v1.x;
		dest.z = v1.x * v2.y - v1.y * v2.x;
		dest.x = x;
		dest.y = y;*/
		
	}
	
}
