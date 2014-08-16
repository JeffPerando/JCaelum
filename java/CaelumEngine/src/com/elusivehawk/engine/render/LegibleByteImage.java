
package com.elusivehawk.engine.render;

import java.nio.ByteBuffer;

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
