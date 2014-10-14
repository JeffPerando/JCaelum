
package com.elusivehawk.engine.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IRenderable extends IPreRenderer, IPostRenderer
{
	@Override
	default void postRender(RenderContext rcon){}
	
	@Override
	default void preRender(RenderContext rcon, double delta){}
	
	/**
	 * 
	 * @param rcon
	 * 
	 * @throws RenderException
	 * 
	 * @see RenderHelper
	 */
	void render(RenderContext rcon) throws RenderException;
	
}
