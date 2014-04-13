
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
	protected void onChanged()
	{
		this.z = this.get(Z);
		
		super.onChanged();
		
	}
	
	public void set(float a, float b, float c)
	{
		super.set(a, b);
		
		this.z = c;
		
	}
	
	public void sub(Vector3f vec)
	{
		this.sub(vec, true);
		
	}
	
}
