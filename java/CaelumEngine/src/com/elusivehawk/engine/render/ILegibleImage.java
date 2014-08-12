
package com.elusivehawk.engine.render;

import java.nio.IntBuffer;
import com.elusivehawk.util.BufferHelper;

/**
 * 
 * Abstraction layer for the different kinds of images there are out there.
 * 
 * @author Elusivehawk
 */
public interface ILegibleImage
{
	public int getPixel(int x, int y);
	
	public ColorFormat getFormat();
	
	public int getHeight();
	
	public int getWidth();
	
	default IntBuffer toInts()
	{
		return toInts(ColorFormat.RGBA);
	}
	
	default IntBuffer toInts(ColorFormat format)
	{
		IntBuffer buf = BufferHelper.createIntBuffer(this.getHeight() * this.getWidth());
		Color col = new Color(this.getFormat());
		
		for (int x = 0; x < this.getWidth(); ++x)
		{
			for (int y = 0; y < this.getHeight(); ++y)
			{
				col.setColor(this.getPixel(x, y));
				buf.put(format.convert(col).getColor());
				
			}
			
		}
		
		buf.flip();
		
		return buf;
	}
	
}
