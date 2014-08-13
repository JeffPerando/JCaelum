
package com.elusivehawk.engine.assets;

import com.elusivehawk.engine.render.opengl.IGLDeletable;
import com.elusivehawk.util.storage.SyncStorage;
import com.elusivehawk.util.task.ITaskListener;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class GraphicAsset extends Asset implements ITaskListener, IGLDeletable
{
	protected SyncStorage<Boolean> loaded = new SyncStorage<Boolean>(false);
	
	public GraphicAsset(String path)
	{
		super(path);
		
	}
	
	public boolean isLoaded()
	{
		return this.loaded.get() && this.isRead();
	}
	
}
