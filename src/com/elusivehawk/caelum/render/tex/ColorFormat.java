
package com.elusivehawk.caelum.render.tex;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum ColorFormat
{
	RGBA(ColorFilter.RED, ColorFilter.GREEN, ColorFilter.BLUE, ColorFilter.ALPHA),
	ARGB(ColorFilter.ALPHA, ColorFilter.RED, ColorFilter.GREEN, ColorFilter.BLUE),
	ABGR(ColorFilter.ALPHA, ColorFilter.BLUE, ColorFilter.GREEN, ColorFilter.RED),
	BGRA(ColorFilter.BLUE, ColorFilter.GREEN, ColorFilter.RED, ColorFilter.ALPHA);
	
	public final ColorFilter[] filters;
	private final int[] colors = new int[ColorFilter.values().length];
	private final boolean[] support = new boolean[ColorFilter.values().length];
	
	@SuppressWarnings("unqualified-field-access")
	ColorFormat(ColorFilter... f)
	{
		filters = f;
		
		ColorFilter color;
		
		for (int c = 0; c < f.length; c++)
		{
			color = f[c];
			
			colors[color.ordinal()] = c * 8;
			support[color.ordinal()] = true;
			
		}
		
	}
	
	public int getColorOffset(ColorFilter col)
	{
		return this.supports(col) ? this.colors[col.ordinal()] : -1;
	}
	
	public int filterCount()
	{
		return this.filters.length;
	}
	
	public boolean supports(ColorFilter col)
	{
		return this.support[col.ordinal()];
	}
	
	public Color convert(Color old)
	{
		if (old.format == this)
		{
			return old;
		}
		
		Color ret = new Color(this);
		
		for (ColorFilter cf : this.filters)
		{
			if (!old.format.supports(cf))
			{
				continue;
			}
			
			ret.setColor(cf, old.getColor(cf));
			
		}
		
		return ret;
	}
	
}
