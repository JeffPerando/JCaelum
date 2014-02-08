
package com.elusivehawk.engine.util.io;

import java.nio.ByteBuffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ByteBuf implements ByteWrapper, ByteWriter
{
	protected final ByteBuffer buf;
	
	@SuppressWarnings("unqualified-field-access")
	public ByteBuf(ByteBuffer b)
	{
		buf = b;
		
	}
	
	@Override
	public byte read()
	{
		return this.buf.get();
	}
	
	@Override
	public void write(byte... bytes)
	{
		this.buf.put(bytes);
		
	}
	
}
