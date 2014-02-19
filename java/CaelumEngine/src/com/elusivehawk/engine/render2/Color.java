
package com.elusivehawk.engine.render2;

import com.elusivehawk.engine.util.io.ByteArray;
import com.elusivehawk.engine.util.io.ByteReader;

/**
 * 
 * The much more flexible cousin to {@link java.awt.Color}.
 * 
 * @author Elusivehawk
 */
public class Color
{
	protected int color = 0;
	public final EnumColorFormat format;
	
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
	
	public Color(EnumColorFormat f, byte... cols)
	{
		this(f, new ByteArray(cols));
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Color(EnumColorFormat f, ByteReader buf)
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
	
}
