
package com.elusivehawk.engine.render.two;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Icon
{
	private final float[] info = new float[8];
	
	@SuppressWarnings("unqualified-field-access")
	public Icon(float x, float y, float w, float h)
	{
		info[0] = x;
		info[1] = y;
		
		info[2] = w;
		info[3] = y;
		
		info[4] = x;
		info[5] = h;
		
		info[6] = w;
		info[7] = h;
		
	}
	
	public float[] getInfo()
	{
		return this.info;
	}
	
}
