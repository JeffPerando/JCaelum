
package com.elusivehawk.engine.render2;

/**
 * 
 * Abstraction layer for the different kinds of images there are out there.
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
