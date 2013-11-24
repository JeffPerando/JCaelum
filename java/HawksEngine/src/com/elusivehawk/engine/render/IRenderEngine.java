
package com.elusivehawk.engine.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IRenderEngine
{
	public void render(IRenderHUB hub);
	
	public int getPriority();
	
}
