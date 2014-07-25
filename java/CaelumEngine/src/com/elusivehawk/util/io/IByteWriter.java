
package com.elusivehawk.util.io;

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
			e.printStackTrace();
			
		}
		
		return -1;
	}
	
}
