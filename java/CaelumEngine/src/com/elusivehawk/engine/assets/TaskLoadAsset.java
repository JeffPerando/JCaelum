
package com.elusivehawk.engine.assets;

import java.io.File;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.util.FileHelper;
import com.elusivehawk.util.task.Task;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TaskLoadAsset extends Task
{
	protected final String assetLoc;
	
	protected Asset fin = null;
	protected IAssetReceiver receiver;
	
	public TaskLoadAsset(String loc)
	{
		this(loc, null);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TaskLoadAsset(String loc, IAssetReceiver r)
	{
		super((t) ->
		{
			Asset a = ((TaskLoadAsset)t).getCompleteAsset();
			
			if (r != null)
			{
				r.onAssetLoaded(a);
				
			}
			
			CaelumEngine.assetManager().onAssetRead(a, r != null);
			
		});
		
		assert loc != null && !loc.isEmpty();
		
		assetLoc = loc;
		receiver = r;
		
	}
	
	@Override
	protected boolean finishTask() throws Throwable
	{
		AssetManager mgr = CaelumEngine.assetManager();
		
		if (mgr == null)
		{
			throw new NullPointerException("Asset manager not found! Aborting!!");
		}
		
		Asset a = mgr.getExistingAsset(this.assetLoc);
		
		if (a != null)
		{
			this.fin = a;
			
			return true;
		}
		
		File file = mgr.findFile(this.assetLoc);
		
		if (!FileHelper.canReadFile(file))
		{
			return false;
		}
		
		IAssetReader r = mgr.getReader(file);
		
		if (r == null)
		{
			return false;
		}
		
		a = r.readAsset(mgr, file);
		
		if (a == null)
		{
			return false;
		}
		
		this.fin = a;
		
		return true;
	}
	
	public Asset getCompleteAsset()
	{
		return this.fin;
	}
	
}
