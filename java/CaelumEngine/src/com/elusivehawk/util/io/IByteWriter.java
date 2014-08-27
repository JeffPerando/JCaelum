
package com.elusivehawk.util.io;

import com.elusivehawk.util.Logger;

/**
 * 
 * Convenience interface for writing bytes.
 * 
 * @author Elusivehawk
 */
public interface IByteWriter
{
	public int write(byte... bytes);
	
	default int write(IByteReader r)
	{
		return this.write(r, r.remaining());
	}
	
	default int write(IByteReader r, int count)
	{
		try
		{
			byte[] b = r.read(count);
			
			return this.write(b);
		}
		catch (Throwable e)
		{
			Logger.log().err(e);
			
		}
		
		return -1;
	}
	
}
