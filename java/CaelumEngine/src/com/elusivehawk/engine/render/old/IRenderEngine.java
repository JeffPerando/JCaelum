
package com.elusivehawk.engine.render.old;

import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.RenderException;


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
	 * @param context The current rendering context.
	 * @param hub The current rendering HUB.
	 * @throws RenderException
	 */
	public void render(RenderContext context, IRenderHUB hub) throws RenderException;
	
	/**
	 * 
	 * @param hub The current rendering HUB.
	 * @return The priority of this renderer.
	 */
	public int getPriority(IRenderHUB hub);
	
}
