
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
	
	@Override
	protected void onChanged()
	{
		this.w = this.get(W);
		
		super.onChanged();
		
	}
	
}
