
package com.elusivehawk.caelum.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import com.elusivehawk.util.io.IByteReader;
import com.elusivehawk.util.io.IByteWriter;
import com.elusivehawk.util.math.MathHelper;
import com.elusivehawk.util.storage.ArrayHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Packet implements IByteReader, IByteWriter
{
	private final byte[] data;
	private int byteCount = 0;
	private boolean potenCorrupt = false, write = true;
	
	public Packet(int size)
	{
		this(new byte[MathHelper.clamp(size, 1, NetworkConst.DATA_LENGTH)]);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Packet(byte[] b)
	{
		assert MathHelper.bounds(b.length, 1, NetworkConst.DATA_LENGTH);
		
		data = b;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Packet(ByteBuffer info)
	{
		assert MathHelper.bounds(info.remaining(), 1, NetworkConst.DATA_LENGTH);
		
		data = ArrayHelper.asBytes(info.slice());
		
	}
	
	@Override
	public void flush(){}
	
	@Override
	public int write(byte... bytes)
	{
		if (!this.write)
		{
			return -1;
		}
		
		int ret = Math.min(bytes.length, this.remaining());
		
		for (int c = 0; c < ret; c++)
		{
			this.data[this.byteCount++] = bytes[c];
			
		}
		
		return ret;
	}
	
	@Override
	public int remaining()
	{
		return this.data.length - this.byteCount;
	}
	
	@Override
	public byte read() throws IOException
	{
		if (this.write)
		{
			throw new IOException("Cannot read bytes when in write mode!");
		}
		
		return this.data[this.byteCount++];
	}
	
	public int getDataSize()
	{
		return this.byteCount;
	}
	
	public byte[] getBytes()
	{
		return this.data;
	}
	
	public boolean mightBeCorrupt()
	{
		return this.potenCorrupt;
	}
	
	public boolean canWrite()
	{
		return this.write;
	}
	
	public void markPotentiallyCorrupt()
	{
		this.potenCorrupt = true;
		
	}
	
	public void markForReading()
	{
		this.write = false;
		
		this.rewind();
		
	}
	
	public void rewind()
	{
		this.byteCount = 0;
		
	}
	
}
