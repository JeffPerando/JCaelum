
package com.elusivehawk.engine.math;

import java.util.List;
import com.elusivehawk.engine.util.storage.Buffer;
import com.google.common.collect.Lists;

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
		
		for (int c = 0; c < getSize(); c++)
		{
			nums[c] = 0f;
			
		}
		
	}
	
	@Override
	public int getSize()
	{
		return this.nums.length;
	}
	
	@Override
	public Float get(int pos)
	{
		return MathHelper.bounds(pos, 0, this.getSize() - 1) ? this.nums[pos] : 0f;
	}
	
	@Override
	public Float[] multiget(int bitmask)
	{
		int count = 0;
		
		for (int bits : BITMASKS)
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
		
		count = 0;
		Float[] ret = new Float[count];
		
		for (int c = 0; c < BITMASKS.length; c++)
		{
			if ((bitmask & BITMASKS[c]) != 0)
			{
				ret[count++] = this.get(c);
				
			}
			
		}
		
		return ret;
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
	
	@Override
	public String toString()
	{
		StringBuilder b = new StringBuilder(10);
		
		b.append(this.getName() == null ? "vector" : this.getName());
		b.append(":[");
		
		for (int c = 0; c < 4; c++)
		{
			b.append(this.get(c));
			if (c < 3) b.append(", ");
			
		}
		
		b.append("]");
		
		return b.toString();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Vector))
		{
			return false;
		}
		
		Vector vec = (Vector)obj;
		
		if (vec.getSize() != this.getSize())
		{
			return false;
		}
		
		for (int c = 0; c < this.getSize(); c++)
		{
			if (!vec.get(c).equals(this.get(c)))
			{
				return false;
			}
			
		}
		
		return true;
	}
	
	public void registerListener(IVectorListener veclis)
	{
		if (this.listeners == null)
		{
			this.listeners = Lists.newArrayList();
			
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
	
	public Vector cross(Vector other)
	{
		return MathHelper.cross(this, other);
	}
	
	public float dot(Vector other)
	{
		return MathHelper.dot(this, other);
	}
	
	public float length()
	{
		return MathHelper.length(this);
	}
	
	public Vector scale(float f, boolean local)
	{
		Vector ret = local ? this : new Vector(this);
		
		for (int c = 0; c < this.getSize(); c++)
		{
			ret.set(c, ret.get(c) * f);
			
		}
		
		return ret;
	}
	
}
