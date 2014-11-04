
package com.elusivehawk.caelum.assets;

import java.io.DataInputStream;
import java.io.InputStream;
import com.elusivehawk.caelum.CaelumEngine;
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
	protected boolean duplicate = false;
	
	@SuppressWarnings("unqualified-field-access")
	public TaskLoadAsset(Asset a)
	{
		super(CaelumEngine.assets());
		
		asset = a;
		
	}
	
	@Override
	protected boolean finishTask() throws Throwable
	{
		AssetManager mgr = CaelumEngine.assets();
		
		Asset a = mgr.getExistingAsset(this.asset.filepath, this.asset.type);
		
		if (a != null)
		{
			this.asset.onExistingAssetFound(a);
			
			this.fin = a;
			this.duplicate = true;
			
			return true;
		}
		
		InputStream is = mgr.getIn(this.asset.filepath);
		
		if (is == null)
		{
			Logger.log().log(EnumLogType.WARN, "Asset stream for \"%s\" cannot be null!", this.asset.filepath);
			
			return false;
		}
		
		DataInputStream in = new DataInputStream(is);
		
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
	
	public boolean foundDuplicate()
	{
		return this.duplicate;
	}
	
}
