
package com.elusivehawk.engine.render.two;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Icon
{
	private final float[][] corners = new float[2][4];
	
	@SuppressWarnings("unqualified-field-access")
	public Icon(float x, float y, float z, float w)
	{
		corners[0] = new float[]{x, y};
		corners[1] = new float[]{z, y};
		corners[2] = new float[]{x, w};
		corners[3] = new float[]{z, w};
		
	}
	
	public float[][] getCorners()
	{
		return this.corners;
	}
	
}
