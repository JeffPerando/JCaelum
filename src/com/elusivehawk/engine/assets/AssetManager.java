
package com.elusivehawk.engine.assets;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.elusivehawk.engine.CaelumException;
import com.elusivehawk.util.FileHelper;
import com.elusivehawk.util.io.IStreamProvider;
import com.elusivehawk.util.storage.SyncList;
import com.elusivehawk.util.task.ITaskListener;
import com.elusivehawk.util.task.Task;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class AssetManager implements ITaskListener
{
	protected final List<IAssetReceiver> receivers = new SyncList<IAssetReceiver>();
	
	protected final Map<EnumAssetType, List<Asset>> assets = Maps.newHashMap();
	
	protected volatile IStreamProvider sProvider = ((path) -> {return FileHelper.getResourceStream(path);});
	
	public AssetManager(){}
	
	@Override
	public void onTaskComplete(Task task)
	{
		if (!(task instanceof TaskLoadAsset))
		{
			return;
		}
		
		TaskLoadAsset t = (TaskLoadAsset)task;
		
		if (t.foundDuplicate())
		{
			return;
		}
		
		Asset a = t.getCompletedAsset();
		
		if (a == null)
		{
			throw new NullPointerException("Asset is null!");
		}
		
		List<Asset> assetList = this.assets.get(a.type);
		
		if (assetList == null)
		{
			assetList = SyncList.newList();
			
			this.assets.put(a.type, assetList);
			
		}
		
		if (assetList.contains(a))
		{
			throw new CaelumException("Duplicate asset entry %s!", a);
		}
		
		assetList.add(a);
		
		Iterator<IAssetReceiver> itr = this.receivers.iterator();
		
		while (itr.hasNext())
		{
			itr.next().onAssetLoaded(a);
			
		}
		
	}
	
	public void setStreamProvider(IStreamProvider sp)
	{
		assert sp != null;
		
		this.sProvider = sp;
		
	}
	
	public void addAssetReceiver(IAssetReceiver r)
	{
		this.receivers.add(r);
		
	}
	
	public void removeAssetReceiver(IAssetReceiver r)
	{
		this.receivers.remove(r);
		
	}
	
	public void dropAsset(Asset a)
	{
		if (a == null)
		{
			throw new NullPointerException();
		}
		
		this.assets.remove(a);
	}
	
	protected Asset getExistingAsset(String filename, EnumAssetType type)
	{
		if (!this.assets.isEmpty())
		{
			List<Asset> assetList = this.assets.get(type);
			
			if (assetList == null)
			{
				return null;
			}
			
			for (Asset a : assetList)
			{
				if (filename.endsWith(a.filepath))
				{
					return a;
				}
				
			}
			
		}
		
		return null;
	}
	
	protected InputStream getStream(String loc)
	{
		return this.sProvider.getInStream(loc);
	}
	
}
