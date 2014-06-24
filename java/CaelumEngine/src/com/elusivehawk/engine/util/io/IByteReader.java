
package com.elusivehawk.engine.util.io;

/**
 * 
 * Convenience interface for reading bytes.
 * 
 * @author Elusivehawk
 */
public interface IByteReader
{
	public int remaining();
	
	public byte read();
	
	default byte[] readAll()
	{
		byte[] ret = new byte[this.remaining()];
		
		for (int c = 0; c < ret.length; c++)
		{
			ret[c] = this.read();
			
		}
		
		return ret;
	}
	
}
