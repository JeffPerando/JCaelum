
package com.elusivehawk.engine.assets;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;
import com.elusivehawk.engine.util.concurrent.ThreadStoppable;
import com.elusivehawk.engine.util.storage.TriTuple;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ThreadAssetLoader extends ThreadStoppable
{
	private final List<TriTuple<String, IAssetReceiver, IAssetReader>> assets = Lists.newArrayList();
	private AssetManager assetMgr = null;
	
	@Override
	public void rawUpdate() throws Throwable
	{
		if (this.assets.isEmpty())
		{
			Thread.sleep(1L);
			
			return;
		}
		
		Iterator<TriTuple<String, IAssetReceiver, IAssetReader>> itr = this.assets.iterator();
		
		itr.forEachRemaining((t) ->
		{
			Asset a = this.assetMgr.getExistingAsset(t.one);
			
			if (a == null)
			{
				for (File file : this.assetMgr.getFiles())
				{
					if (file.getAbsolutePath().endsWith(t.one))
					{
						if (t.three == null)
						{
							a = this.assetMgr.readAsset(file);
							
						}
						else
						{
							try
							{
								a = t.three.readAsset(this.assetMgr, file);
							}
							catch (Throwable e)
							{
								this.handleException(e);
								
							}
							
						}
						
						if (a != null)
						{
							break;
						}
						
					}
					
				}
				
			}
			
			if (a == null)
			{
				CaelumEngine.log().log(EnumLogType.WARN, String.format("Asset %s failed to load", t.one));
				
			}
			else
			{
				t.two.onAssetLoaded(a);
				this.assetMgr.registerAsset(a);
				
				a = null;
				
			}
			
			itr.remove();
			
		});
		
	}
	
	public synchronized void setManager(AssetManager mgr)
	{
		if (this.assetMgr == null)
		{
			this.assetMgr = mgr;
			
		}
		
	}
	
	public synchronized void loadAsset(String location, IAssetReceiver tkt)
	{
		this.loadAsset(location, tkt, null);
		
	}
	
	public synchronized void loadAsset(String location, IAssetReceiver tkt, IAssetReader r)
	{
		this.assets.add(TriTuple.create(location, tkt, r));
		
	}
	
}
