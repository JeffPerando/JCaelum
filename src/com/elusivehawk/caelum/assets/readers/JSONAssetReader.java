
package com.elusivehawk.caelum.assets.readers;

import java.io.DataInputStream;
import com.elusivehawk.caelum.assets.Asset;
import com.elusivehawk.caelum.assets.IAssetReader;
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
	public Object readAsset(Asset asset, DataInputStream in) throws Throwable
	{
		switch (asset.type)
		{
			case MESH: return MeshData.fromJson(JsonParser.parse(in));
		}
		
		return null;
	}
	
}
