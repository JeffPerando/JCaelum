
package com.elusivehawk.engine.core;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import com.elusivehawk.engine.util.Buffer;
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
	protected final Map<String, UUID> expectedRes = Maps.newHashMap();
	protected final Map<String, AssetReader> readers = Maps.newHashMap();
	protected final Map<UUID, Asset> assetMap = Maps.newHashMap();
	protected final List<File> resourceLocations = new SimpleList<File>();
	protected final Buffer<File> filesToScan = new Buffer<File>();
	protected boolean loaded = false, preloaded = false;
	
	AssetManager(){}
	
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
	
	public UUID expectResource(String res)
	{
		assert res != null;
		
		String fixres = res.replace("/", FileHelper.FILE_SEP);
		
		UUID ret = this.expectedRes.get(fixres);
		
		if (ret != null)
		{
			return ret;
		}
		
		ret = UUID.randomUUID();
		
		this.expectedRes.put(fixres, ret);
		
		return ret;
	}
	
	public void addAssetReader(String ext, AssetReader r)
	{
		assert ext != null;
		assert r != null;
		
		this.readers.put(ext, r);
		
	}
	
	public boolean isFinishedLoading()
	{
		return this.loaded;
	}
	
	public void initiate()
	{
		if (this.loaded || this.preloaded)
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
		
		this.preloaded = true;
		
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
				this.scanFile(file0);
				
			}
			
		}
		else if (file.isFile())
		{
			this.filesToScan.add(file);
			
		}
		
	}
	
	public void continueLoadingAssets()
	{
		if (!this.preloaded || this.loaded)
		{
			return;
		}
		
		if (!(Thread.currentThread() instanceof ThreadAssetLoader))
		{
			return;
		}
		
		for (File file : this.resourceLocations)
		{
			this.scanFile(file);
			
		}
		
		if (this.filesToScan.remaining() == 0)
		{
			this.loaded = true;
			
		}
		
	}
	
	protected void scanFile(File file)
	{
		AssetReader r = this.readers.get(TextParser.splitOnce(file.getName(), ".")[1]);
		
		if (r == null)
		{
			return;
		}
		
		String path = file.getAbsolutePath();
		UUID id = null;
		
		for (Entry<String, UUID> entry : this.expectedRes.entrySet())
		{
			if (path.endsWith(entry.getKey()))
			{
				id = entry.getValue();
				break;
			}
			
		}
		
		if (id == null)
		{
			CaelumEngine.log().log(EnumLogType.WARN, String.format("Unexpected resource found: %s", path));
			
			id = UUID.randomUUID();
			
		}
		
		Asset asset = r.readAsset(file);
		
		if (asset != null)
		{
			this.assetMap.put(id, asset);
			
		}
		
	}
	
}
