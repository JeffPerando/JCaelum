
package com.elusivehawk.engine.render;

import org.lwjgl.input.Mouse;
import com.elusivehawk.engine.math.Vector3f;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Camera implements ICamera
{
	private Vector3f angle = new Vector3f();
	private Vector3f pos = new Vector3f();
	
	@Override
	public void updateCamera()
	{
		if (Mouse.isCreated() && Mouse.isInsideWindow())
		{
			
			
		}
		
	}
	
	@Override
	public Vector3f getCamRot()
	{
		return angle;
	}
	
	@Override
	public Vector3f getCamPos()
	{
		return pos;
	}
	
	@Override
	public float getZFar()
	{
		return 0;
	}
	
	@Override
	public float getZNear()
	{
		return 0;
	}
	
}
