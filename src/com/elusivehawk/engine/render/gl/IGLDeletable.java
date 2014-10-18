
package com.elusivehawk.engine.render.gl;

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
