
package com.elusivehawk.engine.assets;

import com.elusivehawk.engine.render.RenderHelper;
import com.elusivehawk.engine.render.old.RenderTask;
import com.elusivehawk.engine.render.opengl.IGLDeletable;
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
	protected volatile boolean loaded = false;
	
	public GraphicAsset(String path)
	{
		super(path);
		
	}
	
	@Override
	public void onTaskComplete(Task task)
	{
		if (task instanceof RenderTask)//TODO Remove this come OpenGL NG
		{
			RenderHelper.renderContext().registerCleanable(this);
			
		}
		
	}
	
	public boolean isLoaded()
	{
		return this.loaded && this.isRead();
	}
	
}
