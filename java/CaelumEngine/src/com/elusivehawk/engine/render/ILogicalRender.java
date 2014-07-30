
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
	public boolean updateBeforeUse(RenderSystem sys);
	
	public GLProgram getProgram();
	
}
