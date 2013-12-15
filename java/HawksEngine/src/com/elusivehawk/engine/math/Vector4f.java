
package com.elusivehawk.engine.math;

import java.nio.FloatBuffer;

/**
 * 
 * @deprecated Same as its {@linkplain Vector3f superclass}.
 * 
 * @author Elusivehawk
 */
@Deprecated
public class Vector4f extends Vector3f
{
	public float w;
	
	public Vector4f()
	{
		this(0, 0, 0, 0);
		
	}
	
	public Vector4f(float a, float b, float c)
	{
		this(a, b, c, 0);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Vector4f(float a, float b, float c, float d)
	{
		super(a, b, c);
		
		w = d;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Vector4f(Vector4f vec)
	{
		super(vec);
		
		w = vec.w;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Vector4f(FloatBuffer buf)
	{
		super(buf);
		
		w = buf.get();
		
	}
	
	public Vector4f(Vector3f vec, float d)
	{
		this(vec.x, vec.y, vec.z, d);
		
	}

	public Vector4f add(Vector4f vec)
	{
		return this.add(vec.x, vec.y, vec.z, vec.w);
	}
	
	public Vector4f add(float x, float y, float z, float w)
	{
		return this.set(this.x + x, this.y + y, this.z + z, this.w + w);
	}
	
	public Vector4f div(Vector4f vec)
	{
		return this.div(vec.x, vec.y, vec.z, vec.w);
	}
	
	public Vector4f div(float x, float y, float z, float w)
	{
		return this.set(this.x / x, this.y / y, this.z / z, this.w / w);
	}
	
	public Vector4f mul(Vector4f vec)
	{
		return this.mul(vec.x, vec.y, vec.z, vec.w);
	}
	
	@Override
	public Vector4f mul(float f)
	{
		return this.mul(f, f, f, f);
	}
	
	public Vector4f mul(float x, float y, float z, float w)
	{
		return this.set(this.x * x, this.y * y, this.z * z, this.w / w);
	}
	
	public Vector4f set(Vector4f vec)
	{
		return this.set(vec.x, vec.y, vec.z, vec.w);
	}
	
	public Vector4f set(float x, float y, float z, float w)
	{
		super.set(x, y, z);
		
		if (!this.isReadOnly())
		{
			this.w = w;
			
		}
		
		return this;
	}
	
	public Vector4f sub(Vector4f vec)
	{
		return this.sub(vec.x, vec.y, vec.z, vec.w);
	}
	
	public Vector4f sub(float x, float y, float z, float w)
	{
		return this.set(this.x - x, this.y - y, this.z - z, this.w - w);
	}
	
	@Override
	public boolean store(FloatBuffer buf)
	{
		super.store(buf);
		
		buf.put(this.w);
		
		return true;
	}
	
	@Override
	public FloatBuffer asBuffer()
	{
		FloatBuffer ret = super.asBuffer();
		
		ret.put(this.w);
		
		return ret;
	}
	
	@Override
	public int getSize()
	{
		return 4;
	}
	
	@Override
	public float[] array()
	{
		return new float[]{this.x, this.y, this.z, this.w};
	}
	
	@Override
	public Vector4f clone()
	{
		return new Vector4f(this);
	}
	
}
