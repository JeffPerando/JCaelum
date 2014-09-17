
package com.elusivehawk.util.math;

import java.util.List;
import com.elusivehawk.util.storage.Buffer;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Vector implements IMathArray<Float>
{
	protected final float[] data;
	
	protected List<IVecListener> listeners = null;
	protected String name = null;
	protected volatile boolean
			dirty = false,
			immutable = false,
			sync = false;
	
	public Vector()
	{
		this(3);
		
	}
	
	public Vector(int length, Buffer<Float> buf)
	{
		this(length);
		
		for (int c = 0; c < size(); c++)
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
		this(vec.size());
		
		for (int c = 0; c < data.length; c++)
		{
			set(c, vec.get(c));
			
		}
		
		setIsDirty(false);
		
		listeners = vec.listeners;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Vector(int length)
	{
		data = new float[Math.max(length, 1)];
		
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
	public int size()
	{
		return this.data.length;
	}
	
	@Override
	public Float get(int pos)
	{
		return this.data[pos];
	}
	
	@Override
	public Vector set(int pos, Number num, boolean notify)
	{
		assert !this.isImmutable();
		
		if (this.data[pos] != num.floatValue())
		{
			if (!this.isDirty())
			{
				this.setIsDirty(true);
				
			}
			
			if (this.sync)
			{
				synchronized (this)
				{
					this.data[pos] = num.floatValue();
					
				}
				
			}
			else
			{
				this.data[pos] = num.floatValue();
				
			}
			
			if (notify)
			{
				this.onChanged();
				
			}
			
		}
		
		return this;
	}
	
	@Override
	public IMathArray<Float> normalize(IMathArray<Float> dest)
	{
		assert !dest.isImmutable();
		
		float f = MathHelper.length(this);
		
		int length = Math.min(this.size(), dest.size());
		
		for (int c = 0; c < length; c++)
		{
			dest.set(c, dest.get(c) / f, false);
			
		}
		
		dest.onChanged();
		
		return dest;
	}
	
	@Override
	public IMathArray<Float> add(IMathArray<Float> obj, IMathArray<Float> dest)
	{
		assert !dest.isImmutable();
		
		int l = Math.min(this.size(), obj.size());
		
		for (int c = 0; c < l; c++)
		{
			dest.set(c, this.get(c) + obj.get(c), false);
			
		}
		
		dest.onChanged();
		
		return dest;
	}
	
	@Override
	public IMathArray<Float> div(IMathArray<Float> obj, IMathArray<Float> dest)
	{
		assert !dest.isImmutable();
		
		int l = Math.min(this.size(), obj.size());
		
		for (int c = 0; c < l; c++)
		{
			dest.set(c, this.get(c) / obj.get(c), false);
			
		}
		
		dest.onChanged();
		
		return dest;
	}
	
	@Override
	public IMathArray<Float> sub(IMathArray<Float> obj, IMathArray<Float> dest)
	{
		assert !dest.isImmutable();
		
		int l = Math.min(this.size(), obj.size());
		
		for (int c = 0; c < l; c++)
		{
			dest.set(c, this.get(c) - obj.get(c), false);
			
		}
		
		dest.onChanged();
		
		return dest;
	}
	
	@Override
	public IMathArray<Float> mul(IMathArray<Float> obj, IMathArray<Float> dest)
	{
		assert !dest.isImmutable();
		
		int l = Math.min(this.size(), obj.size());
		
		for (int c = 0; c < l; c++)
		{
			dest.set(c, this.get(c) * obj.get(c), false);
			
		}
		
		dest.onChanged();
		
		return dest;
	}
	
	@Override
	public void onChanged()
	{
		if (this.listeners != null)
		{
			this.listeners.forEach(((lis) -> {lis.onVecChanged(this);}));
			
		}
		
	}
	
	@Override
	public Vector clone()
	{
		return new Vector(this);
	}
	
	@Override
	public String toString()
	{
		StringBuilder b = new StringBuilder(1 + (this.size() * 2));
		
		b.append(String.format("%s:[", this.getName() == null ? "vector" : this.getName()));
		
		for (int c = 0; c < this.size(); c++)
		{
			if (c > 0)
			{
				b.append(", ");
				
			}
			
			b.append(this.get(c));
			
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
		
		if (vec.size() != this.size())
		{
			return false;
		}
		
		for (int c = 0; c < this.size(); c++)
		{
			if (!vec.get(c).equals(this.get(c)))
			{
				return false;
			}
			
		}
		
		return true;
	}
	
	@Override
	public Vector setImmutable()
	{
		this.immutable = true;
		
		return this;
	}
	
	@Override
	public boolean isImmutable()
	{
		return this.immutable;
	}
	
	public void addListener(IVecListener lis)
	{
		assert lis != null;
		
		if (this.listeners == null)
		{
			this.listeners = Lists.newArrayList();
			
		}
		
		this.listeners.add(lis);
		
	}
	
	public void removeListener(IVecListener lis)
	{
		if (this.listeners != null)
		{
			this.listeners.remove(lis);
			
		}
		
	}
	
	public Vector setName(String str)
	{
		this.name = str;
		
		return this;
	}
	
	public Vector setSync()
	{
		this.sync = true;
		
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
	
	public Vector scale(float f, Vector dest)
	{
		for (int c = 0; c < this.size(); c++)
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
		int i = Math.min(this.size(), dest.size());
		
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
	
	public Vector negate(boolean local)
	{
		return this.negate(local ? this : new Vector(this.size()));
	}
	
	public Vector negate(Vector dest)
	{
		int length = Math.min(this.size(), dest.size());
		
		for (int c = 0; c < length; c++)
		{
			dest.set(c, -this.get(c), false);
			
		}
		
		dest.onChanged();
		
		return dest;
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
		for (int c = 0; c < this.size(); c++)
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
		for (int c = 0; c < this.size(); c++)
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
		for (int c = 0; c < this.size(); c++)
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
		for (int c = 0; c < this.size(); c++)
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
		int length = Math.min(this.size(), fs.length);
		
		for (int c = 0; c < length; c++)
		{
			this.set(c, fs[c], false);
			
		}
		
		this.onChanged();
		
		return this;
	}
	
	public Vector add(IMathArray<Float> obj, boolean local)
	{
		return (Vector)this.add(obj, local ? this : new Vector(this.size()));
	}
	
	public Vector add(int pos, float f)
	{
		return this.add(pos, f, true);
	}
	
	public Vector add(int pos, float f, boolean notify)
	{
		this.set(pos, this.data[pos] + f, notify);
		
		return this;
	}
	
	public Vector div(IMathArray<Float> obj, boolean local)
	{
		return (Vector)this.div(obj, local ? this : new Vector(this.size()));
	}
	
	public Vector div(int pos, float f)
	{
		return this.div(pos, f, true);
	}
	
	public Vector div(int pos, float f, boolean notify)
	{
		this.set(pos, this.data[pos] / f, notify);
		
		return this;
	}
	
	public Vector mul(IMathArray<Float> obj, boolean local)
	{
		return (Vector)this.mul(obj, local ? this : new Vector(this.size()));
	}
	
	public Vector mul(int pos, float f)
	{
		return this.mul(pos, f, true);
	}
	
	public Vector mul(int pos, float f, boolean notify)
	{
		this.set(pos, this.data[pos] * f, notify);
		
		return this;
	}
	
	public Vector sub(IMathArray<Float> obj, boolean local)
	{
		return (Vector)this.sub(obj, local ? this : new Vector(this.size()));
	}
	
	public Vector sub(int pos, float f)
	{
		return this.sub(pos, f, true);
	}
	
	public Vector sub(int pos, float f, boolean notify)
	{
		this.set(pos, this.data[pos] - f, notify);
		
		return this;
	}
	
	public Vector scale(float f)
	{
		return this.mulAll(f);
	}
	
}
