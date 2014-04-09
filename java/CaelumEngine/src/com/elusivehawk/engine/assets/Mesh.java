
package com.elusivehawk.engine.assets;

import com.elusivehawk.engine.util.storage.ImmutableArray;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Mesh extends Asset
{
	public final ImmutableArray<Float> points;
	public final ImmutableArray<Integer> indices;
	
	@SuppressWarnings("unqualified-field-access")
	public Mesh(String filename, Float[] p, Integer[] in)
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
