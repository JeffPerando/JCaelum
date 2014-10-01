
package com.elusivehawk.engine.render;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import com.elusivehawk.util.BufferHelper;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.io.ByteArray;
import com.elusivehawk.util.io.IByteReader;

/**
 * 
 * The much more flexible cousin to {@link java.awt.Color}.
 * 
 * @author Elusivehawk
 * 
 * @see ColorFilter
 * @see ColorFormat
 */
public class Color
{
	public static final Color BLACK = new Color(ColorFormat.RGBA, 0x000000);
	public static final Color GREY = new Color(ColorFormat.RGBA, 0x7F7F7F);
	public static final Color GRAY = GREY;
	public static final Color WHITE = new Color(ColorFormat.RGBA, 0xFFFFFF);
	
	public static final Color RED = new Color(ColorFormat.RGBA, 0xFF0000);
	public static final Color GREEN = new Color(ColorFormat.RGBA, 0x00FF00);
	public static final Color BLUE = new Color(ColorFormat.RGBA, 0x0000FF);
	
	public static final Color YELLOW = new Color(ColorFormat.RGBA, 0xFFFF00);
	public static final Color PINK = new Color(ColorFormat.RGBA, 0xFF00FF);
	public static final Color CYAN = new Color(ColorFormat.RGBA, 0x00FFFF);
	
	public final ColorFormat format;
	public int color = 0;
	
	public Color()
	{
		this(ColorFormat.RGBA);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Color(ColorFormat f)
	{
		format = f;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Color(ColorFormat f, int col)
	{
		this(f);
		
		color = col;
		
	}
	
	public Color(java.awt.Color col)
	{
		this(ColorFormat.RGBA, col.getRGB());
		
	}
	
	public Color(ColorFormat f, int a, int b, int c, int d)
	{
		this(f, new byte[]{(byte)a, (byte)b, (byte)c, (byte)d});
		
	}
	
	public Color(ColorFormat f, float a, float b, float c, float d)
	{
		this(f, (byte)(255 * a), (byte)(255 * b), (byte)(255 * c), (byte)(255 * d));
		
	}
	
	public Color(ColorFormat f, byte... cols)
	{
		this(f, new ByteArray(cols));
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Color(ColorFormat f, IByteReader buf)
	{
		this(f);
		
		for (ColorFilter col : ColorFilter.values())
		{
			if (!f.supports(col))
			{
				continue;
			}
			
			try
			{
				color = (color << f.getColorOffset(col)) | buf.read();
				
			}
			catch (Throwable e)
			{
				Logger.log().err(e);
				break;
			}
			
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
	
	public byte getColor(ColorFilter col)
	{
		return (byte)((this.getColor() >> this.format.getColorOffset(col)) & 0xFF);
	}
	
	public float getColorf(ColorFilter col)
	{
		return this.getColor(col) / (byte)255;
	}
	
	public boolean supportsAlpha()
	{
		return this.format.supports(ColorFilter.ALPHA);
	}
	
	public float[] asFloats()
	{
		float[] colors = new float[this.format.filters.length];
		int c = 0;
		
		for (ColorFilter filter : this.format.filters)
		{
			colors[c++] = this.getColorf(filter);
			
		}
		
		return colors;
	}
	
	public FloatBuffer asBufferF()
	{
		return BufferHelper.makeFloatBuffer(this.asFloats());
	}
	
	public void writeToBuffer(ByteBuffer buf)
	{
		for (ColorFilter filter : this.format.filters)
		{
			buf.put(this.getColor(filter));
			
		}
		
	}
	
	public void writeToBufferf(FloatBuffer buf)
	{
		buf.put(this.asFloats());
		
	}
	
}
