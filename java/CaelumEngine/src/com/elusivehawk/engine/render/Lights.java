
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.IGLManipulator;
import com.elusivehawk.engine.util.ArrayHelper;

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
		return ArrayHelper.add(this.lights, l) ? l : null;
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
