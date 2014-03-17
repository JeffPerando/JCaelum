
package com.elusivehawk.engine.render.opengl;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IGLBindable
{
	public boolean bind(int... extras);
	
	public void unbind(int... extras);
	
	public void glDelete();
	
}
