
package com.elusivehawk.engine.assets;

import com.elusivehawk.engine.render.RenderContext;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class GraphicAsset extends Asset
{
	protected boolean loaded = false;
	
	public GraphicAsset(String path)
	{
		super(path);
		
	}
	
	public boolean isLoaded()
	{
		return this.loaded && this.isRead();
	}
	
	public boolean loadIntoGPU(RenderContext rcon)
	{
		if (!this.isRead())
		{
			return false;
		}
		
		if (!this.loaded)
		{
			return this.loaded = this.loadAssetIntoGPU(rcon);
		}
		
		return true;
	}
	
	protected abstract boolean loadAssetIntoGPU(RenderContext rcon);
	
}
