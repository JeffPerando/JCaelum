
package com.elusivehawk.engine.util.io;

import java.nio.ByteBuffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ByteBuf implements ByteReader, ByteWriter
{
	protected final ByteBuffer in, out;
	
	public ByteBuf(ByteBuffer b)
	{
		this(b, b);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public ByteBuf(ByteBuffer i, ByteBuffer o)
	{
		in = i;
		out = o;
		
	}
	
	@Override
	public byte read()
	{
		return this.in.get();
	}
	
	@Override
	public void write(byte... bytes)
	{
		this.out.put(bytes);
		
	}
	
}
