
package com.elusivehawk.engine.math;

/**
 * 
 * Compatibility class for porting over math libraries.
 * <p>
 * NOTICE: Always deprecated; Refactor your code to use more flexible {@link Vector}s!
 * 
 * @author Elusivehawk
 */
@Deprecated
public class Vector3f extends Vector2f
{
	public float z = 0f;
	
	public Vector3f()
	{
		this(3);
		
	}
	
	public Vector3f(int size)
	{
		super(size);
		
	}
	
	public Vector3f(float a, float b, float c)
	{
		this();
		
		set(a, b, c);
		
	}
	
	public Vector3f(Vector3f vec)
	{
		super(vec);
		
	}
	
	@Override
	public Float get(int pos)
	{
		if (pos == 2)
		{
			return this.z;
		}
		
		return super.get(pos);
	}
	
	@Override
	public void set(int pos, Float f)
	{
		if (pos == 2)
		{
			this.z = f.floatValue();
			
		}
		else
		{
			super.set(pos, f);
			
		}
		
	}
	
	public void set(float a, float b, float c)
	{
		super.set(a, b);
		
		this.z = c;
		
	}
	
	public void sub(Vector3f vec)
	{
		this.sub(vec, this);
		
	}
	
	public float lengthSquared()
	{
		return MathHelper.length(this);
	}
	
	public void interpolate(Vector3f one, Vector3f two, float p)
	{
		this.set(MathHelper.interpolate(one, two, p));
		
	}
	
	public void normalize(Vector v)//FIXME
	{
		int length = Math.min(this.getSize(), v.getSize());
		
		for (int c = 0; c < length; c++)
		{
			v.set(c, (float)Math.sqrt(MathHelper.square(this.get(c))), c == (length - 1));
			
		}
		
	}
	
}
