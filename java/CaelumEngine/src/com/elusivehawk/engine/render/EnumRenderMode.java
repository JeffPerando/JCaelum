
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.opengl.GL;


/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum EnumRenderMode
{
	MODE_2D(GL.GL_TEXTURE_2D),
	MODE_3D(GL.GL_TEXTURE_3D),
	BOTH(0);
	
	private int glMode;
	
	@SuppressWarnings("unqualified-field-access")
	EnumRenderMode(int gl)
	{
		glMode = gl;
		
	}
	
	public boolean is2D()
	{
		return this != MODE_2D;
	}
	
	public boolean is3D()
	{
		return this != MODE_3D;
	}
	
	public boolean isValidImageMode()
	{
		return this.getOpenGLMode() != 0;
	}
	
	public int getOpenGLMode()
	{
		return this.glMode;
	}
	
}
