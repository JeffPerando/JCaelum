
package elusivehawk.engine.math;

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
public class Vector2f extends Vectorf
{
	public float x, y;
	
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
		this.x += vec.x;
		this.y += vec.y;
		
		return this;
	}
	
	public Vector2f div(Vector2f vec)
	{
		this.x /= vec.x;
		this.y /= vec.y;
		
		return this;
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
		this.x *= x;
		this.y *= y;
		
		return this;
	}
	
	public Vector2f sub(Vector2f vec)
	{
		this.x -= vec.x;
		this.y -= vec.y;
		
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
	
}
