
package com.elusivehawk.engine.render.two;

import com.elusivehawk.util.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Icon
{
	private final Vector[] corners = new Vector[4];
	
	@SuppressWarnings("unqualified-field-access")
	public Icon(float x, float y, float z, float w)
	{
		corners[0] = new Vector(x, y);
		corners[1] = new Vector(z, y);
		corners[2] = new Vector(x, w);
		corners[3] = new Vector(z, w);
		
	}
	
	public Vector[] getCorners()
	{
		return this.corners;
	}
	
}
