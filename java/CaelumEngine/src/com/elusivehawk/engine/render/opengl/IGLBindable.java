
package com.elusivehawk.engine.render.opengl;

import com.elusivehawk.engine.render.RenderSystem;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IGLBindable
{
	public boolean bind(RenderSystem sys);
	
	public void unbind(RenderSystem sys);
	
	public void glDelete(RenderSystem sys);
	
}
