
package com.elusivehawk.engine.render.old;

import com.elusivehawk.engine.render.RenderException;
import com.elusivehawk.engine.render.RenderSystem;


/**
 * 
 * Implement this to render stuff. Go nuts!
 * 
 * @author Elusivehawk
 */
@Deprecated
public interface IRenderEngine
{
	/**
	 * Called once every frame.
	 * 
	 * @param sys The current rendering system.
	 * @param hub The current rendering HUB.
	 * @throws RenderException
	 */
	public void render(RenderSystem sys, IRenderHUB hub) throws RenderException;
	
	/**
	 * 
	 * @param hub The current rendering HUB.
	 * @return The priority of this renderer.
	 */
	public int getPriority(IRenderHUB hub);
	
}
