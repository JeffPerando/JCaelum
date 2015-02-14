
package com.elusivehawk.caelum.render;

import com.elusivehawk.util.math.Matrix;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Camera implements IPreRenderer
{
	protected final Matrix proj, view;
	
	@SuppressWarnings("unqualified-field-access")
	public Camera(int msize)
	{
		proj = (Matrix)new Matrix(msize, msize).setSync();
		view = (Matrix)new Matrix(msize, msize).setSync();
		
	}
	
	public Matrix getProjection()
	{
		return this.proj;
	}
	
	public Matrix getView()
	{
		return this.view;
	}
	
}
