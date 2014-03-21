
package com.elusivehawk.engine.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ByteStreams implements IByteReader, IByteWriter
{
	protected final InputStream in;
	protected final OutputStream out;
	
	@SuppressWarnings("unqualified-field-access")
	public ByteStreams(InputStream is, OutputStream os)
	{
		in = is;
		out = os;
		
	}
	
	@Override
	public byte read()
	{
		byte ret = -1;
		
		try
		{
			ret = (byte)this.in.read();
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
			
		}
		
		return ret;
	}
	
	@Override
	public void write(byte... bytes)
	{
		try
		{
			this.out.write(bytes);
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
			
		}
		
	}
	
}
