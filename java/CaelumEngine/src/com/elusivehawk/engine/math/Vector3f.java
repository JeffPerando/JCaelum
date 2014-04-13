
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
	
	@Override
	protected void onChanged()
	{
		this.z = this.get(Z);
		
		super.onChanged();
		
	}
	
}
