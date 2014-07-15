
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.util.io.ByteBufWrapper;
import com.elusivehawk.engine.util.storage.Buffer;

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
	
	public final EnumColorFilter[] filters;
	private final int[] colors = new int[EnumColorFilter.values().length];
	private final boolean[] support = new boolean[EnumColorFilter.values().length];
	
	@SuppressWarnings("unqualified-field-access")
	EnumColorFormat(EnumColorFilter... f)
	{
		filters = f;
		
		EnumColorFilter color;
		
		for (int c = 0; c < f.length; c++)
		{
			color = f[c];
			
			colors[color.ordinal()] = c * 8;
			support[color.ordinal()] = true;
			
		}
		
	}
	
	public int getColorOffset(EnumColorFilter col)
	{
		return this.supports(col) ? this.colors[col.ordinal()] : -1;
	}
	
	public boolean supports(EnumColorFilter col)
	{
		return this.support[col.ordinal()];
	}
	
	public Color convert(Color old)
	{
		if (old.format == this)
		{
			return old;
		}
		
		Buffer<Byte> buf = new Buffer<Byte>();
		
		for (EnumColorFilter col : this.filters)
		{
			buf.add(old.getColor(col));
			
			
		}
		
		return new Color(this, new ByteBufWrapper(buf));
	}
	
}
