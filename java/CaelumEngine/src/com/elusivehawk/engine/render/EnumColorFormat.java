
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.util.Buffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum EnumColorFormat
{
	RGBA(EnumColorFilter.RED, EnumColorFilter.GREEN, EnumColorFilter.BLUE, EnumColorFilter.ALPHA),
	ARGB(EnumColorFilter.ALPHA, EnumColorFilter.RED, EnumColorFilter.GREEN, EnumColorFilter.BLUE),
	ABGR(EnumColorFilter.ALPHA, EnumColorFilter.BLUE, EnumColorFilter.GREEN, EnumColorFilter.RED),
	BGRA(EnumColorFilter.BLUE, EnumColorFilter.GREEN, EnumColorFilter.RED, EnumColorFilter.ALPHA);
	
	public final EnumColorFilter[] colors;
	public final boolean alpha;
	
	@SuppressWarnings("unqualified-field-access")
	EnumColorFormat(EnumColorFilter... f)
	{
		colors = f;
		
		boolean flag = false;
		
		for (EnumColorFilter color : colors)
		{
			if (color == EnumColorFilter.ALPHA)
			{
				flag = true;
				
			}
			
		}
		
		alpha = flag;
		
	}
	
	public int getColorOffset(EnumColorFilter col)
	{
		for (int c = 0; c < this.colors.length; c++)
		{
			if (this.colors[c] == col)
			{
				return 24 - (c * 8);
			}
			
		}
		
		return 0;
	}
	
	public Color convert(Color old)
	{
		if (old.format.ordinal() == this.ordinal())
		{
			return old;
		}
		
		Buffer<Byte> buf = new Buffer<Byte>();
		
		for (EnumColorFilter col : this.colors)
		{
			buf.put(old.getColor(col));
			
		}
		
		return new Color(this, buf);
	}
	
}
