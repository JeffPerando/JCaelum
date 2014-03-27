
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
public class Material implements Asset
{
	protected final Asset tex;
	protected final float shininess;
	protected final Color filter;
	protected final int[] ints = new int[3];
	
	public Material(Color overlay)
	{
		this(null, 0.0f, overlay);
	}
	
	public Material(Asset texture)
	{
		this(texture, 0.0f, new Color(EnumColorFormat.RGBA));
		
	}
	
	public Material(Asset texture, Color overlay)
	{
		this(texture, 0.0f, overlay);
		
	}
	
	public Material(Asset texture, float shine)
	{
		this(texture, shine, new Color(EnumColorFormat.RGBA));
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Material(Asset texture, float shine, Color overlay)
	{
		assert overlay != null;
		assert texture.getType() == EnumAssetType.TEXTURE;
		
		tex = texture;
		shininess = MathHelper.clamp(shine, 0f, 1f);
		filter = EnumColorFormat.RGBA.convert(overlay);
		
		ints[0] = tex.getIds()[0];
		ints[1] = Float.floatToIntBits(shininess);
		ints[2] = filter.color;
		
	}
	
	@Override
	public EnumAssetType getType()
	{
		return EnumAssetType.MATERIAL;
	}
	
	@Override
	public int[] getIds()
	{
		if (this.tex.getIds()[1] == 1)
		{
			this.ints[0] = this.tex.getIds()[0];
			
		}
		
		return this.ints;
	}
	
	@Override
	public boolean isFinished()
	{
		return true;
	}
	
	@Override
	public void finish(){}
	
	@Override
	public Object getAttachment()
	{
		return null;
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
