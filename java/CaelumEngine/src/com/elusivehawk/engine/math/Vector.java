
package com.elusivehawk.engine.math;

import java.util.List;
import com.elusivehawk.engine.util.Buffer;
import com.elusivehawk.engine.util.SimpleList;

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
	
	protected final Number[] nums;
	protected final List<IVectorListener<T>> listeners = SimpleList.newList();
	protected String name = null;
	
	protected Vector(int length, Buffer<T> buf)
	{
		this(length);
		
		for (int c = 0; c < getSize(); c++)
		{
			set(c, buf.next());
			
		}
		
	}
	
	@SafeVarargs
	protected Vector(T... info)
	{
		this(info.length, new Buffer<T>(info));
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	protected Vector(Vector<T> vec)
	{
		this(vec.getSize());
		
		for (int c = 0; c < nums.length; c++)
		{
			set(c, vec.get(c));
			
		}
		
		listeners.addAll(vec.listeners);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	protected Vector(int length)
	{
		nums = new Number[MathHelper.clamp(length, 1, 4)];
		
	}
	
	@Override
	public int getSize()
	{
		return this.nums.length;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T get(int pos)
	{
		return (T)this.nums[pos];
	}
	
	@Override
	public void set(int pos, T num)
	{
		this.nums[pos] = num;
		
	}
	
	@Override
	public Vector<T> set(IMathObject<T> obj)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < l; c++)
		{
			this.set(c, obj.get(c));
			
		}
		
		this.onChanged();
		
		return this;
	}
	
	public void registerListener(IVectorListener<T> veclis)
	{
		this.listeners.add(veclis);
		
	}
	
	public void removeListener(IVectorListener<T> veclis)
	{
		this.listeners.remove(veclis);
		
	}
	
	protected void onChanged()
	{
		if (this.listeners == null || this.listeners.isEmpty())
		{
			return;
		}
		
		for (IVectorListener<T> lis : this.listeners)
		{
			lis.onVectorChanged(this);
			
		}
		
	}
	
	public Vector<T> name(String str)
	{
		this.name = str;
		
		return this;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	@Override
	public String toString()
	{
		StringBuilder b = new StringBuilder(10);
		
		b.append(this.getName() == null ? "vector" : this.getName());
		b.append(":[");
		
		T num = null;
		
		for (int c = 0; c < 4; c++)
		{
			num = this.get(c);
			
			b.append(num == null ? "0" : num.toString());
			if (c < 3) b.append(", ");
			
		}
		
		b.append("]");
		
		return b.toString();
	}
	
}
