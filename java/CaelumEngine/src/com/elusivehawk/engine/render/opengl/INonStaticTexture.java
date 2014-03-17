
package com.elusivehawk.engine.render.opengl;

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
	 */
	public void updateTexture();
	
}
