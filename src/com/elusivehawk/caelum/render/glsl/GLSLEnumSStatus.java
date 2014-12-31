
package com.elusivehawk.caelum.render.glsl;

import com.elusivehawk.caelum.render.gl.GLConst;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum GLSLEnumSStatus
{
	GL_COMPILE_STATUS(GLConst.GL_COMPILE_STATUS),
	GL_DELETE_STATUS(GLConst.GL_DELETE_STATUS),
	GL_INFO_LOG_LENGTH(GLConst.GL_INFO_LOG_LENGTH),
	GL_SHADER_SOURCE_LENGTH(GLConst.GL_SHADER_SOURCE_LENGTH),
	GL_SHADER_TYPE(GLConst.GL_SHADER_TYPE);
	
	public final int gl;
	
	@SuppressWarnings("unqualified-field-access")
	GLSLEnumSStatus(int i)
	{
		gl = i;
		
	}
	 
}
