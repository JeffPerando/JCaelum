
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.engine.assets.Asset;
import com.elusivehawk.engine.assets.EnumAssetType;
import com.elusivehawk.engine.render.gl.IGLDeletable;
import com.elusivehawk.engine.render.old.RenderTask;
import com.elusivehawk.util.task.ITaskListener;
import com.elusivehawk.util.task.Task;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class GraphicAsset extends Asset implements ITaskListener, IGLDeletable
{
	protected volatile boolean loaded = false, registered = false;
	
	public GraphicAsset(String path, EnumAssetType aType)
	{
		super(path, aType);
		
	}
	
	@Override
	public void onTaskComplete(Task task)
	{
		if (!this.registered && task instanceof RenderTask)//TODO Remove this come OpenGL NG
		{
			RenderContext rcon = CaelumEngine.renderContext();
			
			rcon.registerCleanable(this);
			this.finishGPULoading(rcon);
			
			this.registered = true;
			
		}
		
	}
	
	@Override
	public void onExistingAssetFound(Asset a)
	{
		super.onExistingAssetFound(a);
		
		if (a instanceof GraphicAsset)
		{
			GraphicAsset g = (GraphicAsset)a;
			
			this.registered = g.registered;
			this.loaded = g.loaded;
			
		}
		
	}
	
	public boolean isLoaded()
	{
		return this.loaded && this.isRead();
	}
	
	protected abstract void finishGPULoading(RenderContext rcon);
	
}
