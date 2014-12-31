
package com.elusivehawk.caelum.render.tex;

import java.util.Map;
import com.elusivehawk.util.storage.Bitmask;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum ColorFormat
{
	RGB(ColorFilter.RED, ColorFilter.GREEN, ColorFilter.BLUE),
	RGBA(ColorFilter.RED, ColorFilter.GREEN, ColorFilter.BLUE, ColorFilter.ALPHA),
	ARGB(ColorFilter.ALPHA, ColorFilter.RED, ColorFilter.GREEN, ColorFilter.BLUE),
	ABGR(ColorFilter.ALPHA, ColorFilter.BLUE, ColorFilter.GREEN, ColorFilter.RED),
	BGRA(ColorFilter.BLUE, ColorFilter.GREEN, ColorFilter.RED, ColorFilter.ALPHA);
	
	public final ColorFilter[] filters;
	private final Map<ColorFilter, Bitmask> bitmasks = Maps.newHashMap();
	
	@SuppressWarnings("unqualified-field-access")
	ColorFormat(ColorFilter... f)
	{
		filters = f;
		
		int limit = f.length - 1 * 8;
		
		for (int c = 0; c < f.length; c++)
		{
			bitmasks.put(f[c], new Bitmask(0xFF, c * 8 - limit));
			
		}
		
	}
	
	public Bitmask getBitmask(ColorFilter cf)
	{
		return this.bitmasks.get(cf);
	}
	
	public int filterCount()
	{
		return this.filters.length;
	}
	
	public boolean supports(ColorFilter cf)
	{
		return this.bitmasks.containsKey(cf);
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
