
package com.elusivehawk.engine.assets;

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
	Object readAsset(DataInputStream in) throws Throwable;
	
}
