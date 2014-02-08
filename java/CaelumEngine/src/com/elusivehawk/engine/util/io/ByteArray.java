
package com.elusivehawk.engine.util.io;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ByteArray implements ByteWrapper
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
	
}
