
package com.elusivehawk.engine.util.io;

import com.elusivehawk.engine.util.Buffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ByteBufWrapper implements ByteReader, ByteWriter
{
	protected final Buffer<Byte> buffer;
	
	@SuppressWarnings("unqualified-field-access")
	public ByteBufWrapper(Buffer<Byte> buf)
	{
		buffer = buf;
		
	}
	
	@Override
	public void write(byte... bytes)
	{
		for (byte b : bytes)
		{
			this.buffer.add(b);
			
		}
		
	}
	
	@Override
	public byte read()
	{
		return this.buffer.next();
	}
	
}
