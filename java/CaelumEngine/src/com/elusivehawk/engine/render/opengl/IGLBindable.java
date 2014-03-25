
package com.elusivehawk.engine.render.opengl;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IGLBindable
{
	public boolean bind();
	
	public void unbind();
	
	public void glDelete();
	
}
