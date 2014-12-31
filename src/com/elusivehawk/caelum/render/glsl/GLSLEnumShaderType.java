
package com.elusivehawk.caelum.render.glsl;

import com.elusivehawk.caelum.render.gl.GLConst;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum GLSLEnumShaderType
{
	VERTEX(GLConst.GL_VERTEX_SHADER),
	TESS(GLConst.GL_TESS_CONTROL_SHADER),
	EVAL(GLConst.GL_TESS_EVALUATION_SHADER),
	GEOM(GLConst.GL_GEOMETRY_SHADER),
	FRAG(GLConst.GL_FRAGMENT_SHADER),
	COMP(GLConst.GL_COMPUTE_SHADER);
	
	private final int gl;
	
	@SuppressWarnings("unqualified-field-access")
	GLSLEnumShaderType(int glenum)
	{
		gl = glenum;
		
	}
	
	public int getGLId()
	{
		return this.gl;
	}
	
}
