
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
public class Vector2f extends Vector
{
	public float x = 0f, y = 0f;
	
	public Vector2f()
	{
		this(2);
		
	}
	
	public Vector2f(int size)
	{
		super(size);
		
	}
	
	public Vector2f(float a, float b)
	{
		this();
		
		set(a, b);
		
	}
	
	public Vector2f(Vector2f vec)
	{
		super(vec);
		
	}
	
	@Override
	public void onChanged()
	{
		this.x = this.get(X);
		this.y = this.get(Y);
		
		super.onChanged();
		
	}
	
	public void scale(float f)
	{
		this.scale(f, this);
		
	}
	
	public void set(float a, float b)
	{
		this.x = a;
		this.y = b;
		
	}
	
	public void sub(Vector2f vec)
	{
		this.sub(vec, this);
		
	}
	
}
