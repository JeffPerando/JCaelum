
package com.elusivehawk.engine.render.opengl;

import com.elusivehawk.engine.render.RenderContext;

/**
 * 
 * Interface for texturing.
 * 
 * @author Elusivehawk
 */
public interface ITexture extends IGLCleanable
{
	/**
	 * Called on every frame.
	 * 
	 * Note: Should not be called by user code.
	 * 
	 * @param context The current rendering context.
	 */
	public void updateTexture(RenderContext context);
	
	/**
	 * 
	 * @return The texture ID to use.
	 */
	public int getTexture();
	
	/**
	 * 
	 * @return True if this texture changes.
	 */
	public boolean isStatic();
	
}
