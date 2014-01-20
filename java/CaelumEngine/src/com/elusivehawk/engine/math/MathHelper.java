
package com.elusivehawk.engine.math;

/**
 * 
 * Convenience (aka helper) class for math functions.
 * 
 * @author Elusivehawk
 */
public final class MathHelper
{
	public static final float PI = 3.14159f;
	
	private MathHelper(){}
	
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
		return i == 0 ? false : (i >> 1) >= i;
	}
	
	public static int percent(int i, int max)
	{
		return (int)(((float) i / max) * 100);
	}
	
	public static float toDegrees(float radian)
	{
		return ((radian * 180) / PI);
	}
	
	public static int toInt(boolean b)
	{
		return b ? 1 : 0;
	}
	
	public static float toRadians(float degree)
	{
		return ((degree * PI) / 180);
	}
	
}
