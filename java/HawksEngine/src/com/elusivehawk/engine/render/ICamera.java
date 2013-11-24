
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.core.IDirty;
import com.elusivehawk.engine.math.IVector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ICamera<P extends IVector, R extends IVector> extends IDirty
{
	public void updateCamera(IRenderHUB hub);
	
	public void postRender(IRenderHUB hub);
	
	public void updateUniform(GLProgram p);
	
	public P getCamPos();
	
	public R getCamRot();
	
	public float getZFar();
	
	public float getZNear();
	
}
