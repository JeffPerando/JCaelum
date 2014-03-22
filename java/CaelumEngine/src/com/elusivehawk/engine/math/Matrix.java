
package com.elusivehawk.engine.math;

import java.nio.FloatBuffer;
import com.elusivehawk.engine.util.Buffer;
import com.elusivehawk.engine.util.BufferHelper;

/**
 * 
 * Because LWJGL goofed theirs up!
 * 
 * @author Elusivehawk
 */
public class Matrix implements IMathObject<Float>
{
	protected final float[] data;
	public final int w, h;
	
	public Matrix(int size)
	{
		this((int)Math.sqrt(size), (int)Math.sqrt(size));
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Matrix(int x, int y)
	{
		data = new float[x * y];
		w = x;
		h = y;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Matrix(float[] info)
	{
		this(info.length);
		
		for (int c = 0; c < info.length; c++)
		{
			data[c] = info[c];
			
		}
		
	}
	
	public Matrix(Buffer<Float> buf, int x, int y)
	{
		this(x, y);
		
		for (int c = 0; c < buf.remaining(); c++)
		{
			set(c, buf.next());
			
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
		FloatBuffer ret = BufferHelper.createFloatBuffer(this.w * this.h);
		
		this.load(ret);
		
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
			buf.add(this.get(c));
			
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
	public void set(int pos, Float num)
	{
		this.data[pos] = num;
		
	}
	
	@Override
	public Matrix set(IMathObject<Float> obj)
	{
		Buffer<Float> buf = new Buffer<Float>(obj);
		
		for (int c = 0; c < this.getSize(); c++)
		{
			this.set(c, buf.next());
			
			if (!buf.hasNext())
			{
				buf.rewind();
				
			}
			
		}
		
		return this;
	}
	
	@Override
	public Matrix add(IMathObject<Float> obj,  boolean local)
	{
		Buffer<Float> buf = new Buffer<Float>(obj);
		Matrix ret = local ? this : new Matrix(this);
		
		for (int c = 0; c < this.getSize(); c++)
		{
			ret.set(c, this.get(c) + buf.next());
			
			if (!buf.hasNext())
			{
				buf.rewind();
				
			}
			
		}
		
		return ret;
	}
	
	@Override
	public Matrix div(IMathObject<Float> obj,  boolean local)
	{
		Buffer<Float> buf = new Buffer<Float>(obj);
		Matrix ret = local ? this : new Matrix(this);
		
		for (int c = 0; c < this.getSize(); c++)
		{
			ret.set(c, this.get(c) / buf.next());
			
			if (!buf.hasNext())
			{
				buf.rewind();
				
			}
			
		}
		
		return ret;
	}
	
	@Override
	public Matrix sub(IMathObject<Float> obj,  boolean local)
	{
		Buffer<Float> buf = new Buffer<Float>(obj);
		Matrix ret = local ? this : new Matrix(this);
		
		for (int c = 0; c < this.getSize(); c++)
		{
			ret.set(c, this.get(c) - buf.next());
			
			if (!buf.hasNext())
			{
				buf.rewind();
				
			}
			
		}
		
		return ret;
	}
	
	@Override
	public Matrix mul(IMathObject<Float> obj,  boolean local)
	{
		Buffer<Float> buf = new Buffer<Float>(obj);
		Matrix ret = local ? this : new Matrix(this);
		
		for (int c = 0; c < this.getSize(); c++)
		{
			ret.set(c, this.get(c) * buf.next());
			
			if (!buf.hasNext())
			{
				buf.rewind();
				
			}
			
		}
		
		return ret;
	}
	
}
