
package com.elusivehawk.engine.render;

import java.io.BufferedInputStream;
import com.elusivehawk.engine.assets.Asset;
import com.elusivehawk.engine.assets.EnumAssetType;
import com.elusivehawk.util.math.Vector;
import com.elusivehawk.util.storage.ImmutableArray;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Mesh extends Asset
{
	public final ImmutableArray<Vector> points, texOffs, normals;
	
	@SuppressWarnings("unqualified-field-access")
	public Mesh(String filepath, Vector[] p, Vector[] tex, Vector[] norm)
	{
		super(filepath, EnumAssetType.MESH);
		
		points = ImmutableArray.create(p);
		texOffs = ImmutableArray.create(tex);
		normals = ImmutableArray.create(norm);
		
	}
	
	@Override
	protected boolean readAsset(BufferedInputStream in)
	{
		return false;
	}
	
}
