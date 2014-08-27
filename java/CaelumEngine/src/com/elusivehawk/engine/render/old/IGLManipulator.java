
package com.elusivehawk.engine.render.old;

import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.opengl.GLProgram;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Deprecated
public interface IGLManipulator
{
	/**
	 * 
	 * Called once every frame; Do whatever you need to before rendering in this method.
	 * 
	 * @param rcon The current rendering context
	 */
	public void updateUniforms(RenderContext rcon);
	
	public void manipulateUniforms(RenderContext rcon, GLProgram p);
	
	/**
	 * 
	 * Called once every frame, after rendering.
	 */
	public void postRender();
	
}
