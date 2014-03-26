
package com.elusivehawk.engine.assets;

import java.io.File;
import java.util.List;
import java.util.Map;
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
	protected final Map<String, IAssetRequester> expectedRes = Maps.newHashMap();
	protected final Map<String, AssetReader> readers = Maps.newHashMap();
	protected final List<Asset> assets = SimpleList.newList();
	protected final List<File> resourceLocations = SimpleList.newList();
	protected final List<File> filesToScan = SimpleList.newList();
	protected boolean loaded = false;
	
	@SuppressWarnings("unqualified-field-access")
	public AssetManager(ThreadAssetLoader thr)
	{
		worker = thr;
		thr.setManager(this);
		
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
	
	public void loadResource(String res, IAssetRequester req)
	{
		assert res != null;
		assert req != null;
		
		this.worker.loadAsset(res.replace("/", FileHelper.FILE_SEP), req);
		
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
