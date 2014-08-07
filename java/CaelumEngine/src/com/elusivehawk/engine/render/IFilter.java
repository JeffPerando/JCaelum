
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.IGLManipulator;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IFilter extends IGLManipulator
{
	public void filter(RenderContext rcon, GLProgram p);
	
	@Override
	default void updateUniforms(RenderContext rcon){}
	
	@Override
	default void manipulateUniforms(RenderContext rcon, GLProgram p)
	{
		this.filter(rcon, p);
	}
	
	@Override
	default void postRender(){};
	
	
}
