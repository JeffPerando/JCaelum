
package com.elusivehawk.engine.render.opengl;

/**
 * 
 * Experimental OpenGL v3.0 support.
 * 
 * @author Elusivehawk
 */
public interface IGL3
{
	public void glBindVertexArray(int array);
	
	public void glDeleteVertexArrays(int array);
	
	public int glGenVertexArrays();
	
	public void glGenVertexArrays(int[] arrays);
	
}
