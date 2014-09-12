
package com.elusivehawk.util.math;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Quaternion implements IMathObject<Float>
{
	protected final float[] data;
	
	public Quaternion()
	{
		this(4);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Quaternion(int size)
	{
		data = new float[MathHelper.clamp(size, 1, 4)];
		setAll(0f);
		
	}
	
	public Quaternion(Quaternion q)
	{
		this(q.length());
		set(q);
		
	}
	
	@Override
	public int length()
	{
		return this.data.length;
	}
	
	@Override
	public Float get(int pos)
	{
		return MathHelper.bounds(pos, 0, this.length()) ? this.data[pos] : 0f;
	}
	
	@Override
	public Float[] multiget(int bitmask)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void set(int pos, Float num, boolean notify)
	{
		if (MathHelper.bounds(pos, 0, this.length()))
		{
			this.data[pos] = num.floatValue();
			
		}
		
		if (notify)
		{
			this.onChanged();
			
		}
		
	}
	
	@Override
	public IMathObject<Float> normalize(IMathObject<Float> dest)
	{
		assert !dest.isImmutable();
		
		for (int c = 0; c < this.length(); c++)
		{
			dest.set(c, (float)Math.sqrt(MathHelper.square(this.get(c))), false);
			
		}
		
		return dest;
	}
	
	@Override
	public IMathObject<Float> add(IMathObject<Float> obj, IMathObject<Float> dest)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public IMathObject<Float> div(IMathObject<Float> obj, IMathObject<Float> dest)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public IMathObject<Float> sub(IMathObject<Float> obj, IMathObject<Float> dest)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public IMathObject<Float> mul(IMathObject<Float> obj, IMathObject<Float> dest)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}
