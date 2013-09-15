
package elusivehawk.engine.math;

import java.nio.IntBuffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Vector4i extends Vector3i
{
	public int w;
	
	public Vector4i()
	{
		this(0, 0, 0, 0);
		
	}
	
	public Vector4i(int a, int b, int c)
	{
		this(a, b, c, 0);
		
	}
	
	public Vector4i(int a, int b, int c, int d)
	{
		super(a, b, c);
		
		w = d;
		
	}
	
	public Vector4i(Vector4i vec)
	{
		super(vec);
		
		w = vec.w;
		
	}
	
	public Vector4i(IntBuffer buf)
	{
		super(buf);
		
		w = buf.get();
		
	}
	
	public Vector4i(Vector3i vec, int d)
	{
		this(vec.x, vec.y, vec.z, d);
		
	}
	
	public Vector4i add(Vector4i vec)
	{
		super.add(vec);
		
		this.w += vec.w;
		
		return this;
	}
	
	public Vector4i mul(Vector4i vec)
	{
		super.mul(vec);
		
		this.w *= vec.w;
		
		return this;
	}
	
	public Vector4i sub(Vector4i vec)
	{
		super.sub(vec);
		
		this.w -= vec.w;
		
		return this;
	}
	
	@Override
	public IntBuffer asBuffer()
	{
		IntBuffer ret = super.asBuffer();
		
		ret.put(this.w);
		
		return ret;
	}
	
	@Override
	public int getSize()
	{
		return 4;
	}
	
	@Override
	public int[] array()
	{
		return new int[]{this.x, this.y, this.z, this.w};
	}
	
}
