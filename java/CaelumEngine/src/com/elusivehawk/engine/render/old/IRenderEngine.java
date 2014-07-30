
package com.elusivehawk.engine.render.old;

import com.elusivehawk.engine.render.RenderException;
import com.elusivehawk.engine.render.RenderContext;


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
	 * @param con The current rendering context.
	 * @param hub The current rendering HUB.
	 * @throws RenderException
	 */
	public void render(RenderContext con, IRenderHUB hub) throws RenderException;
	
	/**
	 * 
	 * @param hub The current rendering HUB.
	 * @return The priority of this renderer.
	 */
	public int getPriority(IRenderHUB hub);
	
}
