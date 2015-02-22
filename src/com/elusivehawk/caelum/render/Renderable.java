
package com.elusivehawk.caelum.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Renderable implements IDeletable, IPostRenderer, IPreRenderer
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
		
		this.preRendered = true;
		
	}
	
	@Override
	public void postRender(RenderContext rcon) throws RenderException
	{
		this.preRendered = false;
		
	}
	
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
		
		rcon.releaseTextures();
		
		this.recursiveRenders--;
		
	}
	
	public void render(RenderContext rcon, Camera cam) throws RenderException
	{
		Camera cam_tmp = rcon.getCamera();
		
		rcon.setCamera(cam);
		
		try
		{
			this.render(rcon);
			
		}
		catch (RenderException e)
		{
			throw e;
		}
		finally
		{
			rcon.setCamera(cam_tmp);
			
		}
		
	}
	
	public boolean isCulled(RenderContext rcon)
	{
		return false;
	}
	
	protected boolean initiate(RenderContext rcon)
	{
		return true;
	}
	
	protected abstract void renderImpl(RenderContext rcon) throws RenderException;
	
}
