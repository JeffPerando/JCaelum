
package com.elusivehawk.caelum.render.gl;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum GLEnumDataUsage
{
	GL_STREAM_COPY(GLConst.GL_STREAM_COPY),
	GL_STREAM_DRAW(GLConst.GL_STREAM_DRAW),
	GL_STREAM_READ(GLConst.GL_STREAM_READ),
	
	GL_STATIC_COPY(GLConst.GL_STATIC_COPY),
	GL_STATIC_DRAW(GLConst.GL_STATIC_DRAW),
	GL_STATIC_READ(GLConst.GL_STATIC_READ),
	
	GL_DYNAMIC_COPY(GLConst.GL_DYNAMIC_COPY),
	GL_DYNAMIC_DRAW(GLConst.GL_DYNAMIC_DRAW),
	GL_DYNAMIC_READ(GLConst.GL_DYNAMIC_READ);
	
	private final int glId;
	
	@SuppressWarnings("unqualified-field-access")
	GLEnumDataUsage(int gl)
	{
		glId = gl;
		
	}
	
	public int getGLId()
	{
		return this.glId;
	}
	
}
