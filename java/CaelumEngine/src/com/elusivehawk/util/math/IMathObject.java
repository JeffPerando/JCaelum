
package com.elusivehawk.util.math;

import com.elusivehawk.util.storage.Buffer;
import com.elusivehawk.util.storage.IStorable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IMathObject<T extends Number> extends IStorable<T>
{
	@Override
	default void store(Buffer<T> buf)
	{
		for (int c = 0; c < this.getSize(); c++)
		{
			buf.add(this.get(c));
			
		}
		
	}
	
	public int getSize();
	
	default boolean isImmutable()
	{
		return false;
	}
	
	public T get(int pos);
	
	default Number[] multiget(int bitmask)
	{
		int count = 0;
		
		for (int bits : MathConst.BITMASKS)
		{
			if ((bitmask & bits) != 0)
			{
				count++;
				
			}
			
		}
		
		if (count == 0)
		{
			return new Float[0];
		}
		
		Number[] ret = new Number[count];
		count = 0;
		
		for (int c = 0; c < MathConst.BITMASKS.length; c++)
		{
			if ((bitmask & MathConst.BITMASKS[c]) != 0)
			{
				ret[count++] = this.get(c);
				
			}
			
		}
		
		return ret;
	}
	
	default void set(int pos, T num)
	{
		this.set(pos, num, true);
		
	}
	
	public void set(int pos, T num, boolean notify);
	
	default void setAll(T num)
	{
		for (int c = 0; c < this.getSize(); c++)
		{
			this.set(c, num, false);
			
		}
		
		this.onChanged();
		
	}
	
	default void normalize()
	{
		this.normalize(this);
		
	}
	
	public void normalize(IMathObject<T> dest);
	
	default IMathObject<T> set(IMathObject<T> obj)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < l; c++)
		{
			this.set(c, obj.get(c), false);
			
		}
		
		this.onChanged();
		
		return this;
	}
	
	default IMathObject<T> add(IMathObject<T> obj)
	{
		return this.add(obj, this);
	}
	
	public IMathObject<T> add(IMathObject<T> obj, IMathObject<T> dest);
	
	default IMathObject<T> div(IMathObject<T> obj)
	{
		return this.div(obj, this);
	}
	
	public IMathObject<T> div(IMathObject<T> obj, IMathObject<T> dest);
	
	default IMathObject<T> sub(IMathObject<T> obj)
	{
		return this.sub(obj, this);
	}
	
	public IMathObject<T> sub(IMathObject<T> obj, IMathObject<T> dest);
	
	default IMathObject<T> mul(IMathObject<T> obj)
	{
		return this.mul(obj, this);
	}
	
	public IMathObject<T> mul(IMathObject<T> obj, IMathObject<T> dest);
	
	default void onChanged(){}
	
}
