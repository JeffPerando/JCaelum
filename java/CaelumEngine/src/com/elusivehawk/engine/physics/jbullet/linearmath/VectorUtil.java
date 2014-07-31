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

import com.elusivehawk.util.math.MathHelper;
import com.elusivehawk.util.math.Vector;

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
	
	/*public static float getCoord(Vector vec, int num)
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
	
	public static void setCoord(Vector vec, int num, float value)
	{
		switch (num)
		{
			case 0:
				vec.set(X, value;
				break;
			case 1:
				vec.set(Y, value;
				break;
			case 2:
				vec.get(Z)= value;
				break;
			default:
				throw new InternalError();
		}
		
	}
	
	public static void mulCoord(Vector vec, int num, float value)
	{
		switch (num)
		{
			case 0:
				vec.get(X)*= value;
				break;
			case 1:
				vec.get(Y)*= value;
				break;
			case 2:
				vec.get(Z)*= value;
				break;
			default:
				throw new InternalError();
		}
		
	}*/
	
	public static void setInterpolate(Vector dest, Vector v0, Vector v1, float rt)
	{
		float s = 1f - rt;
		
		for (int c = 0; c < 3; c++)
		{
			dest.set(c, s * v0.get(c) + rt * v1.get(c), c == 2);
			
		}
		/*dest.set(X, s * v0.get(X)+ rt * v1.x;
		dest.set(Y, s * v0.get(Y)+ rt * v1.y;
		dest.get(Z)= s * v0.get(Z)+ rt * v1.z;*/
		// don't do the unused w component
		// m_co[3] = s * v0[3] + rt * v1[3];
		
	}
	
	public static void add(Vector dest, Vector v1, Vector v2)
	{
		v1.mul(v2, dest);
		
		/*dest.set(X, v1.get(X)+ v2.x;
		dest.set(Y, v1.get(Y)+ v2.y;
		dest.get(Z)= v1.get(Z)+ v2.z;*/
		
	}
	
	public static void add(Vector dest, Vector v1, Vector v2, Vector v3)
	{
		int length = MathHelper.min(dest.getSize(), v1.getSize(), v2.getSize(), v3.getSize());
		
		for (int c = 0; c < length; c++)
		{
			dest.set(c, v1.get(c) + v2.get(c) + v3.get(c), c == (length - 1));
			
		}
		
		/*dest.set(X, v1.get(X)+ v2.get(X)+ v3.x;
		dest.set(Y, v1.get(Y)+ v2.get(Y)+ v3.y;
		dest.get(Z)= v1.get(Z)+ v2.get(Z)+ v3.z;*/
		
	}
	
	public static void add(Vector dest, Vector v1, Vector v2, Vector v3, Vector v4)
	{
		int length = MathHelper.min(dest.getSize(), v1.getSize(), v2.getSize(), v3.getSize(), v4.getSize());
		
		for (int c = 0; c < length; c++)
		{
			dest.set(c, v1.get(c) + v2.get(c) + v3.get(c) + v4.get(c), c == (length - 1));
			
		}
		
		/*dest.set(X, v1.get(X)+ v2.get(X)+ v3.get(X)+ v4.x;
		dest.set(Y, v1.get(Y)+ v2.get(Y)+ v3.get(Y)+ v4.y;
		dest.get(Z)= v1.get(Z)+ v2.get(Z)+ v3.get(Z)+ v4.z;*/
		
	}
	
	public static void mul(Vector dest, Vector v1, Vector v2)
	{
		v1.mul(v2, dest);
		
		/*dest.set(X, v1.get(X)* v2.x;
		dest.set(Y, v1.get(Y)* v2.y;
		dest.get(Z)= v1.get(Z)* v2.z;*/
		
	}
	
	public static void div(Vector dest, Vector v1, Vector v2)
	{
		v1.div(v2, dest);
		
		/*dest.set(X, v1.get(X)/ v2.x;
		dest.set(Y, v1.get(Y)/ v2.y;
		dest.get(Z)= v1.get(Z)/ v2.z;*/
		
	}
	
	public static void setMin(Vector a, Vector b)
	{
		int length = Math.min(a.getSize(), b.getSize());
		
		for (int c = 0; c < length; c++)
		{
			a.set(c, Math.min(a.get(c), b.get(c)), c == (length - 1));
		}
		
		/*a.set(X, Math.min(a.x, b.x);
		a.set(Y, Math.min(a.y, b.y);
		a.get(Z)= Math.min(a.z, b.z);*/
		
	}
	
	public static void setMax(Vector a, Vector b)
	{
		int length = Math.min(a.getSize(), b.getSize());
		
		for (int c = 0; c < length; c++)
		{
			a.set(c, Math.max(a.get(c), b.get(c)), c == (length - 1));
		}
		
		/*a.set(X, Math.max(a.x, b.x);
		a.set(Y, Math.max(a.y, b.y);
		a.get(Z)= Math.max(a.z, b.z);*/
		
	}
	
	public static float dot3(Vector v0, Vector v1)
	{
		return dot(v0, v1);
	}
	
	public static float dot(Vector v0, Vector v1)
	{
		return MathHelper.dot(v0, v1);//(v0.get(X)* v1.get(X)+ v0.get(Y)* v1.get(Y)+ v0.get(Z)* v1.z);
	}
	
	public static float lengthSquared(Vector v)
	{
		return MathHelper.lengthSquared(v);//(v.get(X)* v.get(X)+ v.get(Y)* v.get(Y)+ v.get(Z)* v.z);
	}
	
	public static void normalize3(Vector v)
	{
		v.normalize();
		/*float norm = (float)(1.0 / Math.sqrt(v.get(X)* v.get(X)+ v.get(Y)* v.get(Y)+ v.get(Z)* v.z));
		v.get(X)*= norm;
		v.get(Y)*= norm;
		v.get(Z)*= norm;*/
		
	}
	
	public static void cross3(Vector dest, Vector v1, Vector v2)
	{
		v1.cross(v2, dest);
		/*float x, y;
		x = v1.get(Y)* v2.get(Z)- v1.get(Z)* v2.y;
		y = v2.get(X)* v1.get(Z)- v2.get(Z)* v1.x;
		dest.get(Z)= v1.get(X)* v2.get(Y)- v1.get(Y)* v2.x;
		dest.set(X, x;
		dest.set(Y, y;*/
		
	}
	
}
