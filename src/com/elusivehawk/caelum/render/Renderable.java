
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.IDisposable;
import com.elusivehawk.caelum.render.tex.TextureBinder;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Renderable implements IDisposable, IRenderer
{
	private boolean initiated = false, preRendered = false;
	private int recursiveRenders = 0;
	
	@Override
	public void preRender(RenderContext rcon) throws RenderException
	{
		if (this.preRendered)
		{
			throw new RenderException("Already pre-rendered!");
		}
		
		if (!this.initiated)
		{
			if (this.initiate(rcon))
			{
				this.initiated = true;
				
			}
			else
			{
				return;
			}
			
		}
		
		this.preRenderImpl(rcon);
		
		this.preRendered = true;
		
	}
	
	@Override
	public void postRender(RenderContext rcon) throws RenderException
	{
		this.preRendered = false;
		
		this.postRenderImpl(rcon);
		
	}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
		if (!this.preRendered)
		{
			return;
		}
		
		if (this.isCulled(rcon))
		{
			return;
		}
		
		if (this.recursiveRenders == RenderConst.RECURSIVE_LIMIT)
		{
			return;
		}
		
		this.recursiveRenders++;
		
		this.renderImpl(rcon);
		
		TextureBinder.instance().releaseTextures();
		
		this.recursiveRenders--;
		
	}
	
	public boolean isCulled(RenderContext rcon)
	{
		return false;
	}
	
	protected abstract boolean initiate(RenderContext rcon);
	
	protected abstract void preRenderImpl(RenderContext rcon) throws RenderException;
	
	protected abstract void renderImpl(RenderContext rcon) throws RenderException;
	
	protected abstract void postRenderImpl(RenderContext rcon) throws RenderException;
	
}
