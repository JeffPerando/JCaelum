
package com.elusivehawk.caelum.assets;

import java.io.DataInputStream;
import com.elusivehawk.caelum.CaelumEngine;
import com.elusivehawk.caelum.IDisposable;
import com.elusivehawk.util.Logger;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IAsset extends IDisposable
{
	String getLocation();
	
	boolean isRead();
	
	void read(DataInputStream in) throws Throwable;
	
	void onRead();
	
	void onDuplicateFound(IAsset asset);
	
	default IAsset readNow()
	{
		return this.readAsset(true);
	}
	
	default IAsset readLater()
	{
		return this.readAsset(false);
	}
	
	default IAsset readAsset(boolean now)
	{
		if (now)
		{
			try
			{
				CaelumEngine.assets().readAsset(this);
			}
			catch (Throwable e)
			{
				Logger.err(e);
				
			}
			
		}
		else
		{
			CaelumEngine.tasks().scheduleTask(new TaskLoadAsset(this));
			
		}
		
		return this;
	}
	
}
