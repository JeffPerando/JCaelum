
package com.elusivehawk.util.io;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IStreamProvider
{
	public InputStream getInStream(String str);
	
	@SuppressWarnings("unused")
	default OutputStream getOutStream(String str)
	{
		return null;
	}
	
}
