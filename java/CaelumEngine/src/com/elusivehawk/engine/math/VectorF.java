
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
	
	public VectorF(Buffer<Float> buf)
	{
		this(buf.remaining());
		
		for (int c = 0; c < this.size; c++)
		{
			this.set(c, buf.next());
			
		}
		
	}
	
	public VectorF(Float... info)
	{
		this(new Buffer<Float>(info));
		
	}
	
	public VectorF(FloatBuffer buf)
	{
		this(buf.remaining());
		
		for (int c = 0; c < this.size; c++)
		{
			this.set(c, buf.get());
			
		}
		
	}
	
	public VectorF(int length, Vector<Float> vec)
	{
		this(length);
		
		for (int c = 0; c < length; c++)
		{
			this.set(c, vec.get(c));
			
		}
		
	}
	
	public VectorF(Vector<Float> vec)
	{
		this(vec.getSize(), vec);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	protected VectorF(int length)
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
	public void set(int pos, Float num)
	{
		this.data[pos] = num;
		
	}
	
	@Override
	public VectorF add(IMathObject<Float> obj)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < l; c++)
		{
			this.set(c, this.get(c) + obj.get(c));
			
		}
		
		return this;
	}
	
	@Override
	public VectorF div(IMathObject<Float> obj)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < l; c++)
		{
			this.set(c, this.get(c) / obj.get(c));
			
		}
		
		return this;
	}
	
	@Override
	public VectorF set(IMathObject<Float> obj)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < l; c++)
		{
			this.set(c, obj.get(c));
			
		}
		
		return this;
	}
	
	@Override
	public VectorF sub(IMathObject<Float> obj)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < l; c++)
		{
			this.set(c, this.get(c) - obj.get(c));
			
		}
		
		return this;
	}
	
	@Override
	public VectorF mul(IMathObject<Float> obj)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < l; c++)
		{
			this.set(c, this.get(c) * obj.get(c));
			
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
