
package com.elusivehawk.engine.assets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
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
		
		Asset a = mgr.getExistingAsset(this.asset.filepath, this.asset.type);
		
		if (a != null)
		{
			this.fin = a;
			
			return true;
		}
		
		File file = mgr.getFile(this.asset.filepath);
		
		if (!FileHelper.canRead(file))
		{
			return false;
		}
		
		FileInputStream fis = FileHelper.createInStream(file);
		
		if (fis == null)
		{
			return false;
		}
		
		if (this.asset.read(new BufferedInputStream(fis)))
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
