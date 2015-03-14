
package com.elusivehawk.caelum.render.tex;

import com.elusivehawk.caelum.render.IRenderer;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderException;
import com.elusivehawk.caelum.render.gl.GLFramebuffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class RenderableTexture implements IRenderer, ITexture
{
	private final GLFramebuffer fbo;
	
	private RenderContext boundRcon = null;
	private boolean rendered = false, initiated = false;
	
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
	public void dispose(Object... args)
	{
		if (this.initiated)
		{
			this.boundRcon.scheduleDeletion(this);
			
		}
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		this.fbo.delete(rcon);
		
	}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
		if (this.rendered)
		{
			return;
		}
		
		if (!this.initiated)
		{
			rcon.scheduleDeletion(this);
			
			this.boundRcon = rcon;
			
			this.initiated = true;
			
		}
		
		if (this.fbo.bind(rcon))
		{
			try
			{
				this.renderTexture(rcon);
				
			}
			catch (RenderException e)
			{
				throw e;
			}
			finally
			{
				this.fbo.unbind(rcon);
				
			}
			
			this.rendered = true;
			
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
	
	@Override
	public void postRender(RenderContext rcon)
	{
		this.rendered = false;
		
	}
	
	public abstract void renderTexture(RenderContext rcon) throws RenderException;
	
}
