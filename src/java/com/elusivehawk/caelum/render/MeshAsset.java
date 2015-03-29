
package com.elusivehawk.caelum.render;

import java.io.DataInputStream;
import com.elusivehawk.caelum.CaelumEngine;
import com.elusivehawk.caelum.assets.Asset;
import com.elusivehawk.caelum.assets.IAsset;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class MeshAsset extends Asset
{
	private MeshData data = null;
	
	public MeshAsset(String filepath)
	{
		super(filepath);
		
	}
	
	@Override
	public void read(DataInputStream in) throws Throwable
	{
		Object md = CaelumEngine.assets().readObjectForAsset(this, in);
		
		this.data = (MeshData)md;
		
	}
	
	@Override
	public void dispose()
	{
		
	}
	
	@Override
	public void onDuplicateFound(IAsset asset)
	{
		if (asset instanceof MeshAsset)
		{
			synchronized (this)
			{
				this.data = ((MeshAsset)asset).data;
				
			}
			
		}
		
	}
	
	public MeshData getData()
	{
		return this.data;
	}
	
}
