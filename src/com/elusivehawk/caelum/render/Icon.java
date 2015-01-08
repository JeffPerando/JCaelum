
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.prefab.Rectangle;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Icon
{
	public static final Icon BLANK_ICON = new Icon(0, 0, 1, 1);
	
	private final float[] stats;
	private final float[][] corners;
	
	public Icon(Rectangle r)
	{
		this(r.x, r.y, r.z, r.w);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Icon(float x, float y, float z, float w)
	{
		stats = new float[]{x, y, z, w};
		corners = new float[][]{{x, y}, {x, w}, {z, y}, {z, w}};
		
	}
	
	public float[] getCorner(int c)
	{
		return this.corners[c];
	}
	
	public float[] getRawCornerInfo()
	{
		return this.stats;
	}
	
}
