
package com.elusivehawk.engine.render.opengl;

import com.elusivehawk.engine.render.RenderContext;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IGLBindable
{
	public boolean bind(RenderContext context, int... extras);
	
	public void unbind(RenderContext context, int... extras);
	
	public void glDelete(RenderContext context);
	
}
