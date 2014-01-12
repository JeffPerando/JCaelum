
package com.elusivehawk.engine.math;

import java.nio.FloatBuffer;
import com.elusivehawk.engine.util.Buffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class VectorF extends Vector<Float>
{
	protected final Float[] data;
	
	public VectorF(int length, Buffer<Float> buf)
	{
		this(length);
		
		if (buf == null || buf.remaining() == 0)
		{
			return;
		}
		
		int l = Math.min(length, buf.remaining());
		
		for (int c = 0; c < l; c++)
		{
			this.set(buf.next(), c);
			
		}
		
	}
	
	public VectorF(int length, Float... info)
	{
		this(length, new Buffer<Float>(info));
		
	}
	
	public VectorF(int length, FloatBuffer buf)
	{
		this(length);
		
		int l = Math.min(length, buf.remaining());
		
		for (int c = 0; c < l; c++)
		{
			this.set(buf.get(), c);
			
		}
		
	}
	
	public VectorF(int length, Vector<Float> vec)
	{
		this(length);
		
		for (int c = 0; c < length; c++)
		{
			this.set(vec.get(c), c);
			
		}
		
	}
	
	public VectorF(Vector<Float> vec)
	{
		this(vec.getSize(), vec);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public VectorF(int length)
	{
		super(length);
		data = new Float[length];
		
	}
	
	@Override
	public Float get(int pos)
	{
		return pos < this.data.length && pos > 0 ? this.data[pos] : 0f;
	}
	
	@Override
	public void set(Float num, int pos)
	{
		this.data[pos] = num;
		
	}
	
	@Override
	public VectorF add(IMathObject<Float> obj)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < l; c++)
		{
			this.set(this.get(c) + obj.get(c), c);
			
		}
		
		return this;
	}
	
	@Override
	public VectorF div(IMathObject<Float> obj)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < l; c++)
		{
			this.set(this.get(c) / obj.get(c), c);
			
		}
		
		return this;
	}
	
	@Override
	public VectorF set(IMathObject<Float> obj)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < l; c++)
		{
			this.set(obj.get(c), c);
			
		}
		
		return this;
	}
	
	@Override
	public VectorF sub(IMathObject<Float> obj)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < l; c++)
		{
			this.set(this.get(c) - obj.get(c), c);
			
		}
		
		return this;
	}
	
	@Override
	public VectorF mul(IMathObject<Float> obj)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < l; c++)
		{
			this.set(this.get(c) * obj.get(c), c);
			
		}
		
		return this;
	}
	
	@Override
	public void store(Buffer<Float> buf)
	{
		for (int c = 0; c < this.getSize(); c++)
		{
			buf.add(this.get(c));
			
		}
		
	}
	
}
