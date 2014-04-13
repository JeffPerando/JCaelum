
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
	protected void onChanged()
	{
		this.w = this.get(W);
		
		super.onChanged();
		
	}
	
	public void set(float a, float b, float c, float d)
	{
		super.set(a, b, c);
		
		this.w = d;
		
	}
	
	public void sub(Vector4f vec)
	{
		this.sub(vec, true);
		
	}
	
}
