
package elusivehawk.engine.math;

import java.nio.IntBuffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Vector3i extends Vector2i
{
	public int z;
	
	public Vector3i()
	{
		this(0, 0, 0);
		
	}
	
	public Vector3i(int a, int b)
	{
		this(a, b, 0);
		
	}
	
	public Vector3i(int a, int b, int c)
	{
		super(a, b);
		
		z = c;
		
	}
	
	public Vector3i(Vector3i vec)
	{
		super(vec);
		
		z = vec.z;
		
	}
	
	public Vector3i(IntBuffer buf)
	{
		super(buf);
		
		z = buf.get();
		
	}
	
	public Vector3i(Vector2i vec, int c)
	{
		this(vec.x, vec.y, c);
		
	}
	
	public Vector3i add(Vector3i vec)
	{
		super.add(vec);
		
		this.z += vec.z;
		
		return this;
	}
	
	public Vector3i mul(Vector3i vec)
	{
		super.mul(vec);
		
		this.z *= vec.z;
		
		return this;
	}
	
	public Vector3i sub(Vector3i vec)
	{
		super.sub(vec);
		
		this.z -= vec.z;
		
		return this;
	}
	
	@Override
	public IntBuffer asBuffer()
	{
		IntBuffer ret = super.asBuffer();
		
		ret.put(this.z);
		
		return ret;
	}
	
	@Override
	public int getSize()
	{
		return 3;
	}
	
	@Override
	public int[] array()
	{
		return new int[]{this.x, this.y, this.z};
	}
	
}
