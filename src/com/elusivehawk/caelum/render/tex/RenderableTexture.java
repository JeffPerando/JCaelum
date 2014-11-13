
package com.elusivehawk.caelum.render.tex;

import com.elusivehawk.caelum.CaelumEngine;
import com.elusivehawk.caelum.render.IDisplay;
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
		this(CaelumEngine.defaultDisplay());
		
	}
	
	public RenderableTexture(boolean depth)
	{
		this(CaelumEngine.defaultDisplay(), depth);
		
	}
	
	public RenderableTexture(IDisplay display)
	{
		this(display, true);
		
	}
	
	public RenderableTexture(IDisplay display, boolean depth)
	{
		this(display.getWidth(), display.getHeight(), depth);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public RenderableTexture(int width, int height, boolean depth)
	{
		fbo = new GLFramebuffer(width, height, depth);
		
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
