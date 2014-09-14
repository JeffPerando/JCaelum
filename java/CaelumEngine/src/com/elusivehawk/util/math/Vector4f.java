
package com.elusivehawk.util.math;

/**
 * 
 * Compatibility class for porting over math libraries.
 * <p>
 * NOTICE: Always deprecated; Refactor your code to use more flexible {@link Vector}s!
 * 
 * @author Elusivehawk
 */
@Deprecated
public class Vector4f extends Vector3f
{
	public float w = 0f;
	
	public Vector4f()
	{
		super(4);
		
	}
	
	public Vector4f(float a, float b, float c, float d)
	{
		this();
		
		set(a, b, c, d);
		
	}
	
	public Vector4f(Vector4f vec)
	{
		super(vec);
		
	}
	
	@Override
	public Float get(int pos)
	{
		if (pos == 3)
		{
			return this.w;
		}
		
		return super.get(pos);
	}
	
	@Override
	public Vector4f set(int pos, Float f)
	{
		if (pos == 3)
		{
			this.w = f.floatValue();
			
		}
		else
		{
			super.set(pos, f);
			
		}
		
		return this;
	}
	
	public void set(float a, float b, float c, float d)
	{
		super.set(a, b, c);
		
		this.w = d;
		
	}
	
	public void sub(Vector4f vec)
	{
		this.sub(vec, this);
		
	}
	
}
