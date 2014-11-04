
package com.elusivehawk.caelum.render.gl;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum GLEnumError
{
	GL_NO_ERROR(GLConst.GL_NO_ERROR),
	GL_INVALID_ENUM(GLConst.GL_INVALID_ENUM),
	GL_INVALID_VALUE(GLConst.GL_INVALID_VALUE),
	GL_INVALID_OPERATION(GLConst.GL_INVALID_OPERATION),
	GL_INVALID_FRAMEBUFFER_OPERATION(GLConst.GL_INVALID_FRAMEBUFFER_OPERATION),
	GL_OUT_OF_MEMORY(GLConst.GL_OUT_OF_MEMORY),
	GL_STACK_OVERFLOW(GLConst.GL_STACK_OVERFLOW),
	GL_STACK_UNDERFLOW(GLConst.GL_STACK_UNDERFLOW);
	
	public final int glCode;
	
	@SuppressWarnings("unqualified-field-access")
	GLEnumError(int gl)
	{
		glCode = gl;
		
	}
	
	public static GLEnumError get(int err)
	{
		for (GLEnumError e : values())
		{
			if (e.glCode == err)
			{
				return e;
			}
			
		}
		
		return null;
	}
	
}
