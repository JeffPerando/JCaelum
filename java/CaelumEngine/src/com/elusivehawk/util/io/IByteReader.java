
package com.elusivehawk.util.io;

import java.util.UUID;
import com.elusivehawk.util.Logger;

/**
 * 
 * Convenience interface for reading bytes.
 * 
 * @author Elusivehawk
 */
public interface IByteReader
{
	public int remaining();
	
	public byte read() throws Throwable;
	
	default byte[] readAll() throws Throwable
	{
		return this.read(this.remaining());
	}
	
	default byte[] read(int count) throws Throwable
	{
		byte[] ret = new byte[count];
		
		for (int c = 0; c < ret.length; c++)
		{
			ret[c] = this.read();
			
		}
		
		return ret;
	}
	
	default boolean readBool()
	{
		boolean ret = false;
		
		try
		{
			ret = this.read() == 1;
			
		}
		catch (Throwable e)
		{
			Logger.log().err(e);
			
		}
		
		return ret;
	}
	
	default double readDouble()
	{
		return Serializers.DOUBLE.fromBytes(this);
	}
	
	default float readFloat()
	{
		return Serializers.FLOAT.fromBytes(this);
	}
	
	default int readInt()
	{
		return Serializers.INTEGER.fromBytes(this);
	}
	
	default long readLong()
	{
		return Serializers.LONG.fromBytes(this);
	}
	
	default short readShort()
	{
		return Serializers.SHORT.fromBytes(this);
	}
	
	default String readString()
	{
		return Serializers.STRING.fromBytes(this);
	}
	
	default UUID readUUID()
	{
		return Serializers.UUID.fromBytes(this);
	}
	
}
