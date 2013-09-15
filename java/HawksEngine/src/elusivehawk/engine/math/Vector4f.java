
package elusivehawk.engine.math;

import java.nio.FloatBuffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
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
	
	public Vector4f(float a, float b, float c, float d)
	{
		super(a, b, c);
		
		w = d;
		
	}
	
	public Vector4f(Vector4f vec)
	{
		super(vec);
		
		w = vec.w;
		
	}
	
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
		super.add(vec);
		
		this.w += vec.w;
		
		return this;
	}
	
	public Vector4f div(Vector4f vec)
	{
		super.div(vec);
		
		this.w /= vec.w;
		
		return this;
	}
	
	public Vector4f mul(Vector4f vec)
	{
		super.mul(vec);
		
		this.w *= vec.w;
		
		return this;
	}
	
	public Vector4f sub(Vector4f vec)
	{
		super.sub(vec);
		
		this.w -= vec.w;
		
		return this;
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
	
}
