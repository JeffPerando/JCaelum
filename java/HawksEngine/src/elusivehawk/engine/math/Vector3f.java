
package elusivehawk.engine.math;

import java.nio.FloatBuffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Vector3f extends Vector2f
{
	public float z;
	
	public Vector3f()
	{
		this(0, 0, 0);
		
	}
	
	public Vector3f(float a, float b)
	{
		this(a, b, 0);
		
	}
	
	public Vector3f(float a, float b, float c)
	{
		super(a, b);
		
		z = c;
		
	}
	
	public Vector3f(Vector3f vec)
	{
		super(vec);
		
		z = vec.z;
		
	}
	
	public Vector3f(FloatBuffer buf)
	{
		super(buf);
		
		z = buf.get();
		
	}
	
	public Vector3f(Vector2f vec, float c)
	{
		this(vec.x, vec.y, c);
		
	}
	
	public Vector3f add(Vector3f vec)
	{
		super.add(vec);
		
		this.z += vec.z;
		
		return this;
	}
	
	public Vector3f div(Vector3f vec)
	{
		super.div(vec);
		
		this.z /= vec.z;
		
		return this;
	}
	
	public Vector3f mul(Vector3f vec)
	{
		super.mul(vec);
		
		this.z *= vec.z;
		
		return this;
	}
	
	public Vector3f sub(Vector3f vec)
	{
		super.sub(vec);
		
		this.z -= vec.z;
		
		return this;
	}
	
	@Override
	public boolean store(FloatBuffer buf)
	{
		super.store(buf);
		
		buf.put(this.z);
		
		return true;
	}
	
	@Override
	public FloatBuffer asBuffer()
	{
		FloatBuffer ret = super.asBuffer();
		
		ret.put(this.z);
		
		return ret;
	}
	
	@Override
	public int getSize()
	{
		return 3;
	}
	
	@Override
	public float[] array()
	{
		return new float[]{this.x, this.y, this.z};
	}
	
}
