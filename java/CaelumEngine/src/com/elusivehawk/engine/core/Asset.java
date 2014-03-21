
package com.elusivehawk.engine.core;

import com.elusivehawk.engine.util.IGettable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface Asset extends IGettable<Asset>
{
	public EnumAssetType getType();
	
	public int[] getIds();
	
	public boolean isFinished();
	
	public void finish();
	
}
