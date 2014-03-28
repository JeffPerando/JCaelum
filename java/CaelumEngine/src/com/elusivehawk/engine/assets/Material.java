
package com.elusivehawk.engine.assets;

import com.elusivehawk.engine.math.MathHelper;
import com.elusivehawk.engine.render.Color;
import com.elusivehawk.engine.render.EnumColorFormat;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Material extends Asset
{
	protected final Asset tex;
	protected final float shininess;
	protected final Color filter;
	protected final int[] ids = new int[3];
	
	public Material(String filename, Color overlay)
	{
		this(filename, null, 0.0f, overlay);
	}
	
	public Material(String filename, Asset texture)
	{
		this(filename, texture, 0.0f, new Color(EnumColorFormat.RGBA));
		
	}
	
	public Material(String filename, Asset texture, Color overlay)
	{
		this(filename, texture, 0.0f, overlay);
		
	}
	
	public Material(String filename, Asset texture, float shine)
	{
		this(filename, texture, shine, new Color(EnumColorFormat.RGBA));
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Material(String filename, Asset texture, float shine, Color overlay)
	{
		super(filename, EnumAssetType.MATERIAL);
		
		assert overlay != null;
		assert texture.type == EnumAssetType.TEXTURE;
		
		tex = texture;
		shininess = MathHelper.clamp(shine, 0f, 1f);
		filter = EnumColorFormat.RGBA.convert(overlay);
		
		ids[0] = tex.getIds()[0];
		ids[1] = Float.floatToIntBits(shininess);
		ids[2] = filter.color;
		
	}
	
	@Override
	public int[] getIds()
	{
		if (this.tex.getIds()[1] == 1)
		{
			this.ids[0] = this.tex.getIds()[0];
			
		}
		
		return this.ids;
	}
	
	@Override
	protected boolean finishAsset()
	{
		return true;
	}
	
	/*
	@Override
	public void updateUniforms(RenderContext context){}
	
	@Override
	public void manipulateUniforms(RenderContext context, GLProgram p)
	{
		p.attachUniform("mat.tex", BufferHelper.makeIntBuffer(this.tex.getIds()[0]), GLProgram.EnumUniformType.ONE);
		p.attachUniform("mat.color", this.filter.asBufferF(), GLProgram.EnumUniformType.FOUR);
		p.attachUniform("mat.shininess", BufferHelper.makeFloatBuffer(this.shininess), GLProgram.EnumUniformType.ONE);
		
	}
	
	@Override
	public void postRender(){}
	*/
	
}
