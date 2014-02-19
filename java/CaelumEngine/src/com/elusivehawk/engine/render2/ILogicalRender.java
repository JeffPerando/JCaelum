
package com.elusivehawk.engine.render2;

import com.elusivehawk.engine.render.opengl.GLProgram;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ILogicalRender
{
	public boolean updateBeforeUse(IRenderHUB hub);
	
	public GLProgram getProgram();
	
}
