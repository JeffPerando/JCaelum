
package com.elusivehawk.caelum.assets;

import java.io.InputStream;


/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IAssetStreamer
{
	InputStream getIn(String name);
	
}
