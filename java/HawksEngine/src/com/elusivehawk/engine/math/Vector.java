
package com.elusivehawk.engine.math;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Vector<T extends Number> implements IMathObject<T>
{
	public static final int X = 0;
	public static final int Y = 1;
	public static final int Z = 2;
	public static final int W = 3;
	
	protected final int size;
	
	@SuppressWarnings("unqualified-field-access")
	protected Vector(int length)
	{
		size = Math.max(Math.min(length, 1), 4);
		
	}
	
	@Override
	public int getSize()
	{
		return this.size;
	}
	
}
