
package com.elusivehawk.engine.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IPostRenderer
{
	/**
	 * 
	 * Called once every frame, after rendering.
	 * 
	 * @param context The current rendering context.
	 */
	public void postRender(RenderContext context);
	
}
