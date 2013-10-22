
package com.elusivehawk.engine.render;


/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ITexture extends IGLCleanable
{
	public int getTexture();
	
	public boolean isStatic();
	
	public int getHeight();
	
	public int getWidth();
	
}
