
package com.elusivehawk.caelum.assets.readers;

import java.io.DataInputStream;
import com.elusivehawk.caelum.assets.IAsset;
import com.elusivehawk.caelum.assets.IAssetReader;
import com.elusivehawk.caelum.render.MeshAsset;
import com.elusivehawk.caelum.render.MeshData;
import com.elusivehawk.util.parse.json.JsonParser;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class JSONAssetReader implements IAssetReader
{
	@Override
	public Object readAsset(IAsset asset, DataInputStream in) throws Throwable
	{
		if (asset instanceof MeshAsset)//FIXME
		{
			return MeshData.fromJson(JsonParser.parse(in));
		}
		
		return null;
	}
	
}
