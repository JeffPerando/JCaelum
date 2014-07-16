
package com.elusivehawk.engine.assets;

import java.io.File;
import java.util.List;
import java.util.Map;
import com.elusivehawk.engine.core.CaelumEngine;
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
	protected final Map<String, IAssetReceiver> expectedRes = Maps.newHashMap();
	protected final Map<String, IAssetReader> readers = Maps.newHashMap();
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
		
		this.resourceLocations.add(dir);
		
	}
	
	public void loadResource(String res, IAssetReceiver req)
	{
		CaelumEngine.tasks().scheduleTask(new TaskLoadAsset(req, this, res.replace("/", FileHelper.FILE_SEP)));
		
	}
	
	public synchronized void addAssetReader(String ext, IAssetReader r)
	{
		assert ext != null;
		assert r != null;
		
		this.readers.put(ext, r);
		
	}
	
	public synchronized void registerAsset(Asset a)
	{
		this.assets.add(a);
		
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
		
		for (File file : this.resourceLocations)
		{
			this.scanForFiles(file);
			
		}
		
		this.loaded = true;
		
	}
	
	public boolean canLoadAssets()
	{
		return !this.resourceLocations.isEmpty() && !this.expectedRes.isEmpty() && !this.readers.isEmpty();
	}
	
	protected void scanForFiles(File file)
	{
		if (file.isDirectory())
		{
			for (File file0 : file.listFiles())
			{
				this.scanForFiles(file0);
				
			}
			
		}
		else if (FileHelper.canReadFile(file))
		{
			this.filesToScan.add(file);
			
		}
		
	}
	
	public IAssetReader getReader(File file)
	{
		return this.readers.get(StringHelper.splitOnce(file.getName(), ".")[1]);
	}
	
	public File findFile(String loc)
	{
		for (File file : this.filesToScan)
		{
			if (file.getPath().endsWith(loc))
			{
				return file;
			}
			
		}
		
		return null;
	}
	
}
