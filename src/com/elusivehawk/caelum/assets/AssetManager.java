
package com.elusivehawk.caelum.assets;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.elusivehawk.caelum.assets.readers.GIFAssetReader;
import com.elusivehawk.caelum.assets.readers.JSONAssetReader;
import com.elusivehawk.caelum.assets.readers.OBJAssetReader;
import com.elusivehawk.caelum.assets.readers.PNGAssetReader;
import com.elusivehawk.util.EnumLogType;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.storage.SyncList;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class AssetManager
{
	private final List<IAssetReceiver> receivers = SyncList.newList();
	private final Map<EnumAssetType, List<Asset>> assets = Maps.newHashMap();
	private final Map<String, IAssetReader> readers = Maps.newHashMap();
	
	private IAssetStreamer sProvider = this.getClass()::getResourceAsStream;
	
	public AssetManager()
	{
		//XXX Generic formats
		
		setReader("json", new JSONAssetReader());
		
		//XXX Image formats
		
		setReader("gif", new GIFAssetReader());
		setReader("png", new PNGAssetReader());
		
		//XXX Model formats
		
		setReader("obj", new OBJAssetReader());
		
	}
	
	public Object readObjectForAsset(Asset a, DataInputStream in)
	{
		IAssetReader ar = this.getReader(a.ext);
		
		Object ret = null;
		
		try
		{
			ret = ar.readAsset(a, in);
			
		}
		catch (Throwable e)
		{
			Logger.err("Error caught while reading asset %s:", e, a);
			
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
	
	public void readAsset(Asset asset) throws Throwable
	{
		Asset aDup = this.getExistingAsset(asset.filepath, asset.type);
		
		if (aDup != null)
		{
			asset.onExistingAssetFound(aDup);
			
			return;
		}
		
		InputStream is = this.getIn(asset.filepath);
		
		if (is == null)
		{
			Logger.log(EnumLogType.WARN, "Asset stream for \"%s\" cannot be null!", asset.filepath);
			
			return;
		}
		
		DataInputStream in = new DataInputStream(is);
		
		if (asset.read(in))
		{
			List<Asset> assetList = this.assets.get(asset.type);
			
			if (assetList == null)
			{
				assetList = SyncList.newList();
				
				this.assets.put(asset.type, assetList);
				
			}
			
			if (!assetList.contains(asset))
			{
				assetList.add(asset);
				
				Iterator<IAssetReceiver> itr = this.receivers.iterator();
				
				while (itr.hasNext())
				{
					itr.next().onAssetLoaded(asset);
					
				}
				
			}
			
		}
		
		in.close();
		
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
