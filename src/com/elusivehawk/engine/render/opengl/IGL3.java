
package com.elusivehawk.engine.render.opengl;

/**
 * 
 * Experimental OpenGL v3.0 support.
 * 
 * @author Elusivehawk
 */
public interface IGL3
{
	void glBindVertexArray(int array) throws GLException;
	
	void glDeleteVertexArrays(int array) throws GLException;
	
	void glGenerateMipmap(int target) throws GLException;
	
	int glGenVertexArrays() throws GLException;
	
	void glGenVertexArrays(int[] arrays) throws GLException;
	
}
