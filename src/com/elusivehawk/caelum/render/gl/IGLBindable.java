
package com.elusivehawk.caelum.render.gl;

import com.elusivehawk.caelum.render.RenderContext;

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
