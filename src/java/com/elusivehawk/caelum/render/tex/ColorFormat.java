
package com.elusivehawk.caelum.render.tex;

import static com.elusivehawk.caelum.render.tex.ColorFilter.ALPHA;
import static com.elusivehawk.caelum.render.tex.ColorFilter.BLUE;
import static com.elusivehawk.caelum.render.tex.ColorFilter.GREEN;
import static com.elusivehawk.caelum.render.tex.ColorFilter.RED;
import java.util.Map;
import com.elusivehawk.caelum.render.gl.GLConst;
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
	RGB(GLConst.GL_RGB, RED, GREEN, BLUE),
	RGBA(GLConst.GL_RGBA, RED, GREEN, BLUE, ALPHA),
	ARGB(GLConst.GL_RGB, ALPHA, RED, GREEN, BLUE),
	ABGR(GLConst.GL_BGR, ALPHA, BLUE, GREEN, RED),
	BGR(GLConst.GL_BGR, BLUE, GREEN, RED),
	BGRA(GLConst.GL_BGRA, BLUE, GREEN, RED, ALPHA);
	
	public final ColorFilter[] filters;
	public final int glFormat;
	
	private final Map<ColorFilter, Bitmask> bitmasks = Maps.newHashMap();
	
	@SuppressWarnings("unqualified-field-access")
	ColorFormat(int gl, ColorFilter... fs)
	{
		glFormat = gl;
		filters = fs;
		
		for (int c = 0; c < fs.length; c++)
		{
			bitmasks.put(fs[c], new Bitmask(0xFF, c * 8));
			
		}
		
	}
	
	@Override
	public String toString()
	{
		return this.name().toLowerCase();
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
	
}
