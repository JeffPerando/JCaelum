
package com.elusivehawk.util.io;

import com.elusivehawk.util.storage.Buffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ByteBufWrapper implements IByteReader, IByteWriter
{
	protected final Buffer<Byte> in, out;
	
	public ByteBufWrapper(Buffer<Byte> buf)
	{
		this(buf, buf);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public ByteBufWrapper(Buffer<Byte> i, Buffer<Byte> o)
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
		return this.out.next();
	}
	
	@Override
	public int write(byte... bytes)
	{
		int written = 0;
		
		for (byte b : bytes)
		{
			if (!this.in.add(b))
			{
				break;
			}
			
			written++;
			
		}
		
		return written;
	}
	
}
