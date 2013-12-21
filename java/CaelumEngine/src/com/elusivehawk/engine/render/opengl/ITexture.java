
package com.elusivehawk.engine.render.opengl;

/**
 * 
 * Interface for texturing.
 * 
 * @author Elusivehawk
 */
public interface ITexture extends IGLCleanable
{
	/**
	 * 
	 * @param next Whether or not to go to the next index. (Note: Only useful for animated textures)
	 * @return The texture ID to use.
	 */
	public int getTexture(boolean next);
	
	/**
	 * 
	 * @return True if this texture changes.
	 */
	public boolean isStatic();
	
}
