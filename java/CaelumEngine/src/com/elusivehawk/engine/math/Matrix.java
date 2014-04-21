
package com.elusivehawk.engine.math;

import java.nio.FloatBuffer;
import com.elusivehawk.engine.util.BufferHelper;
import com.elusivehawk.engine.util.storage.Buffer;

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
		StringBuilder b = new StringBuilder(1 + ((this.h + 1) * ((this.w * 2) + 2)));
		
		b.append("/n");
		
		for (int y = 0; y < this.h; y++)
		{
			b.append("[");
			
			for (int x = 0; x < this.w; x++)
			{
				b.append(this.get(x, y));
				
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
	public boolean isImmutable()
	{
		return false;
	}
	
	@Override
	public Float[] multiget(int bitmask)
	{
		throw new UnsupportedOperationException("Matrices do not currently support multiget().");
	}
	
	@Override
	public void set(int pos, Float num)
	{
		this.set(pos, num, true);
		
	}
	
	@Override
	public void set(int pos, Float num, boolean notify)
	{
		this.data[pos] = num.floatValue();
		
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
	public void setAll(Float num)
	{
		for (int c = 0; c < this.getSize(); c++)
		{
			this.set(c, num);
			
		}
		
	}
	
	@Override
	public Float normalize()
	{
		throw new UnsupportedOperationException("Matrices do not normalize");
	}
	
	@Override
	public IMathObject<Float> add(IMathObject<Float> obj)
	{
		return this.add(obj, this);
	}
	
	@Override
	public IMathObject<Float> add(IMathObject<Float> obj, IMathObject<Float> dest)
	{
		Buffer<Float> buf = new Buffer<Float>(obj);
		
		for (int c = 0; c < this.getSize(); c++)
		{
			dest.set(c, this.get(c) + buf.next());
			
			if (!buf.hasNext())
			{
				buf.rewind();
				
			}
			
		}
		
		return dest;
	}
	
	@Override
	public IMathObject<Float> div(IMathObject<Float> obj)
	{
		return this.div(obj, this);
	}
	
	@Override
	public IMathObject<Float> div(IMathObject<Float> obj, IMathObject<Float> dest)
	{
		Buffer<Float> buf = new Buffer<Float>(obj);
		
		for (int c = 0; c < this.getSize(); c++)
		{
			dest.set(c, this.get(c) / buf.next());
			
			if (!buf.hasNext())
			{
				buf.rewind();
				
			}
			
		}
		
		return dest;
	}
	
	@Override
	public IMathObject<Float> sub(IMathObject<Float> obj)
	{
		return this.mul(obj, this);
	}
	
	@Override
	public IMathObject<Float> sub(IMathObject<Float> obj, IMathObject<Float> dest)
	{
		Buffer<Float> buf = new Buffer<Float>(obj);
		
		for (int c = 0; c < this.getSize(); c++)
		{
			dest.set(c, this.get(c) - buf.next());
			
			if (!buf.hasNext())
			{
				buf.rewind();
				
			}
			
		}
		
		return dest;
	}
	
	@Override
	public IMathObject<Float> mul(IMathObject<Float> obj)
	{
		return this.mul(obj, this);
	}
	
	@Override
	public IMathObject<Float> mul(IMathObject<Float> obj, IMathObject<Float> dest)
	{
		Buffer<Float> buf = new Buffer<Float>(obj);
		
		for (int c = 0; c < this.getSize(); c++)
		{
			dest.set(c, this.get(c) * buf.next());
			
			if (!buf.hasNext())
			{
				buf.rewind();
				
			}
			
		}
		
		return dest;
	}
	
	public float get(int x, int y)
	{
		return this.get(x + (y * this.h));
	}
	
	public void set(int x, int y, float f)
	{
		this.set(x, y, f, true);
		
	}
	
	public void set(int x, int y, float f, boolean notify)
	{
		this.data[x + (y * this.h)] = f;
		
		if (notify)
		{
			
		}
		
	}
	
	public void setRow(int r, float... fs)
	{
		int i = Math.min(this.h, fs.length);
		
		for (int c = 0; c < i; c++)
		{
			this.set(r, c, fs[c], false);
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void setRow(int r, Vector vec)
	{
		if (vec instanceof Vector3f)
		{
			Vector3f vec3f = (Vector3f)vec;
			
			this.set(r, 0, vec3f.x, false);
			this.set(r, 1, vec3f.y, false);
			this.set(r, 2, vec3f.z, false);
			
			return;
		}
		
		int i = Math.min(this.w, vec.getSize());
		
		for (int c = 0; c < i; c++)
		{
			this.set(r, c, vec.get(c), false);
			
		}
		
	}
	
	public void getRow(int r, Vector row)
	{
		int i = Math.min(this.w, row.getSize());
		
		for (int c = 0; c < i; c++)
		{
			row.set(c, this.get(r, c), false);
			
		}
		
		row.onChanged();
		
	}
	
}
