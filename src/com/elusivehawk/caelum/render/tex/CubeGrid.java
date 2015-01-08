
package com.elusivehawk.caelum.render.tex;

import java.nio.ByteBuffer;
import java.util.List;
import com.elusivehawk.util.storage.BufferHelper;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class CubeGrid implements ILegibleImage
{
	private final ColorFormat format;
	private final int width, height;
	
	private final List<ByteBuffer> layers = Lists.newArrayList();
	
	private ByteBuffer pixels;
	
	@SuppressWarnings("unqualified-field-access")
	public CubeGrid(ColorFormat fmt, int w, int h)
	{
		format = fmt;
		width = w;
		height = h;
		
		newLayer();
		
	}
	
	@Override
	public ColorFormat getFormat()
	{
		return this.format;
	}
	
	@Override
	public int getPixel(int x, int y)
	{
		return this.pixels.getInt(y * this.height + x * this.format.filterCount());
	}
	
	@Override
	public void setPixel(int x, int y, int color)
	{
		this.pixels.putInt(y * this.height + x * this.format.filterCount(), color);
		
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
	public ByteBuffer toBytes(ColorFormat fmt)
	{
		ByteBuffer ret = BufferHelper.createByteBuffer(this.width * this.height * fmt.filterCount() * this.getDepth());
		
		Color color = new Color(fmt);
		
		this.layers.forEach(((buf) ->
		{
			if (fmt == this.format)
			{
				ret.put(buf);
				
			}
			else
			{
				while (buf.hasRemaining())
				{
					for (ColorFilter filter : this.format.filters)
					{
						color.setColor(filter, buf.get());
						
					}
					
					color.writeToBuffer(ret);
					
				}
				
				buf.rewind();
				
			}
			
		}));
		
		return ret;
	}
	
	public int getDepth()
	{
		return this.layers.size();
	}
	
	public void newLayer()
	{
		this.pixels = BufferHelper.createByteBuffer(this.width * this.height * this.format.filterCount());
		this.layers.add(this.pixels);
		
	}
	
}
