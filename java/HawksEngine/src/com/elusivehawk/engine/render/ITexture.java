
package com.elusivehawk.engine.render;


/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ITexture extends IGLCleanable
{
	public int getTexture(boolean next);
	
	public boolean isStatic();
	
}
