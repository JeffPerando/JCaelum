
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.prefab.Rectangle;
import com.elusivehawk.util.parse.json.JsonArray;

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
	private final float[] corners;
	
	public Icon(Rectangle r)
	{
		this(r.x, r.y, r.z, r.w);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Icon(float x, float y, float z, float w)
	{
		stats = new float[]{x, y, z, w};
		corners = new float[]{x, y, z, y, x, w, z, w};
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Icon(JsonArray json)
	{
		if (json.length() < 4)
		{
			throw new ArrayIndexOutOfBoundsException("Too small of an array: Needs to be 4 or more");
		}
		
		float[] fs = new float[4];
		
		for (int c = 0; c < 4; c++)
		{
			Object obj = json.getValue(c);
			
			if (!(obj instanceof Double))
			{
				throw new CaelumException("Found invalid input for icon in JSON array index %s: %s", c, obj);
			}
			
			fs[c] = ((Double)obj).floatValue();
			
		}
		
		stats = fs;
		corners = new float[]{fs[0], fs[1], fs[2], fs[1], fs[0], fs[3], fs[2], fs[3]};
		
	}
	
	public float getX(int corner)
	{
		return this.corners[2 * corner];
	}
	
	public float getY(int corner)
	{
		return this.corners[2 * corner + 1];
	}
	
	public float[] getRawCornerInfo()
	{
		return this.stats;
	}
	
}
