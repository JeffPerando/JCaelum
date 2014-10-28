
package com.elusivehawk.engine.render;

import java.nio.ByteBuffer;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class LegibleByteImage implements ILegibleImage
{
	private final ByteBuffer buf;
	private final int width, height;
	
	public LegibleByteImage(int w, int h)
	{
		this(BufferHelper.createByteBuffer(w * h * 4), w, h);
	}
	
	@SuppressWarnings("unqualified-field-access")
	public LegibleByteImage(ByteBuffer bytes, int w, int h)
	{
		buf = bytes;
		width = w;
		height = h;
		
	}
	
	@Override
	public int getPixel(int x, int y)
	{
		return ((ByteBuffer)this.buf.position(x + (y * this.height) * 4)).getInt();
	}
	
	@Override
	public boolean setPixel(int x, int y, int color)
	{
		this.buf.putInt(x + (y * this.height) * 4, color);
		
		return true;
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
	
}
