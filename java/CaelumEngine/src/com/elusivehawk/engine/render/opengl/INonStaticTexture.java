
package com.elusivehawk.engine.render.opengl;

import com.elusivehawk.engine.render.RenderContext;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface INonStaticTexture extends ITexture
{
	/**
	 * Called once a frame.
	 * 
	 * Note: Should not be called by user code.
	 * 
	 * @param context The current rendering context.
	 */
	public void updateTexture(RenderContext context);
	
}
