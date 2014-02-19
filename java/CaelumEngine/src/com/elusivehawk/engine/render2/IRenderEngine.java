
package com.elusivehawk.engine.render2;


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
	 * @param hub The current rendering HUB
	 */
	public void render(IRenderHUB hub);
	
	/**
	 * 
	 * @param hub The current rendering HUB
	 * @return The priority of this renderer.
	 */
	public int getPriority(IRenderHUB hub);
	
}
