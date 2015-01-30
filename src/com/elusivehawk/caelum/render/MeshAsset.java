
package com.elusivehawk.caelum.render;

import java.io.DataInputStream;
import com.elusivehawk.caelum.CaelumEngine;
import com.elusivehawk.caelum.assets.Asset;
import com.elusivehawk.caelum.assets.EnumAssetType;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class MeshAsset extends Asset implements IMesh
{
	private MeshData data = null;
	
	public MeshAsset(String filepath)
	{
		super(filepath, EnumAssetType.MESH);
		
	}
	
	@Override
	public MeshData getData()
	{
		return this.data;
	}
	
	@Override
	protected boolean readAsset(DataInputStream in) throws Throwable
	{
		Object md = CaelumEngine.assets().readObjectForAsset(this, in);
		
		if (!(md instanceof MeshData))
		{
			return false;
		}
		
		this.data = (MeshData)md;
		
		return true;
	}
	
}
