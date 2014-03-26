
package com.elusivehawk.engine.render;

import java.nio.FloatBuffer;
import com.elusivehawk.engine.util.BufferHelper;
import com.elusivehawk.engine.util.io.ByteArray;
import com.elusivehawk.engine.util.io.IByteReader;

/**
 * 
 * The much more flexible cousin to {@link java.awt.Color}.
 * 
 * @author Elusivehawk
 */
public class Color
{
	public static final Color BLACK = new Color(EnumColorFormat.RGBA, 0x000000);
	public static final Color GREY = new Color(EnumColorFormat.RGBA, 0x7F7F7F);
	public static final Color GRAY = GREY;
	public static final Color WHITE = new Color(EnumColorFormat.RGBA, 0xFFFFFF);
	
	public static final Color RED = new Color(EnumColorFormat.RGBA, 0xFF0000);
	public static final Color GREEN = new Color(EnumColorFormat.RGBA, 0x00FF00);
	public static final Color BLUE = new Color(EnumColorFormat.RGBA, 0x0000FF);
	
	public static final Color YELLOW = new Color(EnumColorFormat.RGBA, 0xFFFF00);
	public static final Color PINK = new Color(EnumColorFormat.RGBA, 0xFF00FF);
	public static final Color CYAN = new Color(EnumColorFormat.RGBA, 0x00FFFF);
	
	public final EnumColorFormat format;
	protected int color = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public Color(EnumColorFormat f)
	{
		format = f;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Color(EnumColorFormat f, int col)
	{
		this(f);
		
		color = col;
		
	}
	
	public Color(java.awt.Color col)
	{
		this(EnumColorFormat.RGBA, col.getRGB());
		
	}
	
	public Color(EnumColorFormat f, int a, int b, int c, int d)
	{
		this(f, new byte[]{(byte)a, (byte)b, (byte)c, (byte)d});
		
	}
	
	public Color(EnumColorFormat f, float a, float b, float c, float d)
	{
		this(f, (byte)(255 * a), (byte)(255 * b), (byte)(255 * c), (byte)(255 * d));
		
	}
	
	public Color(EnumColorFormat f, byte... cols)
	{
		this(f, new ByteArray(cols));
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Color(EnumColorFormat f, IByteReader buf)
	{
		this(f);
		
		for (EnumColorFilter col : EnumColorFilter.values())
		{
			if (!f.supports(col))
			{
				continue;
			}
			
			color = (color << f.getColorOffset(col)) | buf.read();
			
		}
		
	}
	
	@Override
	public int hashCode()
	{
		return this.getColor();
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
		return this.format.supports(EnumColorFilter.ALPHA);
	}
	
	public FloatBuffer asBufferF()
	{
		float[] colors = new float[this.format.filters.length];
		int c = 0;
		
		for (EnumColorFilter filter : this.format.filters)
		{
			colors[c++] = this.getColorFloat(filter);
			
		}
		
		return BufferHelper.makeFloatBuffer(colors);
	}
	
}
