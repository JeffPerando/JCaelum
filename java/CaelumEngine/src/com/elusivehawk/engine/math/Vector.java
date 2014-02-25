
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
	
	public static final Vector<Float> X_AXIS = new VectorF(1f, 0f, 0f);
	public static final Vector<Float> Y_AXIS = new VectorF(0f, 1f, 0f);
	public static final Vector<Float> Z_AXIS = new VectorF(0f, 0f, 1f);
	
	protected final int size;
	
	@SuppressWarnings("unqualified-field-access")
	protected Vector(int length)
	{
		size = MathHelper.clamp(length, 1, 4);
		
	}
	
	@Override
	public int getSize()
	{
		return this.size;
	}

	@Override
	public void copy(IMathObject<T> obj)
	{
		for (int c = 0; c < Math.min(obj.getSize(), this.getSize()); c++)
		{
			this.set(c, obj.get(c));
			
		}
		
	}
	
}
