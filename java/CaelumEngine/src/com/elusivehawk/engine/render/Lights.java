
package com.elusivehawk.engine.render;

import java.util.List;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.IGLManipulator;
import com.elusivehawk.engine.util.SimpleList;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Lights implements IGLManipulator
{
	public static final int LIGHT_CAP = 1024;
	
	protected final List<Light> lights = SimpleList.newList(LIGHT_CAP, false);
	
	public Lights()
	{
		
	}
	
	public Light attachLight(Light l)
	{
		if (!this.lights.add(l))
		{
			return null;
		}
		
		return l;
	}
	
	@Override
	public void manipulateUniforms(GLProgram p)
	{
		// TODO Auto-generated method stub
		
	}
	
}
