
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
	boolean bind(RenderContext rcon);
	
	void unbind(RenderContext rcon);
	
}
