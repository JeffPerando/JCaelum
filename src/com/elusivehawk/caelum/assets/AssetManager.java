
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
import com.elusivehawk.util.parse.ParseHelper;
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
	private final Map<String, IAsset> assets = Maps.newHashMap();
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
	
	public Object readObjectForAsset(IAsset a, DataInputStream in)
	{
		IAssetReader ar = this.getReader(ParseHelper.getSuffix(a.getLocation(), "."));
		
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
	
	public IAsset getAsset(String path)
	{
		return this.assets.get(path);
	}
	
	public void readAsset(IAsset asset) throws Throwable
	{
		IAsset aDup = this.getAsset(asset.getLocation());
		
		if (aDup != null)
		{
			if (aDup != asset)
			{
				asset.onDuplicateFound(aDup);
				
			}
			
			return;
		}
		
		InputStream is = this.getIn(asset.getLocation());
		
		if (is == null)
		{
			Logger.log(EnumLogType.WARN, "Asset stream for \"%s\" cannot be null!", asset.getLocation());
			
			return;
		}
		
		DataInputStream in = new DataInputStream(is);
		
		try
		{
			asset.read(in);
			
		}
		catch (Throwable e)
		{
			Logger.err("Error caught while reading asset %s", asset, e);
			
		}
		finally
		{
			in.close();
			
		}
		
		this.assets.put(asset.getLocation(), asset);
		
		asset.onRead();
		
		Iterator<IAssetReceiver> itr = this.receivers.iterator();
		
		while (itr.hasNext())
		{
			itr.next().onAssetLoaded(asset);
			
		}
		
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
