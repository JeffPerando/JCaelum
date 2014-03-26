
package com.elusivehawk.engine.math;

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
		float ax = one.get(Vector.X);
		float ay = one.get(Vector.Y);
		float az = one.get(Vector.Z);
		
		float bx = two.get(Vector.X);
		float by = two.get(Vector.Y);
		float bz = two.get(Vector.Z);
		
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
		return length(to.get(Vector.X) - from.get(Vector.X), to.get(Vector.Y) - from.get(Vector.Y), to.get(Vector.Z) - from.get(Vector.Z));
	}
	
	public static float dot(Vector one, Vector two)
	{
		return (one.get(Vector.X) * two.get(Vector.X)) + (one.get(Vector.Y) * two.get(Vector.Y)) + (one.get(Vector.Z) * two.get(Vector.Z));
	}
	
	public static float interpolate(float one, float two, float factor)
	{
		assert bounds(factor, 0.0f, 1.0f);
		
		return ((two * factor) + ((1f - factor) * one));
	}
	
	public static Vector interpolate(Vector one, Vector two, float factor)
	{
		Vector ret = new Vector(Math.min(one.getSize(), two.getSize()));
		
		for (int c = 0; c < ret.getSize(); c++)
		{
			ret.set(c, interpolate(one.get(c), two.get(c), factor));
			
		}
		
		return ret;
	}
	
	public static boolean isOdd(int i)
	{
		return (i & 1) == 1;
	}
	
	public static float length(Vector vec)
	{
		return length(vec.get(Vector.X), vec.get(Vector.Y), vec.get(Vector.Z));
	}
	
	public static float length(float x, float y, float z)
	{
		return square(x) + square(y) + square(z);
	}
	
	public static int percent(int i, int max)
	{
		return (int)(((float) i / max) * 100);
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
	
	public static int toInt(boolean b)
	{
		return b ? 1 : 0;
	}
	
	public static float toRadians(float degree)
	{
		return (degree * PI) / 180;
	}
	
	public static Vector toRadians(Vector vec)
	{
		Vector ret = new Vector(vec);
		
		ret.set(Vector.X, toRadians(ret.get(Vector.X)));
		ret.set(Vector.Y, toRadians(ret.get(Vector.Y)));
		ret.set(Vector.Z, toRadians(ret.get(Vector.Z)));
		
		return ret;
	}
	
}
