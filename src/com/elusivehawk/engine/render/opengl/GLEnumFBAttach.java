
package com.elusivehawk.engine.render.opengl;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum GLEnumFBAttach
{
	GL_COLOR_ATTACHMENT(GLConst.GL_COLOR_ATTACHMENT0),
	GL_DEPTH_ATTACHMENT(GLConst.GL_DEPTH_ATTACHMENT),
	GL_DEPTH_STENCIL_ATTACHMENT(GLConst.GL_DEPTH_STENCIL_ATTACHMENT),
	GL_STENCIL_ATTACHMENT(GLConst.GL_STENCIL_ATTACHMENT);
	
	public final int gl;
	
	@SuppressWarnings("unqualified-field-access")
	GLEnumFBAttach(int i)
	{
		gl = i;
	
	}
	
}
