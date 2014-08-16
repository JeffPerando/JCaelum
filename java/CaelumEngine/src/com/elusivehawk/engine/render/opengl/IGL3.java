
package com.elusivehawk.engine.render.opengl;

/**
 * 
 * Experimental OpenGL v3.0 support.
 * 
 * @author Elusivehawk
 */
public interface IGL3
{
	public void glBindVertexArray(int array) throws GLException;
	
	public void glDeleteVertexArrays(int array) throws GLException;
	
	public void glGenerateMipmap(int target) throws GLException;
	
	public int glGenVertexArrays() throws GLException;
	
	public void glGenVertexArrays(int[] arrays) throws GLException;
	
}
