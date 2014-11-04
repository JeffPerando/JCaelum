
package com.elusivehawk.caelum.render.gl;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum GLEnumDrawType
{
	GL_POINTS(1, GLConst.GL_POINTS),
	GL_LINE_STRIP(0, GLConst.GL_LINE_STRIP),
	GL_LINE_LOOP(0, GLConst.GL_LINE_LOOP),
	GL_LINES(2, GLConst.GL_LINES),
	GL_LINE_STRIP_ADJACENCY(0, GLConst.GL_LINE_STRIP_ADJACENCY),
	GL_LINES_ADJACENCY(0, GLConst.GL_LINES_ADJACENCY),
	GL_TRIANGLE_STRIP(0, GLConst.GL_TRIANGLE_STRIP),
	GL_TRIANGLE_FAN(0, GLConst.GL_TRIANGLE_FAN),
	GL_TRIANGLES(3, GLConst.GL_TRIANGLES),
	GL_TRIANGLE_STRIP_ADJACENCY(0, GLConst.GL_TRIANGLE_STRIP_ADJACENCY),
	GL_TRIANGLES_ADJACENCY(0, GLConst.GL_TRIANGLES_ADJACENCY),
	GL_PATCHES(0, GLConst.GL_PATCHES);
	
	private final int pointcount, gl;
	
	@SuppressWarnings("unqualified-field-access")
	GLEnumDrawType(int points, int glType)
	{
		pointcount = points;
		gl = glType;
		
	}
	
	public int getPointCount()
	{
		return this.pointcount;
	}
	
	public int getGLId()
	{
		return this.gl;
	}
	
}
