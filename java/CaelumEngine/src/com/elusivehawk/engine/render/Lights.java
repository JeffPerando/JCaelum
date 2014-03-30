
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.IGLManipulator;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Lights implements IGLManipulator
{
	public static final int LIGHT_CAP = 1024;
	
	protected final Light[] lights = new Light[LIGHT_CAP];
	
	public Lights()
	{
		
	}
	
	public Light attachLight(Light l)
	{
		for (int c = 0; c < LIGHT_CAP; c++)
		{
			if (this.lights[c] == null)
			{
				this.lights[c] = l;
				
				return l;
			}
			
		}
		
		return null;
	}
	
	@Override
	public boolean isModeValid(EnumRenderMode mode)
	{
		return mode.is3D();
	}
	
	@Override
	public void updateUniforms(RenderContext context){}
	
	@Override
	public void manipulateUniforms(RenderContext context, GLProgram p)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postRender()
	{
		// TODO Auto-generated method stub
		
	}
	
}
