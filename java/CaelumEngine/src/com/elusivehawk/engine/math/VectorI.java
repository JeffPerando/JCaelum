
package com.elusivehawk.engine.math;

import java.nio.IntBuffer;
import com.elusivehawk.engine.util.Buffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class VectorI extends Vector<Integer>
{
	protected final Integer[] data;
	
	public VectorI(int length, Buffer<Integer> buf)
	{
		this(length);
		
		if (buf == null || buf.remaining() == 0)
		{
			return;
		}
		
		int l = Math.min(length, buf.remaining());
		
		for (int c = 0; c < l; c++)
		{
			this.set(c, buf.next());
			
		}
		
	}
	
	public VectorI(int length, Integer... info)
	{
		this(length, new Buffer<Integer>(info));
		
	}
	
	public VectorI(int length, IntBuffer buf)
	{
		this(length);
		
		int l = Math.min(length, buf.remaining());
		
		for (int c = 0; c < l; c++)
		{
			this.set(c, buf.get());
			
		}
		
	}
	
	public VectorI(int length, Vector<Integer> vec)
	{
		this(length);
		
		for (int c = 0; c < length; c++)
		{
			this.set(c, vec.get(c));
			
		}
		
	}
	
	public VectorI(Vector<Integer> vec)
	{
		this(vec.getSize(), vec);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public VectorI(int length)
	{
		super(length);
		data = new Integer[length];
		
	}
	
	@Override
	public Integer get(int pos)
	{
		return pos < this.data.length && pos > 0 ? this.data[pos] : 0;
	}
	
	@Override
	public void set(int pos, Integer num)
	{
		this.data[pos] = num;
		
	}
	
	@Override
	public VectorI add(IMathObject<Integer> obj)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < l; c++)
		{
			this.set(c, this.get(c) + obj.get(c));
			
		}
		
		return this;
	}
	
	@Override
	public VectorI div(IMathObject<Integer> obj)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < l; c++)
		{
			this.set(c, this.get(c) / obj.get(c));
			
		}
		
		return this;
	}
	
	@Override
	public VectorI set(IMathObject<Integer> obj)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < l; c++)
		{
			this.set(c, obj.get(c));
			
		}
		
		return this;
	}
	
	@Override
	public VectorI sub(IMathObject<Integer> obj)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < l; c++)
		{
			this.set(c, this.get(c) - obj.get(c));
			
		}
		
		return this;
	}
	
	@Override
	public VectorI mul(IMathObject<Integer> obj)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		
		for (int c = 0; c < l; c++)
		{
			this.set(c, this.get(c) * obj.get(c));
			
		}
		
		return this;
	}
	
	@Override
	public void store(Buffer<Integer> buf)
	{
		for (int c = 0; c < this.getSize(); c++)
		{
			buf.add(this.get(c));
			
		}
		
	}
	
}
