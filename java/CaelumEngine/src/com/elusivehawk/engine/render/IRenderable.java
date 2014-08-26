
package com.elusivehawk.engine.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IRenderable
{
	/**
	 * 
	 * @param rcon
	 * @param delta
	 * 
	 * @throws RenderException
	 * 
	 * @see RenderHelper
	 */
	public void render(RenderContext rcon, double delta) throws RenderException;
	
}
