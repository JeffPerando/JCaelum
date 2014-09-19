
package com.elusivehawk.engine.assets;

import java.io.BufferedInputStream;
import java.io.InputStream;
import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.util.Internal;
import com.elusivehawk.util.task.Task;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Internal
public class TaskLoadAsset extends Task
{
	protected final Asset asset;
	
	protected Asset fin = null;
	
	@SuppressWarnings("unqualified-field-access")
	public TaskLoadAsset(Asset a)
	{
		super(CaelumEngine.assetManager());
		
		asset = a;
		
	}
	
	@Override
	protected boolean finishTask() throws Throwable
	{
		AssetManager mgr = CaelumEngine.assetManager();
		
		if (mgr == null)
		{
			throw new NullPointerException("Asset manager not found! Aborting!!");
		}
		
		if (!mgr.isLoaded())
		{
			while (!mgr.isLoaded())
			{
				try
				{
					Thread.sleep(1);
					
				}
				catch (Exception e){}
				
			}
			
		}
		
		Asset a = mgr.getExistingAsset(this.asset.filepath, this.asset.type);
		
		if (a != null)
		{
			this.fin = a;
			
			return true;
		}
		
		InputStream is = mgr.getStream(this.asset.filepath);
		
		if (is == null)
		{
			throw new NullPointerException(String.format("Asset stream for \"%s\" cannot be null!", this.asset.filepath));
		}
		
		BufferedInputStream in = new BufferedInputStream(is);
		
		boolean ret = this.asset.read(in);
		
		in.close();
		
		if (ret)
		{
			this.fin = this.asset;
			
		}
		
		return ret;
	}
	
	public Asset getCompletedAsset()
	{
		return this.fin;
	}
	
}
