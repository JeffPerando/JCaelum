
package com.elusivehawk.engine.render;

import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;

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
	
	EnumColorFormat(EnumColorFilter... f)
	{
		colors = f;
		
		offsets = new int[f.length];
		
		boolean flag = false;
		
		for (int c = 0; c < f.length; c++)
		{
			offsets[colors[c].ordinal()] = 24 - (c * 8);
			
			if (colors[c] == EnumColorFilter.ALPHA)
			{
				flag = true;
				
			}
			
		}
		
		alpha = flag;
		
	}
	
	public final EnumColorFilter[] colors;
	private int[] offsets;
	private final boolean alpha;
	
	public int getColorOffset(EnumColorFilter col)
	{
		return this.offsets[col.ordinal()];
	}
	
	public Color convert(Color old)
	{
		if (old.format.ordinal() == this.ordinal())
		{
			return old;
		}
		
		ByteBuffer buf = BufferUtils.createByteBuffer(this.colors.length);
		
		for (EnumColorFilter col : this.colors)
		{
			buf.put(old.getColor(col));
			
		}
		
		return new Color(this, buf);
	}
	
	public boolean supportsAlpha()
	{
		return this.alpha;
	}
	
}
