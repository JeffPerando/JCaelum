
package com.elusivehawk.caelum.assets;

import java.io.DataInputStream;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IAssetReader
{
	Object readAsset(IAsset asset, DataInputStream in) throws Throwable;
	
}
