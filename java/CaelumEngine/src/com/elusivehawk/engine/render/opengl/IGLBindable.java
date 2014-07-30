
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
	public boolean bind(RenderContext con);
	
	public void unbind(RenderContext con);
	
	public void glDelete(RenderContext con);
	
}
