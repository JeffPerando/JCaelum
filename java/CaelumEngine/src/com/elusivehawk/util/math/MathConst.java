
package com.elusivehawk.util.math;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class MathConst
{
	public static final int X = 0, A = 0;
	public static final int Y = 1, B = 1;
	public static final int Z = 2, C = 2;
	public static final int W = 3, D = 3;
	
	public static final int X_BITMASK = 0b0001;
	public static final int Y_BITMASK = 0b0010;
	public static final int Z_BITMASK = 0b0100;
	public static final int W_BITMASK = 0b1000;
	
	public static final int[] BITMASKS = {X_BITMASK, Y_BITMASK, Z_BITMASK, W_BITMASK};
	
	public static final int XY =	X_BITMASK | Y_BITMASK;
	public static final int XZ =	X_BITMASK | Z_BITMASK;
	public static final int XYZ =	X_BITMASK | Y_BITMASK | Z_BITMASK;
	public static final int XYZW =	X_BITMASK | Y_BITMASK | Z_BITMASK | W_BITMASK;
	
	public static final Vector X_AXIS = new Vector(1f, 0f, 0f);
	public static final Vector Y_AXIS = new Vector(0f, 1f, 0f);
	public static final Vector Z_AXIS = new Vector(0f, 0f, 1f);
	
	public static final Vector[] AXES = new Vector[]{X_AXIS, Y_AXIS, Z_AXIS};
	
	private MathConst(){}
	
}
