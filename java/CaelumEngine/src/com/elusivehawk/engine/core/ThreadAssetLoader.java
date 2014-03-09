
package com.elusivehawk.engine.core;

import java.io.File;
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
	private final Buffer<Tuple<String, AssetTicket>> assets = new Buffer<Tuple<String, AssetTicket>>();
	private AssetManager assetMgr = null;
	
	@Override
	public void rawUpdate() throws Throwable
	{
		if (!this.assets.isEmpty())
		{
			for (Tuple<String, AssetTicket> tuple : this.assets)
			{
				for (File file : this.assetMgr.getFiles())
				{
					if (file.getAbsolutePath().endsWith(tuple.one))
					{
						AssetReader r = this.assetMgr.getReader(file);
						
						if (r != null)
						{
							Asset a = r.readAsset(file);
							
							if (a != null)
							{
								tuple.two.setAsset(a);
								
								this.assets.remove();
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
	public void setManager(AssetManager mgr)
	{
		this.assetMgr = mgr;
		
	}
	
	public synchronized void loadAsset(String location, AssetTicket tkt)
	{
		this.assets.add(new Tuple<String, AssetTicket>(location, tkt));
		
	}
	
}
