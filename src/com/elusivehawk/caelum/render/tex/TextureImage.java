
package com.elusivehawk.caelum.render.tex;

import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TextureImage implements ITexture
{
	protected final ILegibleImage img;
	protected int tex = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public TextureImage(ILegibleImage image)
	{
		assert image != null;
		
		img = image;
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		if (this.tex == 0)
		{
			this.tex = RenderHelper.genTexture(rcon, this.img);
			
			rcon.registerCleanable(this);
			
		}
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		if (this.tex != 0)
		{
			rcon.getGL1().glDeleteTextures(this.tex);
			
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
