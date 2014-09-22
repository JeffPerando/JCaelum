
package com.elusivehawk.engine.assets;

import java.io.BufferedInputStream;
import java.io.InputStream;
import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.util.EnumLogType;
import com.elusivehawk.util.Internal;
import com.elusivehawk.util.Logger;
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
		
		Asset a = mgr.getExistingAsset(this.asset.filepath, this.asset.type);
		
		if (a != null)
		{
			this.fin = a;
			
			return true;
		}
		
		InputStream is = mgr.getStream(this.asset.filepath);
		
		if (is == null)
		{
			Logger.log().log(EnumLogType.WARN, "Asset stream for \"%s\" cannot be null!", this.asset.filepath);
			
			return false;
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
	
	@Override
	public boolean doTryAgain()
	{
		return false;
	}
	
	public Asset getCompletedAsset()
	{
		return this.fin;
	}
	
}
