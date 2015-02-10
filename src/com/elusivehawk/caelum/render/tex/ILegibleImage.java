
package com.elusivehawk.caelum.render.tex;

import java.nio.ByteBuffer;
import com.elusivehawk.caelum.render.Icon;
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
	
	default ILegibleImage subImage(Icon icon)
	{
		return this.subImage(icon.getX(), icon.getY(), icon.getW(), icon.getH());
	}
	
	default ILegibleImage subImage(float x, float y, float w, float h)
	{
		int xp = (int)(x * this.getWidth());
		int yp = (int)(y * this.getHeight());
		int wp = (int)(w - x * this.getWidth());
		int hp = (int)(h - y * this.getHeight());
		
		ILegibleImage ret = new LegibleByteImage(this.getFormat(), wp, hp);
		
		for (int a = 0; a < wp; a++)
		{
			for (int b = 0; b < hp; b++)
			{
				ret.setPixel(a, b, this.getPixel(a + xp, b + yp));
				
			}
			
		}
		
		return ret;
	}
	
	default ByteBuffer toBytes()
	{
		return this.toBytes(this.getFormat());
	}
	
	default ByteBuffer toBytes(ColorFormat format)
	{
		return this.toBytes(Icon.BLANK_ICON, format);
	}
	
	default ByteBuffer toBytes(Icon icon)
	{
		return this.toBytes(icon, this.getFormat());
	}
	
	default ByteBuffer toBytes(Icon icon, ColorFormat format)
	{
		int xoff = (int)(this.getWidth() * icon.getX());
		int yoff = (int)(this.getHeight() * icon.getY());
		
		int w = (int)(this.getWidth() * icon.getW() - icon.getX());
		int h = (int)(this.getHeight() * icon.getH() - icon.getY());
		
		ByteBuffer buf = BufferHelper.createByteBuffer(w * h * format.filterCount());
		
		Color col = new Color(this.getFormat());
		Color n = new Color(format);
		
		for (int x = 0; x < w; x++)
		{
			for (int y = 0; y < h; y++)
			{
				n.setColor(col.setColor(this.getPixel(x + xoff, y + yoff))).writeToBuffer(buf);
				
			}
			
		}
		
		buf.flip();
		
		return buf;
	}
	
}
