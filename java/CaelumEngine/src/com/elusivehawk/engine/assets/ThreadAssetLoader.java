
package com.elusivehawk.engine.assets;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;
import com.elusivehawk.engine.util.SimpleList;
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
	private final List<Tuple<String, IAssetReceiver>> assets = SimpleList.newList();
	private AssetManager assetMgr = null;
	
	@Override
	public void rawUpdate() throws Throwable
	{
		if (this.assetMgr == null || this.assets.isEmpty())
		{
			Thread.sleep(1L);
			
			return;
		}
		
		Iterator<Tuple<String, IAssetReceiver>> itr = this.assets.iterator();
		Tuple<String, IAssetReceiver> tuple;
		Asset a;
		
		while (itr.hasNext())
		{
			tuple = itr.next();
			a = this.assetMgr.getExistingAsset(tuple.one);
			
			if (a == null)
			{
				for (File file : this.assetMgr.getFiles())
				{
					if (file.getAbsolutePath().endsWith(tuple.one))
					{
						a = this.assetMgr.readAsset(file);
						
						if (a != null)
						{
							break;
						}
						
					}
					
				}
				
			}
			
			if (a == null)
			{
				CaelumEngine.log().log(EnumLogType.WARN, String.format("Asset %s failed to load", tuple.one));
				
			}
			else
			{
				tuple.two.onAssetLoaded(a);
				this.assetMgr.registerAsset(a);
				
				a = null;
				
			}
			
			itr.remove();
			
		}
		
	}
	
	public synchronized void setManager(AssetManager mgr)
	{
		this.assetMgr = mgr;
		
	}
	
	public synchronized void loadAsset(String location, IAssetReceiver tkt)
	{
		this.assets.add(Tuple.create(location, tkt));
		
	}
	
}
