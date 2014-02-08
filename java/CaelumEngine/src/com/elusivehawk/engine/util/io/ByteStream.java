
package com.elusivehawk.engine.util.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ByteStream implements ByteWrapper
{
	protected final InputStream in;
	
	@SuppressWarnings("unqualified-field-access")
	public ByteStream(InputStream is)
	{
		in = is;
		
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
	
}
