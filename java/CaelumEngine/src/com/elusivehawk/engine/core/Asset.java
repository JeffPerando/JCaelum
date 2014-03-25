
package com.elusivehawk.engine.core;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface Asset
{
	public EnumAssetType getType();
	
	public int[] getIds();
	
	public boolean isFinished();
	
	public void finish();
	
}
