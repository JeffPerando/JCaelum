
package com.elusivehawk.caelum.render.tex;

import java.nio.ByteBuffer;
import java.util.function.BiConsumer;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * Abstraction layer for the different kinds of images there are out there.
 * 
 * @author Elusivehawk
 */
public interface ILegibleImage
{
	ColorFormat getFormat();
	
	int getPixel(int x, int y);
	
	void setPixel(int x, int y, int color);
	
	int getHeight();
	
	int getWidth();
	
	default Color getColor(int x, int y)
	{
		return new Color(this.getFormat(), this.getPixel(x, y));
	}
	
	default void setPixel(int x, int y, Color color)
	{
		this.setPixel(x, y, color.convertTo(this.getFormat()).getColor());
		
	}
	
	default ByteBuffer toBytes()
	{
		return toBytes(this.getFormat());
	}
	
	default ByteBuffer toBytes(ColorFormat format)
	{
		ByteBuffer buf = BufferHelper.createByteBuffer(this.getWidth() * this.getHeight() * format.filterCount());
		
		Color col = new Color(this.getFormat());
		Color n = new Color(format);
		
		this.forEach(((x, y) ->
		{
			n.setColor(col.setColor(this.getPixel(x, y))).writeToBuffer(buf);
			
		}));
		
		buf.flip();
		
		return buf;
	}
	
	default void forEach(BiConsumer<Integer, Integer> consumer)
	{
		for (int x = 0; x < this.getWidth(); x++)
		{
			for (int y = 0; y < this.getHeight(); y++)
			{
				consumer.accept(x, y);
				
			}
			
		}
		
	}
	
}
