
package com.elusivehawk.engine.math;

/**
 * 
 * Convenience class for math functions.
 * 
 * @author Elusivehawk
 */
public final class MathHelper
{
	public static final float PI = 3.14159f;
	
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
	
	public static float dist(Vector<Float> from, Vector<Float> to)
	{
		return (float)Math.sqrt(distSquare(from, to));
	}
	
	public static float distSquare(Vector<Float> from, Vector<Float> to)
	{
		float x = to.get(Vector.X) - from.get(Vector.X),
		y = to.get(Vector.Y) - from.get(Vector.Y),
		z = to.get(Vector.Z) - from.get(Vector.Z);
		
		return (x * x) + (y * y) + (z * z);
	}
	
	public static boolean isOdd(int i)
	{
		return (i & 1) == 1;
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
	
	public static Vector<Float> toRadians(Vector<Float> vec)
	{
		Vector<Float> ret = new VectorF(vec);
		
		ret.set(Vector.X, toRadians(ret.get(Vector.X)));
		ret.set(Vector.Y, toRadians(ret.get(Vector.Y)));
		ret.set(Vector.Z, toRadians(ret.get(Vector.Z)));
		
		return ret;
	}
	
}
