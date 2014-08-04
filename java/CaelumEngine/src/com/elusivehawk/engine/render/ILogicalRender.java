
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.opengl.GLProgram;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ILogicalRender
{
	public boolean updateBeforeRender(RenderContext con, double delta);
	
	public GLProgram getProgram();
	
}
