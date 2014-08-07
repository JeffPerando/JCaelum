
package com.elusivehawk.engine.assets;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.elusivehawk.engine.CaelumException;
import com.elusivehawk.util.FileHelper;
import com.elusivehawk.util.StringHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class AssetManager
{
	protected final Map<String, IAssetReader> readers = Maps.newHashMap();
	protected final List<IAssetReceiver> receivers = Lists.newArrayList();
	protected final List<Asset> assets = Lists.newArrayList();
	protected final List<File> resourceLocations = Lists.newArrayList(),
			filesToScan = Lists.newArrayList();
	
	protected boolean loaded = false;
	
	public AssetManager(){}
	
	public synchronized void addSearchDirectory(File dir)
	{
		if (!dir.exists())
		{
			return;
		}
		
		if (!dir.isDirectory())
		{
			return;
		}
		
		this.filesToScan.add(dir);
		
	}
	
	public synchronized void addAssetReader(String ext, IAssetReader r)
	{
		assert ext != null;
		assert r != null;
		
		this.readers.put(ext, r);
		
	}
	
	public synchronized void addAssetReceiver(IAssetReceiver r)
	{
		this.receivers.add(r);
		
	}
	
	public synchronized void removeAssetReceiver(IAssetReceiver r)
	{
		this.receivers.remove(r);
		
	}
	
	public void dropAsset(Asset a)
	{
		if (a != null)
		{
			synchronized (this)
			{
				this.assets.remove(a);
				
			}
			
			return;
		}
		
		throw new NullPointerException();
	}
	
	public void onAssetRead(Asset a, boolean received)
	{
		if (a == null)
		{
			throw new NullPointerException("Asset was not read, it is null!");
		}
		if (this.assets.contains(a))
		{
			throw new CaelumException("Duplicate asset entry %s!", a);
		}
		
		synchronized (this)
		{
			this.assets.add(a);
			
		}
		
		if (!received)
		{
			Iterator<IAssetReceiver> itr = this.receivers.iterator();
			IAssetReceiver r;
			
			while (itr.hasNext())
			{
				r = itr.next();
				
				if (!r.onAssetLoaded(a))
				{
					itr.remove();
					
				}
				
			}
			
		}
		
	}
	
	public Asset getExistingAsset(String filename)
	{
		if (!this.assets.isEmpty())
		{
			for (Asset a : this.assets)
			{
				if (filename.endsWith(a.name))
				{
					return a;
				}
				
			}
			
		}
		
		return null;
	}
	
	public void initiate()
	{
		if (this.loaded)
		{
			return;
		}
		
		if (!this.canLoadAssets())
		{
			return;
		}
		
		for (File file : this.filesToScan)
		{
			this.resourceLocations.addAll(FileHelper.getFiles(file));
			
		}
		
		this.loaded = true;
		
	}
	
	public boolean canLoadAssets()
	{
		return !this.resourceLocations.isEmpty() && !this.readers.isEmpty() && !this.receivers.isEmpty();
	}
	
	public IAssetReader getReader(File file)
	{
		return this.readers.get(StringHelper.splitOnce(file.getName(), ".")[1]);
	}
	
	public File findFile(String loc)
	{
		for (File file : this.resourceLocations)
		{
			if (file.getPath().endsWith(loc))
			{
				return file;
			}
			
		}
		
		return null;
	}
	
}
