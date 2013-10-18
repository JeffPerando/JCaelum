
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.math.Vector3f;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ICamera
{
	public void updateCamera();
	
	public Vector3f getCamRot();
	
	public Vector3f getCamPos();
	
	public float getZFar();
	
	public float getZNear();
	
}
