
package com.elusivehawk.engine.assets;

import java.io.File;
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
		super(filepath);
		
		points = ImmutableArray.create(p);
		texOffs = ImmutableArray.create(tex);
		normals = ImmutableArray.create(norm);
		
	}
	
	@Override
	protected boolean readAsset(File asset)
	{
		return true;
	}
	
}
