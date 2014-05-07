
package com.elusivehawk.engine.assets;

import java.io.File;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IAssetReader
{
	public Asset readAsset(AssetManager mgr, File file) throws Throwable;
	
}
