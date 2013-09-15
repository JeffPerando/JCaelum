
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
public class ColorBGRA extends Color
{
	public int color = 0;
	
	public ColorBGRA(){}
	
	public ColorBGRA(int col)
	{
		color = col;
		
	}
	
	public ColorBGRA(ByteBuffer buf)
	{
		this(buf.get(), buf.get(), buf.get(), buf.get());
		
	}
	
	public ColorBGRA(Color col)
	{
		this(col.getColor(EnumColor.BLUE), col.getColor(EnumColor.GREEN), col.getColor(EnumColor.RED), col.getColor(EnumColor.ALPHA));
		
	}
	
	public ColorBGRA(int b, int g, int r)
	{
		this((byte)b, (byte)g, (byte)r);
		
	}
	
	public ColorBGRA(byte b, byte g, byte r)
	{
		this(b, g, r, 0);
		
	}
	
	public ColorBGRA(int b, int g, int r, int a)
	{
		this((byte)b, (byte)g, (byte)r, (byte)a);
		
	}
	
	public ColorBGRA(byte b, byte g, byte r, byte a)
	{
		color = ((b << 24) | (g << 16) | (r << 8) | a);
		
	}
	
	@Override
	public boolean store(IntBuffer buf)
	{
		buf.put(this.color);
		
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
		return (byte)((this.color >> id.bgra) & 0xFF);
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
		buf.put(this.getColor(EnumColor.BLUE));
		buf.put(this.getColor(EnumColor.GREEN));
		buf.put(this.getColor(EnumColor.RED));
		buf.put(alpha ? this.getColor(EnumColor.ALPHA) : 0);
		
	}
	
	@Override
	public void loadIntoBuffer(FloatBuffer buf, boolean alpha)
	{
		buf.put(this.getColorFloat(EnumColor.BLUE));
		buf.put(this.getColorFloat(EnumColor.GREEN));
		buf.put(this.getColorFloat(EnumColor.RED));
		buf.put(alpha ? this.getColorFloat(EnumColor.ALPHA) : 0);
		
	}
	
}
