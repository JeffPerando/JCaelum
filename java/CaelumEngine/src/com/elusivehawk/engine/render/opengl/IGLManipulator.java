
package com.elusivehawk.engine.render.opengl;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IGLManipulator
{
	public void updateUniforms();
	
	public void manipulateUniforms(GLProgram p);
	
}
