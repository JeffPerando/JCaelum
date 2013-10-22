
package com.elusivehawk.engine.render;


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
