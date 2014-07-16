
package com.elusivehawk.engine.assets;

import com.elusivehawk.engine.math.Vector;
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
	public Mesh(String filename, Vector[] p, Vector[] tex, Vector[] norm)
	{
		super(filename);
		points = ImmutableArray.create(p);
		texOffs = ImmutableArray.create(tex);
		normals = ImmutableArray.create(norm);
		
	}
	
	@Override
	protected boolean finishAsset()
	{
		return true;
	}
	
}
