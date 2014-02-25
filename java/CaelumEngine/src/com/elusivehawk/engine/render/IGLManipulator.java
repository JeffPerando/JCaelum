
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.opengl.GLProgram;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IGLManipulator
{
	public void manipulateUniforms(RenderContext context, GLProgram program);
	
}
