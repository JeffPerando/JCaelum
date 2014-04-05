
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
	public final Texture tex;
	public final float shininess;
	public final Color filter;
	
	public Material(String filename, Color overlay)
	{
		this(filename, null, 0.0f, overlay);
	}
	
	public Material(String filename, Texture texture)
	{
		this(filename, texture, 0.0f, new Color(EnumColorFormat.RGBA));
		
	}
	
	public Material(String filename, Texture texture, Color overlay)
	{
		this(filename, texture, 0.0f, overlay);
		
	}
	
	public Material(String filename, Texture texture, float shine)
	{
		this(filename, texture, shine, new Color(EnumColorFormat.RGBA));
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Material(String filename, Texture texture, float shine, Color overlay)
	{
		super(filename);
		
		assert overlay != null;
		
		tex = texture;
		shininess = MathHelper.clamp(shine, 0f, 1f);
		filter = EnumColorFormat.RGBA.convert(overlay);
		
	}
	
	@Override
	protected boolean finishAsset()
	{
		if (this.tex == null || this.tex.isFinished())
		{
			return true;
		}
		
		this.tex.finish();
		
		return this.tex.isFinished();
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
