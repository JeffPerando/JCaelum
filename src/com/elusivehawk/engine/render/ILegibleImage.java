
package com.elusivehawk.engine.render;

import java.nio.ByteBuffer;
import com.elusivehawk.engine.render.tex.Color;
import com.elusivehawk.engine.render.tex.ColorFormat;
import com.elusivehawk.util.BufferHelper;

/**
 * 
 * Abstraction layer for the different kinds of images there are out there.
 * 
 * @author Elusivehawk
 */
public interface ILegibleImage
{
	int getPixel(int x, int y);
	
	boolean setPixel(int x, int y, int color);
	
	int getHeight();
	
	int getWidth();
	
	default ColorFormat getFormat()
	{
		return ColorFormat.RGBA;
	}
	
	default ByteBuffer toBytes()
	{
		return toBytes(this.getFormat());
	}
	
	default ByteBuffer toBytes(ColorFormat format)
	{
		ByteBuffer buf = BufferHelper.createByteBuffer(this.getHeight() * this.getWidth() * this.getFormat().filterCount());
		Color col = new Color(format);
		
		for (int x = 0; x < this.getWidth(); ++x)
		{
			for (int y = 0; y < this.getHeight(); ++y)
			{
				col.setColor(this.getPixel(x, y));
				col.writeToBuffer(buf);
				
			}
			
		}
		
		buf.flip();
		
		return buf;
	}
	
}
