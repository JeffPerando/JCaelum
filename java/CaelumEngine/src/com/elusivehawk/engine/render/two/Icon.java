
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
	public Icon(float x, float y, float z, float w)
	{
		info[0] = x;
		info[1] = y;
		
		info[2] = z;
		info[3] = y;
		
		info[4] = x;
		info[5] = w;
		
		info[6] = z;
		info[7] = w;
		
	}
	
	public float[] getInfo()
	{
		return this.info;
	}
	
}
