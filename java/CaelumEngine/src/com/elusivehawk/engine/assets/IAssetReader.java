
package com.elusivehawk.engine.assets;

import java.io.File;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IAssetReader
{
	public Asset readAsset(File asset);
	
}
