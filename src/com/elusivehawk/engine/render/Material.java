
package com.elusivehawk.engine.render;

import com.elusivehawk.util.math.MathHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Material
{
	public final ITexture tex;
	public final float shininess;
	public final Color filter;
	
	public Material(Color overlay)
	{
		this(null, 0.0f, overlay);
	}
	
	public Material(ITexture texture)
	{
		this(texture, 0.0f, new Color(ColorFormat.RGBA));
		
	}
	
	public Material(ITexture texture, Color overlay)
	{
		this(texture, 0.0f, overlay);
		
	}
	
	public Material(ITexture texture, float shine)
	{
		this(texture, shine, new Color(ColorFormat.RGBA));
		
	}
	
	public Material(Material m)
	{
		this(m.tex, m.shininess, m.filter);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Material(ITexture texture, float shine, Color overlay)
	{
		assert overlay != null;
		
		tex = texture;
		shininess = MathHelper.clamp(shine, 0f, 1f);
		filter = ColorFormat.RGBA.convert(overlay);
		
	}
	
	@Override
	public Material clone()
	{
		return new Material(this);
	}
	
	public boolean isStatic()
	{
		return this.tex == null ? true : !this.tex.isAnimated();
	}
	
}
