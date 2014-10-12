
package com.elusivehawk.engine.render.opengl;

import com.elusivehawk.engine.render.RenderContext;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IGLDeletable
{
	void delete(RenderContext rcon);
	
}
