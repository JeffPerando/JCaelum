
package com.elusivehawk.engine.math;

import com.elusivehawk.engine.util.storage.Buffer;

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
		this(q.getSize());
		set(q);
		
	}
	
	@Override
	public void store(Buffer<Float> buf)
	{
		for (int c = 0; c < this.getSize(); c++)
		{
			buf.add(this.get(c));
			
		}
		
	}
	
	@Override
	public int getSize()
	{
		return this.data.length;
	}
	
	@Override
	public boolean isImmutable()
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Float get(int pos)
	{
		return MathHelper.bounds(pos, 0, this.getSize()) ? this.data[pos] : 0f;
	}
	
	@Override
	public Float[] multiget(int bitmask)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void set(int pos, Float num)
	{
		this.set(pos, num, false);
		
	}
	
	@Override
	public void set(int pos, Float num, boolean notify)
	{
		if (MathHelper.bounds(pos, 0, this.getSize()))
		{
			this.data[pos] = num.floatValue();
			
		}
		
	}
	
	@Override
	public void setAll(Float num)
	{
		this.setAll(num, true);
		
	}
	
	@Override
	public void setAll(Float num, boolean notify)
	{
		for (int c = 0; c < this.getSize(); c++)
		{
			this.set(c, num, false);
			
		}
		
	}
	
	@Override
	public void normalize()
	{
		this.normalize(this);
		
	}
	
	@Override
	public void normalize(IMathObject<Float> dest)
	{
		assert !dest.isImmutable();
		
		for (int c = 0; c < this.getSize(); c++)
		{
			dest.set(c, (float)Math.sqrt(MathHelper.square(this.get(c))), false);
			
		}
		
	}
	
	@Override
	public IMathObject<Float> set(IMathObject<Float> obj)
	{
		int length = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < length; c++)
		{
			this.data[c] = obj.get(c);
			
		}
		
		return this;
	}
	
	@Override
	public IMathObject<Float> add(IMathObject<Float> obj)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public IMathObject<Float> add(IMathObject<Float> obj, IMathObject<Float> dest)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public IMathObject<Float> div(IMathObject<Float> obj)
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
	public IMathObject<Float> sub(IMathObject<Float> obj)
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
	
	@Override
	public IMathObject<Float> mul(IMathObject<Float> obj)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onChanged(){}
	
}
