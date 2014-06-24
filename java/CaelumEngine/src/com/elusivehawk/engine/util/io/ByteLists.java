
package com.elusivehawk.engine.util.io;

import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ByteLists implements IByteReader, IByteWriter
{
	protected final List<Byte> in, out;
	public int inPos = 0;
	
	public ByteLists(List<Byte> l)
	{
		this(l, l);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public ByteLists(List<Byte> i, List<Byte> o)
	{
		in = i;
		out = o;
		
	}
	
	@Override
	public int remaining()
	{
		return this.in.size() - this.inPos;
	}
	
	@Override
	public byte read()
	{
		return this.in.get(this.inPos++);
	}
	
	@Override
	public int write(byte... bytes)
	{
		int written = 0;
		
		for (byte b : bytes)
		{
			if (!this.out.add(b))
			{
				break;
			}
			
			written++;
			
		}
		
		return written;
	}
	
}
