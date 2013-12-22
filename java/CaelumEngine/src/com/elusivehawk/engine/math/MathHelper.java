
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
