
package com.elusivehawk.caelum.assets;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.util.FileHelper;
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
public final class AssetManager implements ITaskListener
{
	private final List<IAssetReceiver> receivers = new SyncList<IAssetReceiver>();
	private final Map<EnumAssetType, List<Asset>> assets = Maps.newHashMap();
	private final Map<String, IAssetReader> readers = Maps.newHashMap();
	
	private IAssetStreamer sProvider = ((path) -> {return new DataInputStream(FileHelper.getResourceStream(path));});
	
	public AssetManager()
	{
		setReader("gif", new GIFReader());
		setReader("png", new PNGReader());
		
	}
	
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
	
	public Object readObjectForAsset(Asset a, DataInputStream in) throws Throwable
	{
		IAssetReader ar = this.getReader(a.ext);
		
		Object ret = null;
		
		try
		{
			ret = ar.readAsset(in);
			
		}
		catch (Throwable e)
		{
			throw new CaelumException("Error caught while reading asset %s:", e, a.filepath);
		}
		
		return ret;
	}
	
	public synchronized void setStreamer(IAssetStreamer as)
	{
		assert as != null;
		
		this.sProvider = as;
		
	}
	
	public void addReceiver(IAssetReceiver r)
	{
		this.receivers.add(r);
		
	}
	
	public void removeReceiver(IAssetReceiver r)
	{
		this.receivers.remove(r);
		
	}
	
	public void setReader(String ext, IAssetReader r)
	{
		assert ext != null && !ext.equalsIgnoreCase("");
		assert r != null;
		
		this.readers.put(ext.toLowerCase(), r);
		
	}
	
	public void dropAsset(Asset a)
	{
		if (a == null)
		{
			throw new NullPointerException();
		}
		
		this.assets.remove(a);
	}
	
	public Asset getExistingAsset(String filename, EnumAssetType type)
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
	
	public IAssetReader getReader(String ext)
	{
		return this.readers.get(ext.toLowerCase());
	}
	
	public InputStream getIn(String loc)
	{
		return this.sProvider.getIn(loc);
	}
	
}
