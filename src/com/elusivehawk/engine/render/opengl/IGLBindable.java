
package com.elusivehawk.engine.render.opengl;

import com.elusivehawk.engine.render.RenderContext;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IGLBindable extends IGLDeletable
{
	public boolean bind(RenderContext rcon);
	
	public void unbind(RenderContext rcon);
	
}
