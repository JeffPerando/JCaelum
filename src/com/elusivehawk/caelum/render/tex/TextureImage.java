
package com.elusivehawk.caelum.render.tex;

import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderHelper;
import com.elusivehawk.caelum.render.gl.GL1;
import com.elusivehawk.caelum.render.gl.GLEnumTexture;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TextureImage implements ITexture
{
	private final GLEnumTexture type;
	private final ILegibleImage image;
	private int tex = 0;
	
	public TextureImage(ILegibleImage img)
	{
		this(img instanceof CubeGrid ? GLEnumTexture.GL_TEXTURE_3D : GLEnumTexture.GL_TEXTURE_2D, img);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TextureImage(GLEnumTexture texType, ILegibleImage img)
	{
		assert img != null;
		
		type = texType;
		image = img;
		
	}
	
	@Override
	public void preRender(RenderContext rcon)
	{
		if (this.tex == 0)
		{
			this.tex = RenderHelper.genTexture(this.type, this.image);
			
			rcon.registerDeletable(this);
			
		}
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		if (this.tex != 0)
		{
			GL1.glDeleteTextures(this.tex);
			
		}
		
	}
	
	@Override
	public int getTexId()
	{
		return this.tex;
	}
	
	@Override
	public boolean isStatic()
	{
		return true;
	}
	
}
