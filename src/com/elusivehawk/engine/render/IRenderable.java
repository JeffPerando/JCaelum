
package com.elusivehawk.engine.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IRenderable extends IPreRenderer, IPostRenderer
{
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
