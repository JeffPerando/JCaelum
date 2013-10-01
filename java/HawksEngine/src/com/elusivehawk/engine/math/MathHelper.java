
package com.elusivehawk.engine.math;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class MathHelper
{
	public static final float PI = 3.14159f;
	
	private MathHelper(){}
	
	public static double avg(double a, double b)
	{
		double a0 = Math.min(a, b);
		double b0 = Math.max(a, b);
		
		return (a0 + ((b0 - a0) / 2));
	}
	
	public static float avg(float a, float b)
	{
		float a0 = Math.min(a, b);
		float b0 = Math.max(a, b);
		
		return (a0 + ((b0 - a0) / 2));
	}
	
	public static int avg(int a, int b)
	{
		int a0 = Math.min(a, b);
		int b0 = Math.max(a, b);
		
		return (int)(a0 + (float)((b0 - a0) / 2));
	}
	
	public static long avg(long a, long b)
	{
		long a0 = Math.min(a, b);
		long b0 = Math.max(a, b);
		
		return (long)(a0 + (float)((b0 - a0) / 2));
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
