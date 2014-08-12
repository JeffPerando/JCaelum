
package com.elusivehawk.engine.assets;

import com.elusivehawk.util.task.ITaskListener;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class GraphicAsset extends Asset implements ITaskListener
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
	
}
