
package com.elusivehawk.engine.util.io;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ByteArray implements IByteReader, IByteWriter
{
	protected final byte[] info;
	public int pos = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public ByteArray(byte... b)
	{
		info = b;
		
	}
	
	@Override
	public int remaining()
	{
		return this.info.length - this.pos;
	}
	
	@Override
	public byte read()
	{
		if (this.pos == this.info.length)
		{
			return -1;
		}
		
		return this.info[this.pos++];
	}
	
	@Override
	public byte[] readAll()
	{
		if (this.pos == 0)
		{
			return this.info;
		}
		
		byte[] ret = new byte[this.remaining()];
		
		for (int c = 0; c < ret.length; c++)
		{
			ret[c] = this.read();
			
		}
		
		return ret;
	}
	
	@Override
	public void write(byte... bytes)
	{
		for (int c = 0; c < bytes.length; c++)
		{
			if (c + this.pos == this.info.length)
			{
				return;
			}
			
			this.info[this.pos + c] = bytes[c];
			
		}
		
	}
	
}
