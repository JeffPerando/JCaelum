
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
public class ColorARGB extends Color
{
	public int color = 0;
	
	public ColorARGB(){}
	
	public ColorARGB(int col)
	{
		color = col;
		
	}
	
	public ColorARGB(ByteBuffer buf)
	{
		this(buf.get(), buf.get(), buf.get(), buf.get());
		
	}
	
	public ColorARGB(Color col)
	{
		this(col.getColor(EnumColor.ALPHA), col.getColor(EnumColor.RED), col.getColor(EnumColor.GREEN), col.getColor(EnumColor.BLUE));
		
	}
	
	public ColorARGB(int r, int g, int b)
	{
		this((byte)r, (byte)g, (byte)b);
		
	}
	
	public ColorARGB(byte r, byte g, byte b)
	{
		this(0, r, g, b);
		
	}
	
	public ColorARGB(int a, int r, int g, int b)
	{
		this((byte)a, (byte)r, (byte)g, (byte)b);
		
	}
	
	public ColorARGB(byte a, byte r, byte g, byte b)
	{
		color = ((a << 24) | (r << 16) | (g << 8) | b);
		
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
		return (byte)((this.color >> id.argb) & 0xFF);
	}
	
	@Override
	public float getColorFloat(EnumColor id)
	{
		return this.getColor(id) / 255;
	}
	
	@Override
	public boolean supportsAlpha()
	{
		return true;
	}
	
	@Override
	public void loadIntoBuffer(ByteBuffer buf, boolean alpha)
	{
		buf.put(alpha ? this.getColor(EnumColor.ALPHA) : (byte)0);
		buf.put(this.getColor(EnumColor.RED));
		buf.put(this.getColor(EnumColor.GREEN));
		buf.put(this.getColor(EnumColor.BLUE));
		
	}
	
	@Override
	public void loadIntoBuffer(FloatBuffer buf, boolean alpha)
	{
		buf.put(alpha ? this.getColorFloat(EnumColor.ALPHA) : 0f);
		buf.put(this.getColorFloat(EnumColor.RED));
		buf.put(this.getColorFloat(EnumColor.GREEN));
		buf.put(this.getColorFloat(EnumColor.BLUE));
		
	}
	
}
