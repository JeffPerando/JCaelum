
package com.elusivehawk.engine.assets;

import java.io.File;
import com.elusivehawk.engine.CaelumEngine;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Asset
{
	public final String filepath;
	
	private boolean read = false;
	
	@SuppressWarnings("unqualified-field-access")
	protected Asset(String path)
	{
		filepath = path;
		
		CaelumEngine.tasks().scheduleTask(new TaskLoadAsset(this));
		
	}
	
	public final boolean isRead()
	{
		return this.read;
	}
	
	public final boolean read(File file) throws Throwable
	{
		return this.read ? true : (this.read = this.readAsset(file));
	}
	
	protected abstract boolean readAsset(File asset) throws Throwable;
	
	public void onRead(){}
	
}
