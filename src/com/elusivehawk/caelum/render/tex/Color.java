
package com.elusivehawk.caelum.render.tex;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import com.elusivehawk.util.storage.BufferHelper;

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
	public Color(ColorFormat cf)
	{
		format = cf;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Color(ColorFormat cf, int col)
	{
		this(cf);
		
		color = col;
		
	}
	
	public Color(java.awt.Color col)
	{
		this(ColorFormat.RGBA, col.getRGB());
		
	}
	
	public Color(ColorFormat cf, int a, int b, int c)
	{
		this(cf, a, b, c, 0);
		
	}
	
	public Color(ColorFormat cf, int a, int b, int c, int d)
	{
		this(cf, new byte[]{(byte)a, (byte)b, (byte)c, (byte)d});
		
	}
	
	public Color(ColorFormat cf, float a, float b, float c, float d)
	{
		this(cf, (byte)(255 * a), (byte)(255 * b), (byte)(255 * c), (byte)(255 * d));
		
	}
	
	public Color(ColorFormat cf, byte... bs)
	{
		this(cf);
		
		int c = 0;
		
		for (ColorFilter col : ColorFilter.values())
		{
			if (c == bs.length)
			{
				return;
			}
			
			if (!cf.supports(col))
			{
				continue;
			}
			
			setColor(col, bs[c++]);
			
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
	
	public Color setColor(int col)
	{
		this.color = col;
		
		return this;
	}
	
	public Color setColor(ColorFilter cf, float col)
	{
		return this.setColor(cf, (byte)(255 * col));
	}
	
	public Color setColor(ColorFilter cf, byte col)
	{
		this.color = (this.color << this.format.getColorOffset(cf)) | col;
		
		return this;
	}
	
	public byte getColor(ColorFilter cf)
	{
		return (byte)((this.getColor() >> this.format.getColorOffset(cf)) & 0xFF);
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
