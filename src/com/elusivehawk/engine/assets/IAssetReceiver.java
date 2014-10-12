
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
	void onAssetLoaded(Asset a);
	
}
