
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.core.Asset;
import com.elusivehawk.engine.core.EnumAssetType;
import com.elusivehawk.engine.math.MathHelper;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.IGLManipulator;
import com.elusivehawk.engine.render.opengl.ITexture;
import com.elusivehawk.engine.util.BufferHelper;
import com.elusivehawk.engine.util.IDirty;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Material implements Asset, IGLManipulator
{
	protected final ITexture tex;
	protected final float shininess;
	protected final Color filter;
	
	protected final int[] ints = new int[3];
	protected final boolean isTexDirtable;
	
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
		shininess = MathHelper.clamp(shine, 0f, 1f);
		filter = EnumColorFormat.RGBA.convert(overlay);
		isTexDirtable = tex instanceof IDirty;
		
		ints[0] = tex.getTexture();
		ints[1] = Float.floatToIntBits(shininess);
		ints[2] = filter.color;
		
	}
	
	@Override
	public Asset get()
	{
		return this;
	}
	
	@Override
	public EnumAssetType getType()
	{
		return EnumAssetType.MATERIAL;
	}
	
	@Override
	public int[] getIds()
	{
		if (this.isTexDirtable && ((IDirty)this.tex).isDirty())
		{
			this.ints[0] = this.tex.getTexture();
			
		}
		
		return this.ints;
	}
	
	@Override
	public boolean isFinished()
	{
		return true;
	}
	
	@Override
	public void finish()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void manipulateUniforms(GLProgram p)
	{
		p.attachUniform("mat.tex", BufferHelper.makeIntBuffer(new int[]{this.tex.getTexture()}), GLProgram.EnumUniformType.ONE);
		p.attachUniform("mat.color", this.filter.asBufferF(), GLProgram.EnumUniformType.FOUR);
		p.attachUniform("mat.shininess", BufferHelper.makeFloatBuffer(this.shininess), GLProgram.EnumUniformType.ONE);
		
	}
	
}
