
package com.elusivehawk.engine.render;


/**
 * 
 * Implement this to render stuff. Go nuts!
 * 
 * @author Elusivehawk
 */
public interface IRenderEngine
{
	/**
	 * 
	 * Called once every frame.
	 * 
	 * @param context The current rendering context.
	 */
	public void render(RenderContext context);
	
	/**
	 * 
	 * @param hub The current rendering HUB
	 * @return The priority of this renderer.
	 */
	public int getPriority(IRenderHUB hub);
	
}
