
package elusivehawk.engine.render;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import elusivehawk.engine.util.BufferHelper;
import elusivehawk.engine.util.IStoreable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Color implements IStoreable
{
	public int color = 0;
	protected final EnumColorFormat format;
	
	public Color(EnumColorFormat f)
	{
		format = f;
		
	}
	
	public Color(EnumColorFormat f, int col)
	{
		this(f);
		
		color = col;
		
	}
	
	public Color(java.awt.Color col)
	{
		this(EnumColorFormat.RGBA, col.getRGB());
		
	}
	
	public Color(int a, int b, int c, int d)
	{
		this(EnumColorFormat.RGBA, a, b, c, d);
		
	}
	
	public Color(EnumColorFormat f, int a, int b, int c, int d)
	{
		this(f, new byte[]{(byte)a, (byte)b, (byte)c, (byte)d});
		
	}
	
	public Color(byte... cols)
	{
		this(EnumColorFormat.RGBA, cols);
		
	}
	
	public Color(EnumColorFormat f, byte... cols)
	{
		this(f, BufferHelper.makeByteBuffer(cols));
		
	}
	
	public Color(ByteBuffer buf)
	{
		this(EnumColorFormat.RGBA, buf);
		
	}
	
	public Color(EnumColorFormat f, ByteBuffer buf)
	{
		this(f);
		
		for (EnumColorFilter col : f.colors)
		{
			color = (color << f.getColorOffset(col)) | buf.get();
			
		}
		
	}
	
	@Override
	public boolean store(ByteBuffer buf)
	{
		for (EnumColorFilter col : this.format.colors)
		{
			buf.put(this.getColor(col));
			
		}
		
		return true;
	}
	
	@Override
	public boolean store(FloatBuffer buf)
	{
		for (EnumColorFilter col : this.format.colors)
		{
			buf.put(this.getColorFloat(col));
			
		}
		
		return true;
	}
	
	@Override
	public boolean store(IntBuffer buf)
	{
		buf.put(this.getColor());
		
		return true;
	}
	
	@Override
	public int hashCode()
	{
		return this.getColor();
	}
	
	public EnumColorFormat getFormat()
	{
		return this.format;
	}
	
	public int getColor()
	{
		return this.color;
	}
	
	public boolean setColor(int col)
	{
		this.color = col;
		
		return true;
	}
	
	public byte getColor(EnumColorFilter col)
	{
		return (byte)((this.getColor() >> this.format.getColorOffset(col)) & 0xFF);
	}
	
	public float getColorFloat(EnumColorFilter col)
	{
		return this.getColor(col) / (byte)255;
	}
	
	public boolean supportsAlpha()
	{
		return this.format.supportsAlpha();
	}
	
}
