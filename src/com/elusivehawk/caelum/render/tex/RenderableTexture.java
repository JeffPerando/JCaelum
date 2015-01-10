
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
	public boolean render(RenderContext rcon) throws RenderException
	{
		boolean ret = false;
		
		if (!this.rendered)
		{
			if (this.fbo.bind(rcon))
			{
				try
				{
					ret = this.renderTexture(rcon);
					
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
		
		return ret;
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
	
	public abstract boolean renderTexture(RenderContext rcon) throws RenderException;
	
}
