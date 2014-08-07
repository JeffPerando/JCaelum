
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.assets.Texture;
import com.elusivehawk.util.math.MathHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Material
{
	public final Texture tex;
	public final float shininess;
	public final Color filter;
	
	public Material(Color overlay)
	{
		this(null, 0.0f, overlay);
	}
	
	public Material(Texture texture)
	{
		this(texture, 0.0f, new Color(EnumColorFormat.RGBA));
		
	}
	
	public Material(Texture texture, Color overlay)
	{
		this(texture, 0.0f, overlay);
		
	}
	
	public Material(Texture texture, float shine)
	{
		this(texture, shine, new Color(EnumColorFormat.RGBA));
		
	}
	
	public Material(Material m)
	{
		this(m.tex, m.shininess, m.filter);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Material(Texture texture, float shine, Color overlay)
	{
		assert overlay != null;
		
		tex = texture;
		shininess = MathHelper.clamp(shine, 0f, 1f);
		filter = EnumColorFormat.RGBA.convert(overlay);
		
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
