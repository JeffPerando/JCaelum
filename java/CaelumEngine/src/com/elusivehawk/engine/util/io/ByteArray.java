
package com.elusivehawk.engine.util.io;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ByteArray implements ByteWrapper, ByteWriter
{
	protected final byte[] info;
	protected int pos = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public ByteArray(byte... b)
	{
		info = b;
		
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
