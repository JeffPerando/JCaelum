
package com.elusivehawk.engine.assets;

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
	
	public Object getAttachment();
	
}
