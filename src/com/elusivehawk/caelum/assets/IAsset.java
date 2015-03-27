
package com.elusivehawk.caelum.assets;

import java.io.DataInputStream;
import com.elusivehawk.caelum.IDisposable;

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
	
	void onDuplicateFound(IAsset asset);
	
	default IAsset readNow()
	{
		return this.readAsset(true);
	}
	
	default IAsset readLater()
	{
		return this.readAsset(true);
	}
	
	default IAsset readAsset(boolean now)
	{
		if (now)
		{
			//CaelumEngine.assets().readAsset(this);
			
		}
		else
		{
			//CaelumEngine.tasks().scheduleTask(new TaskLoadAsset(this));
			
		}
		
		return this;
	}
	
}
