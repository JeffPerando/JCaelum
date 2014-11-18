
package com.elusivehawk.caelum.render.tex;

import com.elusivehawk.caelum.render.IRenderable;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderException;
import com.elusivehawk.caelum.render.gl.GLFramebuffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class RenderableTexture implements ITexture, IRenderable
{
	private final GLFramebuffer fbo;
	
	private boolean rendered = false;
	
	public RenderableTexture()
	{
		this(false);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public RenderableTexture(boolean depth)
	{
		fbo = new GLFramebuffer(depth);
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		this.fbo.delete(rcon);
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		this.rendered = false;
		
	}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
		if (!this.rendered)
		{
			if (this.fbo.bind(rcon))
			{
				this.renderTexture(rcon);
				
				this.fbo.unbind(rcon);
				
				this.rendered = true;
				
			}
			
		}
		
	}
	
	@Override
	public int getTexId()
	{
		return this.fbo.getTexture();
	}
	
	@Override
	public boolean isStatic()
	{
		return false;
	}
	
	public abstract void renderTexture(RenderContext rcon) throws RenderException;
	
}
