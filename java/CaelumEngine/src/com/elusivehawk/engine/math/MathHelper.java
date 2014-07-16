
package com.elusivehawk.engine.math;

import com.elusivehawk.util.RNG;

/**
 * 
 * Convenience class for math functions.
 * 
 * @author Elusivehawk
 */
public final class MathHelper
{
	public static final float PI = 3.141592653589793238f;
	
	private MathHelper(){}
	
	public static boolean bounds(float f, float min, float max)
	{
		return clamp(f, min, max) == f;
	}
	
	public static boolean bounds(int i, int min, int max)
	{
		return clamp(i, min, max) == i;
	}
	
	public static boolean bounds(long l, long min, long max)
	{
		return clamp(l, min, max) == l;
	}
	
	public static float clamp(float f, float min, float max)
	{
		return Math.min(max, Math.max(f, min));
	}
	
	public static int clamp(int i, int min, int max)
	{
		return Math.min(max, Math.max(i, min));
	}
	
	public static long clamp(long l, long min, long max)
	{
		return Math.min(max, Math.max(l, min));
	}
	
	public static Vector cross(Vector one, Vector two)
	{
		float ax = one.get(MathConst.X);
		float ay = one.get(MathConst.Y);
		float az = one.get(MathConst.Z);
		
		float bx = two.get(MathConst.X);
		float by = two.get(MathConst.Y);
		float bz = two.get(MathConst.Z);
		
		return new Vector((ay * bz) - (az * by),
				(az * bx) - (ax * bz),
				(ax * by) - (ay * bx));
	}
	
	public static float dist(Vector from, Vector to)
	{
		return (float)Math.sqrt(distSquare(from, to));
	}
	
	public static float distSquare(Vector from, Vector to)
	{
		return lengthSquared(to.get(MathConst.X) - from.get(MathConst.X), to.get(MathConst.Y) - from.get(MathConst.Y), to.get(MathConst.Z) - from.get(MathConst.Z));
	}
	
	public static float dot(Vector one, Vector two)
	{
		return (one.get(MathConst.X) * two.get(MathConst.X)) + (one.get(MathConst.Y) * two.get(MathConst.Y)) + (one.get(MathConst.Z) * two.get(MathConst.Z));
	}
	
	public static float interpolate(float one, float two, float factor)
	{
		assert bounds(factor, 0.0f, 1.0f);
		
		return ((two * factor) + ((1f - factor) * one));
	}
	
	public static Vector interpolate(Vector one, Vector two, float factor)
	{
		return interpolate(one, two, factor, new Vector(Math.min(one.getSize(), two.getSize())));
	}
	
	public static Vector interpolate(Vector one, Vector two, float factor, Vector dest)
	{
		for (int c = 0; c < dest.getSize(); c++)
		{
			dest.set(c, interpolate(one.get(c), two.get(c), factor), false);
			
		}
		
		return dest;
	}
	
	public static boolean isOdd(int i)
	{
		return (i & 1) == 1;
	}
	
	public static float length(Vector vec)
	{
		return (float)Math.sqrt(lengthSquared(vec));
	}
	
	public static float lengthSquared(Vector vec)
	{
		return lengthSquared(vec.get(MathConst.X), vec.get(MathConst.Y), vec.get(MathConst.Z));
	}
	
	public static float lengthSquared(float x, float y, float z)
	{
		return square(x) + square(y) + square(z);
	}
	
	public static int percent(int i, int max)
	{
		return (int)(((float)i / max) * 100);
	}
	
	public static float pow(float f, int pow)
	{
		float ret = f;
		
		for (int c = 0; c < pow; c++)
		{
			ret *= f;
			
		}
		
		return ret;
	}
	
	public static int pow(int i, int pow)
	{
		int ret = i;
		
		for (int c = 0; c < pow; c++)
		{
			ret *= i;
			
		}
		
		return ret;
	}
	
	public static boolean rollDice(float weight)
	{
		return weight > RNG.instance().nextFloat();
	}
	
	public static float square(float f)
	{
		return f * f;
	}
	
	public static int square(int i)
	{
		return i * i;
	}
	
	public static float toDegrees(float radian)
	{
		return (radian * 180) / PI;
	}
	
	public static float toRadians(float degree)
	{
		return (degree * PI) / 180;
	}
	
	public static Vector toRadians(Vector vec)
	{
		Vector ret = new Vector(vec);
		
		ret.set(MathConst.X, toRadians(ret.get(MathConst.X)));
		ret.set(MathConst.Y, toRadians(ret.get(MathConst.Y)));
		ret.set(MathConst.Z, toRadians(ret.get(MathConst.Z)));
		
		return ret;
	}
	
	public static int min(int... is)
	{
		int min = Integer.MAX_VALUE;
		
		for (int i : is)
		{
			min = Math.min(min, i);
			
		}
		
		return min;
	}
	
}
