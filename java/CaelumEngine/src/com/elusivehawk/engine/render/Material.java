
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.ITexture;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Material
{
	protected final ITexture tex;
	protected float reflectivity;
	protected Color filter;
	
	public Material(Color overlay)
	{
		this(null, 0.0f, overlay);
	}
	
	public Material(ITexture texture)
	{
		this(texture, 0.0f, new Color(EnumColorFormat.RGBA));
		
	}
	
	public Material(ITexture texture, Color overlay)
	{
		this(texture, 0.0f, overlay);
		
	}
	
	public Material(ITexture texture, float shine)
	{
		this(texture, shine, new Color(EnumColorFormat.RGBA));
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Material(ITexture texture, float shine, Color overlay)
	{
		assert overlay != null;
		
		tex = texture;
		reflectivity = shine;
		filter = overlay;
		
	}
	
	public void update(GLProgram p, RenderContext context)
	{
		
	}
	
}
