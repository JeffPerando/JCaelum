
package com.elusivehawk.engine.math;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import com.elusivehawk.engine.core.Buffer;

/**
 * 
 * Because LWJGL goofed theirs up!
 * 
 * @author Elusivehawk
 */
public class Matrix implements IMathObject<Float>
{
	protected final Float[] data;
	public final int w, h;
	
	public Matrix(int size)
	{
		this((int)Math.sqrt(size), (int)Math.sqrt(size));
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Matrix(int x, int y)
	{
		data = new Float[x * y];
		w = x;
		h = y;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Matrix(Float[] info)
	{
		this(info.length);
		
		for (int c = 0; c < info.length; c++)
		{
			data[c] = info[c];
			
		}
		
	}
	
	public Matrix(FloatBuffer buf, int x, int y)
	{
		this(x, y);
		
		load(buf);
		
	}
	
	public Matrix(Matrix m)
	{
		this(m.data);
		
	}
	
	public Matrix load(FloatBuffer buf)
	{
		int l = Math.min(this.getSize(), buf.remaining());
		
		for (int c = 0; c < l; c++)
		{
			this.data[c] = buf.get();
			
		}
		
		return this;
	}
	
	public FloatBuffer asBuffer()
	{
		FloatBuffer ret = BufferUtils.createFloatBuffer(this.w * this.h);
		
		for (int c = 0; c < this.getSize(); c++)
		{
			ret.put(this.get(c));
			
		}
		
		return ret;
	}
	
	@Override
	public String toString()
	{
		StringBuilder b = new StringBuilder("\n");
		
		for (int y = 0; y < this.h; y++)
		{
			b.append("[");
			
			for (int x = 0; x < this.w; x++)
			{
				b.append(this.data[x + (y * this.h)]);
				
				if (x != (this.w - 1))
				{
					b.append(", ");
					
				}
				
			}
			
			b.append("]");
			
			if (y != (this.h - 1))
			{
				b.append("\n");
				
			}
			
		}
		
		return b.toString();
	}
	
	@Override
	public void store(Buffer<Float> buf)
	{
		for (int c = 0; c < this.getSize(); c++)
		{
			buf.put(this.get(c));
			
		}
		
	}
	
	@Override
	public int getSize()
	{
		return this.w * this.h;
	}
	
	@Override
	public Float get(int pos)
	{
		return this.data[pos];
	}
	
	@Override
	public void set(Float num, int pos)
	{
		this.data[pos] = num;
		
	}
	
	@Override
	public Float[] array()
	{
		return this.data;
	}
	
	@Override
	public Matrix add(IMathObject<Float> obj)
	{
		Buffer<Float> buf = new Buffer<Float>(obj);
		
		for (int c = 0; c < this.getSize(); c++)
		{
			this.set(this.get(c) + buf.next(), c);
			
			if (!buf.hasNext())
			{
				buf.rewind();
				
			}
			
		}
		
		return this;
	}
	
	@Override
	public Matrix div(IMathObject<Float> obj)
	{
		Buffer<Float> buf = new Buffer<Float>(obj);
		
		for (int c = 0; c < this.getSize(); c++)
		{
			this.set(this.get(c) / buf.next(), c);
			
			if (!buf.hasNext())
			{
				buf.rewind();
				
			}
			
		}
		
		return this;
	}
	
	@Override
	public Matrix set(IMathObject<Float> obj)
	{
		Buffer<Float> buf = new Buffer<Float>(obj);
		
		for (int c = 0; c < this.getSize(); c++)
		{
			this.set(buf.next(), c);
			
			if (!buf.hasNext())
			{
				buf.rewind();
				
			}
			
		}
		
		return this;
	}
	
	@Override
	public Matrix sub(IMathObject<Float> obj)
	{
		Buffer<Float> buf = new Buffer<Float>(obj);
		
		for (int c = 0; c < this.getSize(); c++)
		{
			this.set(this.get(c) - buf.next(), c);
			
			if (!buf.hasNext())
			{
				buf.rewind();
				
			}
			
		}
		
		return this;
	}
	
	@Override
	public Matrix mul(IMathObject<Float> obj)
	{
		Buffer<Float> buf = new Buffer<Float>(obj);
		
		for (int c = 0; c < this.getSize(); c++)
		{
			this.set(this.get(c) * buf.next(), c);
			
			if (!buf.hasNext())
			{
				buf.rewind();
				
			}
			
		}
		
		return this;
	}
	
}
