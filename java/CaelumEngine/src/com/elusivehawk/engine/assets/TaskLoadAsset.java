
package com.elusivehawk.engine.assets;

import java.io.File;
import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.util.FileHelper;
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
		super((t) ->
		{
			Asset asset = ((TaskLoadAsset)t).getCompleteAsset();
			
			if (asset != null)
			{
				CaelumEngine.assetManager().onAssetRead(a);
				
			}
			
		});
		
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
		
		Asset a = mgr.getExistingAsset(this.asset.filepath);
		
		if (a != null)
		{
			this.fin = a;
			
			return true;
		}
		
		File file = mgr.findFile(this.asset.filepath);
		
		if (!FileHelper.canReadFile(file))
		{
			return false;
		}
		
		if (this.asset.read(file))
		{
			this.fin = this.asset;
			
			return true;
		}
		
		return false;
	}
	
	public Asset getCompleteAsset()
	{
		return this.fin;
	}
	
}
