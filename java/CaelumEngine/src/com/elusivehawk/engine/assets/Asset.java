
package com.elusivehawk.engine.assets;

import java.io.BufferedInputStream;
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
	
	@Override
	public String toString()
	{
		return this.filepath;
	}
	
	public final boolean isRead()
	{
		return this.read;
	}
	
	public final boolean read(BufferedInputStream in) throws Throwable
	{
		return this.read ? true : (this.read = this.readAsset(in));
	}
	
	protected abstract boolean readAsset(BufferedInputStream in) throws Throwable;
	
	public void onRead(){}
	
}
