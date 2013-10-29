
package com.elusivehawk.engine.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ILegibleImage
{
	public int getPixel(int x, int y);
	
	public EnumColorFormat getFormat();
	
	public int getHeight();
	
	public int getWidth();
	
}
