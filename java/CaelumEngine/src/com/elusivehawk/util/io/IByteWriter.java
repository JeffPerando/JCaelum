
package com.elusivehawk.util.io;

import java.util.UUID;
import com.elusivehawk.util.Logger;

/**
 * 
 * Convenience interface for writing bytes.
 * 
 * @author Elusivehawk
 */
public interface IByteWriter
{
	public int write(byte... bytes);
	
	default int write(IByteReader r)
	{
		return this.write(r, r.remaining());
	}
	
	default int write(IByteReader r, int count)
	{
		try
		{
			byte[] b = r.read(count);
			
			return this.write(b);
		}
		catch (Throwable e)
		{
			Logger.log().err(e);
			
		}
		
		return -1;
	}
	
	default void writeBool(boolean b)
	{
		this.write((byte)(b ? 1 : 0));
		
	}
	
	default void writeDouble(double d)
	{
		Serializers.DOUBLE.toBytes(this, d);
		
	}
	
	default void writeFloat(float f)
	{
		Serializers.FLOAT.toBytes(this, f);
	}
	
	default void writeInt(int i)
	{
		Serializers.INTEGER.toBytes(this, i);
		
	}
	
	default void writeLong(long l)
	{
		Serializers.LONG.toBytes(this, l);
		
	}
	
	default void writeShort(short s)
	{
		Serializers.SHORT.toBytes(this, s);
		
	}
	
	default void writeString(String str)
	{
		Serializers.STRING.toBytes(this, str);
		
	}
	
	default void writeUUID(UUID uuid)
	{
		Serializers.UUID.toBytes(this, uuid);
		
	}
	
}
