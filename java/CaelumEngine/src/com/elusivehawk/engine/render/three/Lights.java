
package com.elusivehawk.engine.render.three;

import com.elusivehawk.engine.render.RenderSystem;
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
	protected int size = 0;
	
	public Lights()
	{
		
	}
	
	public Light attachLight(Light l)
	{
		if (this.lights[this.size] != null)
		{
			return null;
		}
		
		this.lights[this.size++] = l;
		
		return l;
	}
	
	@Override
	public void updateUniforms(RenderSystem sys){}
	
	@Override
	public void manipulateUniforms(RenderSystem sys, GLProgram p)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void postRender()
	{
		// TODO Auto-generated method stub
		
	}
	
}
