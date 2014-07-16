
package com.elusivehawk.util.io;

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
	public int write(byte... bytes)
	{
		int written = 0;
		
		for (int c = 0; c < bytes.length; c++)
		{
			if (this.pos + c == this.info.length)
			{
				break;
			}
			
			written++;
			this.info[this.pos + c] = bytes[c];
			
		}
		
		return written;
	}
	
}
