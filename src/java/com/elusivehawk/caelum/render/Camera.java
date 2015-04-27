
package com.elusivehawk.caelum.render;

import com.elusivehawk.util.math.MatrixF;
import com.elusivehawk.util.math.VectorF;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Camera implements IPreRenderer
{
	protected final VectorF pos;
	protected final MatrixF proj, view;
	
	@SuppressWarnings("unqualified-field-access")
	protected Camera(int psize, int msize)
	{
		pos = (VectorF)new VectorF(psize).setSync();
		proj = (MatrixF)new MatrixF(msize).setSync();
		view = (MatrixF)new MatrixF(msize).setSync();
		
	}
	
	public VectorF getPosition()
	{
		return this.pos;
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
