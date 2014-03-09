
package com.elusivehawk.engine.core;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.elusivehawk.engine.util.FileHelper;
import com.elusivehawk.engine.util.SimpleList;
import com.elusivehawk.engine.util.TextParser;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class AssetManager
{
	protected final ThreadAssetLoader worker;
	protected final Map<String, AssetTicket> expectedRes = Maps.newHashMap();
	protected final Map<String, AssetReader> readers = Maps.newHashMap();
	protected final Map<UUID, Asset> assetMap = Maps.newHashMap();
	protected final List<File> resourceLocations = new SimpleList<File>();
	protected final List<File> filesToScan = new SimpleList<File>();
	protected boolean loaded = false;
	
	@SuppressWarnings("unqualified-field-access")
	AssetManager(ThreadAssetLoader thr)
	{
		worker = thr;
		
	}
	
	public void addSearchDirectory(File dir)
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
	
	public AssetTicket loadResource(String res)
	{
		assert res != null;
		
		String fixres = res.replace("/", FileHelper.FILE_SEP);
		
		AssetTicket ret = this.expectedRes.get(fixres);
		
		if (ret == null)
		{
			ret = new AssetTicket(UUID.randomUUID());
			
			this.expectedRes.put(fixres, ret);
			
		}
		
		return ret;
	}
	
	public void addAssetReader(String ext, AssetReader r)
	{
		assert ext != null;
		assert r != null;
		
		this.readers.put(ext, r);
		
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
		else if (file.isFile())
		{
			this.filesToScan.add(file);
			
		}
		
	}
	
	public AssetReader getReader(File file)
	{
		return this.readers.get(TextParser.splitOnce(file.getName(), ".")[1]);
	}
	
	public List<File> getFiles()
	{
		return this.filesToScan;
	}
	
}
