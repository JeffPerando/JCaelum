
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
	@SuppressWarnings("unused")
	default boolean updateBeforeRender(RenderContext rcon, double delta)
	{
		return true;
	}
	
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
