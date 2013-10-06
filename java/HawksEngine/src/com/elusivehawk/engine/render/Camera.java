
package com.elusivehawk.engine.render;

import org.lwjgl.input.Mouse;
import com.elusivehawk.engine.math.Vector3f;

/**
 * 
 * To be replaced with an interface and default implementation.
 * 
 * @author Elusivehawk
 */
@Deprecated
public class Camera
{
	private static Vector3f angle = new Vector3f();
	private static Vector3f pos = new Vector3f();
	
	private Camera(){}
	
	public static void update()
	{
		if (Mouse.isCreated() && Mouse.isInsideWindow())
		{
			
		}
		
	}
	
	public static Vector3f getCameraRotation()
	{
		return angle;
	}
	
	public static Vector3f getCameraPosition()
	{
		return pos;
	}
	
}
