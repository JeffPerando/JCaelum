
package com.elusivehawk.util.math;

import java.util.List;
import com.elusivehawk.util.IDirty;
import com.elusivehawk.util.storage.Buffer;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Vector implements IMathObject<Float>, IDirty
{
	protected final float[] nums;
	protected List<IVectorListener> listeners = null;
	protected String name = null;
	protected boolean dirty = false;
	
	public Vector()
	{
		this(3);
		
	}
	
	public Vector(int length, Buffer<Float> buf)
	{
		this(length);
		
		for (int c = 0; c < getSize(); c++)
		{
			set(c, buf.next());
			
		}
		
		setIsDirty(false);
		
	}
	
	public Vector(float... info)
	{
		this(info.length, info);
		
	}
	
	public Vector(int length, float... info)
	{
		this(length);
		
		int i = Math.min(length, info.length);
		
		for (int c = 0; c < i; c++)
		{
			set(c, info[c], false);
			
		}
		
		setIsDirty(false);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Vector(Vector vec)
	{
		this(vec.getSize());
		
		for (int c = 0; c < nums.length; c++)
		{
			set(c, vec.get(c));
			
		}
		
		setIsDirty(false);
		
		listeners = vec.listeners;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Vector(int length)
	{
		nums = new float[Math.max(length, 1)];
		
		setAll(0f);
		
		setIsDirty(false);
		
	}
	
	@Override
	public boolean isDirty()
	{
		return this.dirty;
	}
	
	@Override
	public void setIsDirty(boolean b)
	{
		this.dirty = b;
		
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
	public void set(int pos, Float num, boolean notify)
	{
		if (this.nums[pos] == num.floatValue())
		{
			return;
		}
		
		if (!this.isDirty())
		{
			this.setIsDirty(true);
			
		}
		
		this.nums[pos] = num.floatValue();
		
		if (notify)
		{
			this.onChanged();
			
		}
		
	}
	
	@Override
	public void normalize(IMathObject<Float> dest)
	{
		float f = MathHelper.length(this);
		
		int length = Math.min(this.getSize(), dest.getSize());
		
		for (int c = 0; c < length; c++)
		{
			dest.set(c, dest.get(c) / f, false);
			
		}
		
		dest.onChanged();
		
	}
	
	@Override
	public IMathObject<Float> add(IMathObject<Float> obj, IMathObject<Float> dest)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < l; c++)
		{
			dest.set(c, this.get(c) + obj.get(c), false);
			
		}
		
		this.onChanged();
		
		return dest;
	}
	
	@Override
	public IMathObject<Float> div(IMathObject<Float> obj, IMathObject<Float> dest)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < l; c++)
		{
			dest.set(c, this.get(c) / obj.get(c), false);
			
		}
		
		this.onChanged();
		
		return dest;
	}
	
	@Override
	public IMathObject<Float> sub(IMathObject<Float> obj, IMathObject<Float> dest)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < l; c++)
		{
			dest.set(c, this.get(c) - obj.get(c), false);
			
		}
		
		this.onChanged();
		
		return dest;
	}
	
	@Override
	public IMathObject<Float> mul(IMathObject<Float> obj, IMathObject<Float> dest)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < l; c++)
		{
			dest.set(c, this.get(c) * obj.get(c), false);
			
		}
		
		this.onChanged();
		
		return dest;
	}
	
	@Override
	public void onChanged()
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
	
	@Override
	public String toString()
	{
		StringBuilder b = new StringBuilder(1 + (this.getSize() * 2));
		
		b.append(this.getName() == null ? "vector" : this.getName());
		b.append(":[");
		
		for (int c = 0; c < this.getSize(); c++)
		{
			b.append(this.get(c));
			if (c < (this.getSize() - 1)) b.append(", ");
			
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
	
	public Vector setName(String str)
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
		this.cross(other, this);
		
		return this;
	}
	
	public void cross(Vector other, Vector dest)
	{
		dest.set(MathHelper.cross(this, other));
		
	}
	
	public float dot(Vector other)
	{
		return MathHelper.dot(this, other);
	}
	
	public float length()
	{
		return MathHelper.length(this);
	}
	
	public float lengthSquared()
	{
		return MathHelper.lengthSquared(this);
	}
	
	public Vector scale(float f, Vector dest)
	{
		for (int c = 0; c < this.getSize(); c++)
		{
			dest.set(c, this.get(c) * f, false);
			
		}
		
		this.onChanged();
		
		return dest;
	}
	
	public Vector absolute()
	{
		return this.absolute(this);
	}
	
	public Vector absolute(Vector dest)
	{
		int i = Math.min(this.getSize(), dest.getSize());
		
		for (int c = 0; c < i; c++)
		{
			dest.set(c, Math.abs(this.get(c)), false);
			
		}
		
		dest.onChanged();
		
		return dest;
	}
	
	public Vector negate()
	{
		return this.negate(this);
	}
	
	public Vector negate(Vector v)
	{
		int length = Math.min(this.getSize(), v.getSize());
		
		for (int c = 0; c < length; c++)
		{
			this.set(c, -v.get(c), false);
			
		}
		
		this.onChanged();
		
		return v;
	}
	
	@SuppressWarnings({"static-method", "unused"})
	public Vector scaleAdd(float f, Vector vec, Vector dest)//FIXME
	{
		return dest;
	}
	
	public Vector addAll(float f)
	{
		return this.addAll(f, true);
	}
	
	public Vector addAll(float f, boolean notify)
	{
		for (int c = 0; c < this.getSize(); c++)
		{
			this.add(c, f, false);
			
		}
		
		if (notify)
		{
			this.onChanged();
			
		}
		
		return this;
	}
	
	public Vector divAll(float f)
	{
		return this.divAll(f, true);
	}
	
	public Vector divAll(float f, boolean notify)
	{
		for (int c = 0; c < this.getSize(); c++)
		{
			this.div(c, f, false);
			
		}
		
		if (notify)
		{
			this.onChanged();
			
		}
		
		return this;
	}
	
	public Vector mulAll(float f)
	{
		return this.mulAll(f, true);
	}
	
	public Vector mulAll(float f, boolean notify)
	{
		for (int c = 0; c < this.getSize(); c++)
		{
			this.mul(c, f, false);
			
		}
		
		if (notify)
		{
			this.onChanged();
			
		}
		
		return this;
	}
	
	public Vector subAll(float f)
	{
		return this.subAll(f, true);
	}
	
	public Vector subAll(float f, boolean notify)
	{
		for (int c = 0; c < this.getSize(); c++)
		{
			this.sub(c, f, false);
			
		}
		
		if (notify)
		{
			this.onChanged();
			
		}
		
		return this;
	}
	
	public Vector set(float... fs)
	{
		int length = Math.min(this.getSize(), fs.length);
		
		for (int c = 0; c < length; c++)
		{
			this.set(c, fs[c], false);
			
		}
		
		this.onChanged();
		
		return this;
	}
	
	public Vector add(int pos, float f)
	{
		return this.add(pos, f, true);
	}
	
	public Vector add(int pos, float f, boolean notify)
	{
		this.set(pos, this.nums[pos] + f, notify);
		
		return this;
	}
	
	public Vector div(int pos, float f)
	{
		return this.div(pos, f, true);
	}
	
	public Vector div(int pos, float f, boolean notify)
	{
		this.set(pos, this.nums[pos] / f, notify);
		
		return this;
	}
	
	public Vector mul(int pos, float f)
	{
		return this.mul(pos, f, true);
	}
	
	public Vector mul(int pos, float f, boolean notify)
	{
		this.set(pos, this.nums[pos] * f, notify);
		
		return this;
	}
	
	public Vector sub(int pos, float f)
	{
		return this.sub(pos, f, true);
	}
	
	public Vector sub(int pos, float f, boolean notify)
	{
		this.set(pos, this.nums[pos] - f, notify);
		
		return this;
	}
	
	public Vector scale(float f)
	{
		return this.mulAll(f);
	}
	
}
