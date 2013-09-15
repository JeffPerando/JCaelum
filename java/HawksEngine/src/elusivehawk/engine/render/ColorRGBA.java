
package elusivehawk.engine.render;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ColorRGBA extends Color
{
	public int color = 0;
	
	public ColorRGBA(){}
	
	public ColorRGBA(int col)
	{
		color = col;
		
	}
	
	public ColorRGBA(ByteBuffer buf)
	{
		this(buf.get(), buf.get(), buf.get(), buf.get());
		
	}
	
	public ColorRGBA(java.awt.Color col)
	{
		color = col.getRGB();
		
	}
	
	public ColorRGBA(Color col)
	{
		this(col.getColor(EnumColor.RED), col.getColor(EnumColor.GREEN), col.getColor(EnumColor.BLUE), col.getColor(EnumColor.ALPHA));
		
	}
	
	public ColorRGBA(int r, int g, int b)
	{
		this((byte)r, (byte)g, (byte)b);
		
	}
	
	public ColorRGBA(byte r, byte g, byte b)
	{
		this(r, g, b, 0);
		
	}
	
	public ColorRGBA(int r, int g, int b, int a)
	{
		this((byte)r, (byte)g, (byte)b, (byte)a);
		
	}
	
	public ColorRGBA(byte r, byte g, byte b, byte a)
	{
		color = ((r << 24) | (g << 16) | (b << 8) | a);
		
	}
	
	@Override
	public boolean store(IntBuffer buf)
	{
		buf.put(this.getColor());
		
		return true;
	}
	
	@Override
	public int getColor()
	{
		return this.color;
	}
	
	@Override
	public byte getColor(EnumColor id)
	{
		return (byte)((this.color >> id.rgba) & 0xFF);
	}
	
	@Override
	public float getColorFloat(EnumColor id)
	{
		return ((float)this.getColor(id)) / 255;
	}
	
	@Override
	public boolean supportsAlpha()
	{
		return true;
	}
	
	@Override
	public void loadIntoBuffer(ByteBuffer buf, boolean alpha)
	{
		buf.put(this.getColor(EnumColor.RED));
		buf.put(this.getColor(EnumColor.GREEN));
		buf.put(this.getColor(EnumColor.BLUE));
		buf.put(alpha ? this.getColor(EnumColor.ALPHA) : (byte)0);
		
	}
	
	@Override
	public void loadIntoBuffer(FloatBuffer buf, boolean alpha)
	{
		buf.put(this.getColorFloat(EnumColor.RED));
		buf.put(this.getColorFloat(EnumColor.GREEN));
		buf.put(this.getColorFloat(EnumColor.BLUE));
		buf.put(alpha ? this.getColorFloat(EnumColor.ALPHA) : 0f);
		
	}
	
}
