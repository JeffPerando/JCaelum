
package com.elusivehawk.caelum.render.tex;

import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.Deletables;
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
public class Texture implements ITexture
{
	private final GLEnumTexture type;
	
	protected ILegibleImage[] sources = null;
	protected int[] frames = null;
	
	protected int frame = 0, id = 0;
	
	private boolean initiated = false, deleted = false;
	
	public Texture()
	{
		this(GLEnumTexture.GL_TEXTURE_2D);
		
	}
	
	public Texture(GLEnumTexture textype)
	{
		this(textype, (ILegibleImage[])null);
		
	}
	
	public Texture(ILegibleImage... imgs)
	{
		this(GLEnumTexture.GL_TEXTURE_2D, imgs);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Texture(GLEnumTexture textype, ILegibleImage... imgs)
	{
		assert textype != null;
		
		type = textype;
		sources = imgs;
		
	}
	
	public int getFrameCount()
	{
		return this.initiated ? 0 : this.frames.length;
	}
	
	@Override
	public void preRender(RenderContext rcon) throws RenderException
	{
		if (!this.initiated)
		{
			if (this.sources == null)
			{
				return;
			}
			
			this.frames = new int[this.sources.length];
			
			Deletables.instance().register(this);
			
			this.initiated = true;
			
		}
		
		if (this.frames.length > 0)
		{
			this.id = this.frames[this.frame];
			
			if (this.id == 0)
			{
				this.id = this.frames[this.frame] = RenderHelper.genTexture(this.sources[this.frame]);
				
				this.sources[this.frame] = null;
				
			}
			
			this.frame++;
			
			if (this.frame == this.frames.length)
			{
				this.frame = 0;
				
			}
			
		}
		
	}
	
	@Override
	public void delete()
	{
		if (!this.initiated)
		{
			return;
		}
		
		if (this.deleted)
		{
			return;
		}
		
		GL1.glDeleteTextures(this.frames);
		
		this.deleted = true;
		
	}
	
	@Override
	public GLEnumTexture getType()
	{
		return this.type;
	}
	
	@Override
	public int getId()
	{
		return this.id;
	}
	
	@Override
	public boolean isStatic()
	{
		return this.getFrameCount() < 2;
	}
	
}
