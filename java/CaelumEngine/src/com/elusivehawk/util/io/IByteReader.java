
package com.elusivehawk.util.io;

/**
 * 
 * Convenience interface for reading bytes.
 * 
 * @author Elusivehawk
 */
public interface IByteReader
{
	public int remaining();
	
	public byte read() throws Throwable;
	
	default byte[] readAll() throws Throwable
	{
		return this.read(this.remaining());
	}
	
	default byte[] read(int count) throws Throwable
	{
		byte[] ret = new byte[count];
		
		for (int c = 0; c < ret.length; c++)
		{
			ret[c] = this.read();
			
		}
		
		return ret;
	}
	
}
