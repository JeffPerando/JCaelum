
package com.elusivehawk.engine.assets;

import com.elusivehawk.util.io.IByteReader;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IAssetReader
{
	Object readAsset(IByteReader r) throws Throwable;
	
}
