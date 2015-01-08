
package com.elusivehawk.caelum.render.tex;

import java.nio.ByteBuffer;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class LegibleByteImage implements ILegibleImage
{
	private final ColorFormat format;
	public final ByteBuffer buf;
	private final int width, height;
	
	public LegibleByteImage(ColorFormat cf, int w, int h)
	{
		this(cf, w, h, BufferHelper.createByteBuffer(w * h * cf.filterCount()));
		
	}
	
	public LegibleByteImage(ColorFormat cf, int w, int h, IPopulator<LegibleByteImage> pop)
	{
		this(cf, w, h);
		
		pop.populate(this);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public LegibleByteImage(ColorFormat cf, int w, int h, ByteBuffer bytes)
	{
		assert bytes != null;
		
		format = cf;
		width = w;
		height = h;
		buf = bytes;
		
	}
	
	public LegibleByteImage(ColorFormat cf, int w, int h, ByteBuffer bytes, IPopulator<LegibleByteImage> pop)
	{
		this(cf, w, h, bytes);
		
		pop.populate(this);
		
	}
	
	@Override
	public ColorFormat getFormat()
	{
		return this.format;
	}
	
	@Override
	public int getPixel(int x, int y)
	{
		return this.buf.getInt(y * this.height + x * this.format.filterCount());
	}
	
	@Override
	public void setPixel(int x, int y, int color)
	{
		this.buf.putInt(y * this.height + x * this.format.filterCount(), color);
		
	}
	
	@Override
	public int getHeight()
	{
		return this.height;
	}
	
	@Override
	public int getWidth()
	{
		return this.width;
	}
	
	@Override
	public ByteBuffer toBytes()
	{
		return this.buf;
	}
	
	@Override
	public String toString()
	{
		StringBuilder b = new StringBuilder();
		
		b.append(String.format("(%s, %s)", this.format, this.buf.capacity()));
		
		b.append("[");
		
		for (int c = 0; c < this.buf.capacity(); c++)
		{
			if (c > 0)
			{
				b.append(", ");
				
			}
			
			b.append(this.buf.get(c) & 0xFF);
			
		}
		
		b.append("]");
		
		return b.toString();
	}
	
}
