
package elusivehawk.engine.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Polygon
{
	public final float[] vecs;
	public final int gl;
	
	public Polygon(float... vectors)
	{
		this(GL.GL_TRIANGLES, vectors);
		
	}
	
	public Polygon(int glMode, float... vectors)
	{
		gl = glMode;
		
		if (vectors.length != RenderHelper.getPointCount(gl) * 4)
		{
			throw new RuntimeException("Invalid number of floats found!");
		}
		
		vecs = vectors;
		
	}
	
}
