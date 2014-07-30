
package com.elusivehawk.engine.render.opengl;

import com.elusivehawk.engine.render.RenderSystem;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IGLManipulator
{
	/**
	 * 
	 * Called once every frame; Do whatever you need to before rendering in this method.
	 * 
	 * @param sys The current rendering system
	 */
	public void updateUniforms(RenderSystem sys);
	
	public void manipulateUniforms(RenderSystem sys, GLProgram p);
	
	/**
	 * 
	 * Called once every frame, after rendering.
	 */
	public void postRender();
	
}
