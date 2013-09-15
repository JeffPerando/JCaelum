
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
public class Vector2i extends Vectori
{
	public int x, y;
	
	public Vector2i()
	{
		this(0, 0);
		
	}
	
	public Vector2i(int a, int b)
	{
		x = a;
		y = b;
		
	}
	
	public Vector2i(Vector2i vec)
	{
		this(vec.x, vec.y);
		
	}
	
	public Vector2i(IntBuffer buf)
	{
		x = buf.get();
		y = buf.get();
		
	}
	
	public Vector2i add(Vector2i vec)
	{
		this.x += vec.x;
		this.y += vec.y;
		
		return this;
	}
	
	public Vector2i mul(Vector2i vec)
	{
		this.x *= vec.x;
		this.y *= vec.y;
		
		return this;
	}
	
	public Vector2i sub(Vector2i vec)
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
		return false;
	}
	
	@Override
	public boolean store(IntBuffer buf)
	{
		buf.put(this.asBuffer());
		
		return true;
	}
	
	@Override
	public int getSize()
	{
		return 2;
	}
	
	@Override
	public IntBuffer asBuffer()
	{
		IntBuffer ret = BufferUtils.createIntBuffer(this.getSize());
		
		ret.put(this.x).put(this.y);
		
		return ret;
	}
	
	@Override
	public int[] array()
	{
		return new int[]{this.x, this.y};
	}
	
}
