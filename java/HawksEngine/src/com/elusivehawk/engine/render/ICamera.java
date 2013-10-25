
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.core.IDirty;
import com.elusivehawk.engine.math.IVector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ICamera extends IDirty
{
	public void updateCamera(IRenderHUB hub);
	
	public IVector getCamRot();
	
	public IVector getCamPos();
	
	public float getZFar();
	
	public float getZNear();
	
}
