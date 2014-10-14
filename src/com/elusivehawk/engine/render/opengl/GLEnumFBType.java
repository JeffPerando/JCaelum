
package com.elusivehawk.engine.render.opengl;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum GLEnumFBType
{
	GL_DRAW_FRAMEBUFFER(GLConst.GL_DRAW_FRAMEBUFFER),
	GL_READ_FRAMEBUFFER(GLConst.GL_READ_FRAMEBUFFER),
	GL_FRAMEBUFFER(GLConst.GL_FRAMEBUFFER);
	
	public final int gl;
	
	@SuppressWarnings("unqualified-field-access")
	GLEnumFBType(int glconst)
	{
		gl = glconst;
		
	}
	
}
