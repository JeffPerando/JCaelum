
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
	
	@Override
	protected void onChanged()
	{
		this.x = this.get(X);
		this.y = this.get(Y);
		
		super.onChanged();
		
	}
	
}
