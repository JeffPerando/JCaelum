
package com.elusivehawk.engine.math;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Vector2f implements IVector
{
	public float x, y;
	protected boolean dirty = false, readOnly = false;
	
	public Vector2f()
	{
		this(0, 0);
		
	}
	
	public Vector2f(float a, float b)
	{
		x = a;
		y = b;
		
	}
	
	public Vector2f(Vector2f vec)
	{
		this(vec.x, vec.y);
		
	}
	
	public Vector2f(FloatBuffer buf)
	{
		x = buf.get();
		y = buf.get();
		
	}
	
	public Vector2f add(Vector2f vec)
	{
		return this.add(vec.x, vec.y);
	}
	
	public Vector2f add(float x, float y)
	{
		return this.set(this.x + x, this.y + y);
	}
	
	public Vector2f div(Vector2f vec)
	{
		return this.div(vec.x, vec.y);
	}
	
	public Vector2f div(float x, float y)
	{
		return this.set(this.x / x, this.y / y);
	}
	
	public Vector2f mul(Vector2f vec)
	{
		return this.mul(vec.x, vec.y);
	}
	
	public Vector2f mul(float f)
	{
		return this.mul(f, f);
	}
	
	public Vector2f mul(float x, float y)
	{
		return this.set(this.x * x, this.y * y);
	}
	
	public Vector2f set(Vector2f vec)
	{
		return this.set(vec.x, vec.y);
	}
	
	public Vector2f set(float x, float y)
	{
		if (!this.isReadOnly())
		{
			this.x = x;
			this.y = y;
			
			this.setIsDirty(true);
			
		}
		
		return this;
	}
	
	public Vector2f sub(Vector2f vec)
	{
		return this.sub(vec.x, vec.y);
	}
	
	public Vector2f sub(float x, float y)
	{
		return this.set(this.x - x, this.y - y);
	}
	
	@Override
	public boolean isDirty()
	{
		return this.dirty;
	}
	
	@Override
	public boolean isReadOnly()
	{
		return this.readOnly;
	}
	
	public Vector2f markReadOnly()
	{
		if (!this.isDirty())
		{
			this.readOnly = true;
			
		}
		
		return this;
	}
	
	public Vector2f setIsDirty(boolean b)
	{
		this.dirty = b;
		
		return this;
	}
	
	@Override
	public boolean store(ByteBuffer buf)
	{
		return false;
	}
	
	@Override
	public boolean store(FloatBuffer buf)
	{
		buf.put(this.x);
		buf.put(this.y);
		
		return true;
	}
	
	@Override
	public boolean store(IntBuffer buf)
	{
		return false;
	}
	
	@Override
	public FloatBuffer asBuffer()
	{
		FloatBuffer ret = BufferUtils.createFloatBuffer(this.getSize());
		
		ret.put(this.x).put(this.y);
		
		return ret;
	}
	
	@Override
	public int getSize()
	{
		return 2;
	}
	
	@Override
	public float[] array()
	{
		return new float[]{this.x, this.y};
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof IVector))
		{
			return false;
		}
		
		IVector vec = (IVector)obj;
		
		if (vec.getSize() != this.getSize())
		{
			return false;
		}
		
		float[] first = this.array();
		float[] sec = vec.array();
		
		for (int c = 0; c < sec.length; c++)
		{
			if (first[c] != sec[c])
			{
				return false;
			}
			
		}
		
		return true;
	}
	
	@Override
	public Vector2f clone()
	{
		return new Vector2f(this);
	}
	
}
