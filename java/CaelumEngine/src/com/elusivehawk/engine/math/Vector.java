
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
public class Vector implements IMathObject<Float>
{
	public static final int X = 0;
	public static final int Y = 1;
	public static final int Z = 2;
	public static final int W = 3;
	
	public static final Vector X_AXIS = new Vector(1f, 0f, 0f);
	public static final Vector Y_AXIS = new Vector(0f, 1f, 0f);
	public static final Vector Z_AXIS = new Vector(0f, 0f, 1f);
	
	protected final float[] nums;
	protected List<IVectorListener> listeners = null;
	protected String name = null;
	
	public Vector(int length, Buffer<Float> buf)
	{
		this(length);
		
		for (int c = 0; c < getSize(); c++)
		{
			set(c, buf.next());
			
		}
		
	}
	
	@SafeVarargs
	public Vector(Float... info)
	{
		this(info.length, new Buffer<Float>(info));
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Vector(Vector vec)
	{
		this(vec.getSize());
		
		for (int c = 0; c < nums.length; c++)
		{
			set(c, vec.get(c));
			
		}
		
		listeners = vec.listeners;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Vector(int length)
	{
		nums = new float[MathHelper.clamp(length, 1, 4)];
		
	}
	
	@Override
	public int getSize()
	{
		return this.nums.length;
	}
	
	@Override
	public Float get(int pos)
	{
		return this.nums[pos];
	}
	
	@Override
	public void set(int pos, Float num)
	{
		this.nums[pos] = num;
		
		this.onChanged();
		
	}
	
	@Override
	public Vector set(IMathObject<Float> obj)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < l; c++)
		{
			this.set(c, obj.get(c));
			
		}
		
		this.onChanged();
		
		return this;
	}
	
	@Override
	public Vector add(IMathObject<Float> obj, boolean local)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		Vector ret = local ? this : new Vector(this);
		
		for (int c = 0; c < l; c++)
		{
			ret.set(c, this.get(c) + obj.get(c));
			
		}
		
		ret.onChanged();
		
		return ret;
	}
	
	@Override
	public Vector div(IMathObject<Float> obj, boolean local)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		Vector ret = local ? this : new Vector(this);
		
		for (int c = 0; c < l; c++)
		{
			ret.set(c, this.get(c) / obj.get(c));
			
		}
		
		ret.onChanged();
		
		return ret;
	}
	
	@Override
	public Vector sub(IMathObject<Float> obj, boolean local)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		Vector ret = local ? this : new Vector(this);
		
		for (int c = 0; c < l; c++)
		{
			ret.set(c, this.get(c) - obj.get(c));
			
		}
		
		ret.onChanged();
		
		return ret;
	}
	
	@Override
	public Vector mul(IMathObject<Float> obj, boolean local)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		Vector ret = local ? this : new Vector(this);
		
		for (int c = 0; c < l; c++)
		{
			ret.set(c, this.get(c) * obj.get(c));
			
		}
		
		ret.onChanged();
		
		return ret;
	}
	
	@Override
	public void store(Buffer<Float> buf)
	{
		for (int c = 0; c < this.getSize(); c++)
		{
			buf.add(this.get(c));
			
		}
		
	}
	
	public void registerListener(IVectorListener veclis)
	{
		if (this.listeners == null)
		{
			this.listeners = SimpleList.newList();
			
		}
		
		this.listeners.add(veclis);
		
	}
	
	public void removeListener(IVectorListener veclis)
	{
		if (this.listeners == null || this.listeners.isEmpty())
		{
			return;
		}
		
		this.listeners.remove(veclis);
		
	}
	
	protected void onChanged()
	{
		if (this.listeners == null || this.listeners.isEmpty())
		{
			return;
		}
		
		for (IVectorListener lis : this.listeners)
		{
			lis.onVectorChanged(this);
			
		}
		
	}
	
	public Vector name(String str)
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
		
		Float num = null;
		
		for (int c = 0; c < 4; c++)
		{
			num = c < this.getSize() ? this.get(c) : null;
			
			b.append(num == null ? "0" : num.toString());
			if (c < 3) b.append(", ");
			
		}
		
		b.append("]");
		
		return b.toString();
	}
	
}
