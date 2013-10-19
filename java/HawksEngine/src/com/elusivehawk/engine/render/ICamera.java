
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.core.EnumRenderMode;
import com.elusivehawk.engine.core.IDirty;
import com.elusivehawk.engine.math.Vector3f;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ICamera extends IDirty
{
	public void updateCamera();
	
	public EnumRenderMode getRenderMode();
	
	public Vector3f getCamRot();
	
	public Vector3f getCamPos();
	
	public float getZFar();
	
	public float getZNear();
	
}
