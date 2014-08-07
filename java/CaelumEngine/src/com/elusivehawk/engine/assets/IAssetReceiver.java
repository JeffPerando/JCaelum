
package com.elusivehawk.engine.assets;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IAssetReceiver
{
	public boolean onAssetLoaded(Asset a);
	
}
