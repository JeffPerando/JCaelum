
package com.elusivehawk.engine.assets;

import java.io.File;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;
import com.elusivehawk.engine.util.Buffer;
import com.elusivehawk.engine.util.ThreadStoppable;
import com.elusivehawk.engine.util.Tuple;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ThreadAssetLoader extends ThreadStoppable
{
	private final Buffer<Tuple<String, IAssetRequester>> assets = new Buffer<Tuple<String, IAssetRequester>>();
	private AssetManager assetMgr = null;
	
	@Override
	public void rawUpdate() throws Throwable
	{
		if (this.assets.isEmpty())
		{
			Thread.sleep(1L);
			
			return;
		}
		
		for (Tuple<String, IAssetRequester> tuple : this.assets)
		{
			for (File file : this.assetMgr.getFiles())
			{
				if (file.getAbsolutePath().endsWith(tuple.one))
				{
					AssetReader r = this.assetMgr.getReader(file);
					
					if (r != null)
					{
						Asset a = r.readAsset(file);
						
						if (a == null)
						{
							CaelumEngine.log().log(EnumLogType.WARN, String.format("Asset %s failed to load", file.getAbsolutePath()));
							
						}
						else
						{
							tuple.two.onAssetLoaded(a);
							
						}
						
						this.assets.remove();
						
					}
					
				}
				
			}
			
		}
		
	}
	
	public void setManager(AssetManager mgr)
	{
		this.assetMgr = mgr;
		
	}
	
	public synchronized void loadAsset(String location, IAssetRequester tkt)
	{
		this.assets.add(Tuple.create(location, tkt));
		
	}
	
}
