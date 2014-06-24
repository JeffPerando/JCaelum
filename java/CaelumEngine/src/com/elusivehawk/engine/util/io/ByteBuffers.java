
package com.elusivehawk.engine.util.io;

import java.nio.ByteBuffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ByteBuffers implements IByteReader, IByteWriter
{
	protected final ByteBuffer in, out;
	
	public ByteBuffers(ByteBuffer b)
	{
		this(b, b);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public ByteBuffers(ByteBuffer i, ByteBuffer o)
	{
		in = i;
		out = o;
		
	}
	
	@Override
	public int remaining()
	{
		return this.in.remaining();
	}
	
	@Override
	public byte read()
	{
		return this.in.get();
	}
	
	@Override
	public int write(byte... bytes)
	{
		int pos = this.out.position();
		
		this.out.put(bytes);
		
		return this.out.position() - pos;
	}
	
}
