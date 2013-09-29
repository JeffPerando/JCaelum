
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
		return this.add(vec.x, vec.y, vec.z);
	}
	
	public Vector3f add(float x, float y, float z)
	{
		return this.set(this.x + x, this.y + y, this.z + z);
	}
	
	public Vector3f cross(Vector3f vec)
	{
		return new Vector3f(this.y * vec.z - vec.y * this.z, this.z * vec.x - vec.z * this.x, this.x * vec.y - vec.x * this.y);
	}
	
	public Vector3f div(Vector3f vec)
	{
		return this.div(vec.x, vec.y, vec.z);
	}
	
	public Vector3f div(float x, float y, float z)
	{
		return this.set(this.x / x, this.y / y, this.z / z);
	}
	
	public Vector3f mul(Vector3f vec)
	{
		return this.mul(vec.x, vec.y, vec.z);
	}
	
	@Override
	public Vector3f mul(float f)
	{
		return this.mul(f, f, f);
	}
	
	public Vector3f mul(float x, float y, float z)
	{
		return this.set(this.x * x, this.y * y, this.z * z);
	}
	
	public Vector3f set(Vector3f vec)
	{
		return this.set(vec.x, vec.y, vec.z);
	}
	
	public Vector3f set(float x, float y, float z)
	{
		super.set(x, y);
		
		this.z = z;
		
		return this;
	}
	
	public Vector3f sub(Vector3f vec)
	{
		return this.sub(vec.x, vec.y, vec.z);
	}
	
	public Vector3f sub(float x, float y, float z)
	{
		return this.set(this.x - x, this.y - y, this.z - z);
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
