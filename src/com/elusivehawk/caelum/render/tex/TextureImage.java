
package com.elusivehawk.caelum.render.tex;

import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderException;
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
	private RenderContext boundRcon = null;
	private boolean initiated = false, deleted = false;
	
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
	public void preRender(RenderContext rcon) throws RenderException
	{
		if (this.deleted)
		{
			throw new RenderException("Cannot pre-render a deleted texture");
		}
		
		if (!this.initiated)
		{
			this.tex = RenderHelper.genTexture(this.type, this.image);
			
			rcon.registerDeletable(this);
			
			this.boundRcon = rcon;
			
			this.initiated = true;
			
		}
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		GL1.glDeleteTextures(this.tex);
		
	}
	
	@Override
	public void dispose(Object... args)
	{
		if (!this.initiated)
		{
			return;
		}
		
		if (this.deleted)
		{
			return;
		}
		
		this.boundRcon.scheduleDeletion(this);
		
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
