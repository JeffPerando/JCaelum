
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.core.IDirty;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ICamera extends IDirty
{
	public void updateCamera(IRenderHUB hub);
	
	public void postRender(IRenderHUB hub);
	
	public void updateUniform(GLProgram p);
	
	public float getFloat(EnumCameraPollType pollType);
	
	public boolean setFloat(EnumCameraPollType pollType, float f);
	
}
