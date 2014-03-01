
package com.elusivehawk.engine.render.opengl;

/**
 * 
 * Interface for texturing.
 * 
 * @author Elusivehawk
 */
public interface ITexture extends IGLBindable
{
	/**
	 * 
	 * @return The texture ID to use.
	 */
	public int getTexture();
	
}
