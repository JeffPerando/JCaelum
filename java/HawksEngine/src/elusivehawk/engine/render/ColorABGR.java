
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
public class ColorABGR extends Color
{
	public int color = 0;
	
	public ColorABGR(){}
	
	public ColorABGR(int col)
	{
		color = col;
		
	}
	
	public ColorABGR(ByteBuffer buf)
	{
		this(buf.get(), buf.get(), buf.get(), buf.get());
		
	}
	
	public ColorABGR(Color col)
	{
		this(col.getColor(EnumColor.ALPHA), col.getColor(EnumColor.BLUE), col.getColor(EnumColor.GREEN), col.getColor(EnumColor.RED));
		
	}
	
	public ColorABGR(int b, int g, int r)
	{
		this((byte)b, (byte)g, (byte)r);
		
	}
	
	public ColorABGR(byte b, byte g, byte r)
	{
		this(0, b, g, r);
		
	}
	
	public ColorABGR(int a, int b, int g, int r)
	{
		this((byte)a, (byte)b, (byte)g, (byte)r);
		
	}
	
	public ColorABGR(byte a, byte b, byte g, byte r)
	{
		color = ((a << 24) | (b << 16) | (g << 8) | r);
		
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
		return (byte)((this.color >> id.abgr) & 0xFF);
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
		buf.put(alpha ? this.getColor(EnumColor.ALPHA) : 0);
		buf.put(this.getColor(EnumColor.BLUE));
		buf.put(this.getColor(EnumColor.GREEN));
		buf.put(this.getColor(EnumColor.RED));
		
	}
	
	@Override
	public void loadIntoBuffer(FloatBuffer buf, boolean alpha)
	{
		buf.put(alpha ? this.getColorFloat(EnumColor.ALPHA) : 0);
		buf.put(this.getColorFloat(EnumColor.BLUE));
		buf.put(this.getColorFloat(EnumColor.GREEN));
		buf.put(this.getColorFloat(EnumColor.RED));
		
	}
	
}
