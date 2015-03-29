
package com.elusivehawk.caelum.render;

import com.elusivehawk.util.math.MatrixF;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Camera implements IPreRenderer
{
	protected final MatrixF proj, view;
	
	@SuppressWarnings("unqualified-field-access")
	public Camera(int msize)
	{
		proj = (MatrixF)new MatrixF(msize).setSync();
		view = (MatrixF)new MatrixF(msize).setSync();
		
	}
	
	public MatrixF getProjection()
	{
		return this.proj;
	}
	
	public MatrixF getView()
	{
		return this.view;
	}
	
}
