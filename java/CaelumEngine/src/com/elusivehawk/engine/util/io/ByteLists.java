
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
	public int inPos = 0, outPos = 0;
	
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
	public byte[] readAll()
	{
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
		for (byte b : bytes)
		{
			this.out.add(this.outPos++, b);
			
		}
		
	}
	
}
