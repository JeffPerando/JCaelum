
package com.elusivehawk.engine.render.opengl;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IGLManipulator
{
	/**
	 * Called once every frame; Do whatever you need to before rendering in this method.
	 */
	public void updateUniforms();
	
	public void manipulateUniforms(GLProgram p);
	
}
