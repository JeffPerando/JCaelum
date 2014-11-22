
package com.elusivehawk.caelum.assets;

import java.io.DataInputStream;
import java.io.DataOutputStream;


/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IAssetStreamer
{
	DataInputStream getIn(String name);
	
	default DataOutputStream getOut(String name)
	{
		return null;
	}
	
}
