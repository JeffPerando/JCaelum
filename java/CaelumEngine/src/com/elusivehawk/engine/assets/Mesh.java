
package com.elusivehawk.engine.assets;

import com.elusivehawk.engine.math.Vector;
import com.elusivehawk.engine.util.storage.ImmutableArray;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Mesh extends Asset
{
	public final ImmutableArray<Vector> points;
	public final ImmutableArray<Integer> indices;
	
	@SuppressWarnings("unqualified-field-access")
	public Mesh(String filename, Vector[] p, Integer[] in)
	{
		super(filename);
		points = ImmutableArray.create(p);
		indices = ImmutableArray.create(in);
		
	}
	
	@Override
	protected boolean finishAsset()
	{
		return true;
	}
	
}
