
package com.elusivehawk.engine.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;
import com.elusivehawk.util.BufferHelper;
import com.elusivehawk.util.io.IByteReader;
import com.elusivehawk.util.io.IByteWriter;
import com.elusivehawk.util.io.Serializers;
import com.elusivehawk.util.math.MathHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Packet implements IByteReader, IByteWriter
{
	private final ByteBuffer data;
	private boolean potenCorrupt = false, write = true;
	
	public Packet(int size)
	{
		this(BufferHelper.createByteBuffer(size));
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Packet(ByteBuffer info)
	{
		assert MathHelper.bounds(info.remaining(), 1, NetworkConst.DATA_LENGTH);
		
		data = BufferHelper.makeByteBuffer(info.slice());
		
	}
	
	@Override
	public int write(byte... bytes)
	{
		if (!this.write)
		{
			return -1;
		}
		
		int rem = this.data.remaining();
		
		this.data.put(bytes);
		
		return rem - this.data.remaining();
	}
	
	@Override
	public int remaining()
	{
		return this.data.remaining();
	}
	
	@Override
	public byte read() throws IOException
	{
		if (this.write)
		{
			throw new IOException("Cannot read bytes when in write mode!");
		}
		
		return this.data.get();
	}
	
	public int getDataSize()
	{
		return this.data.capacity();
	}
	
	public ByteBuffer getBytes()
	{
		return this.data;
	}
	
	public boolean readBool()
	{
		return this.data.get() == 1;
	}
	
	public double readDouble()
	{
		return Serializers.DOUBLE.fromBytes(this);
	}
	
	public float readFloat()
	{
		return Serializers.FLOAT.fromBytes(this);
	}
	
	public int readInt()
	{
		return Serializers.INTEGER.fromBytes(this);
	}
	
	public long readLong()
	{
		return Serializers.LONG.fromBytes(this);
	}
	
	public short readShort()
	{
		return Serializers.SHORT.fromBytes(this);
	}
	
	public String readString()
	{
		return Serializers.STRING.fromBytes(this);
	}
	
	public UUID readUUID()
	{
		return Serializers.UUID.fromBytes(this);
	}
	
	public void writeBool(boolean b)
	{
		this.data.put((byte)(b ? 1 : 0));
		
	}
	
	public void writeDouble(double d)
	{
		Serializers.DOUBLE.toBytes(this, d);
		
	}
	
	public void writeFloat(float f)
	{
		Serializers.FLOAT.toBytes(this, f);
	}
	
	public void writeInt(int i)
	{
		Serializers.INTEGER.toBytes(this, i);
		
	}
	
	public void writeLong(long l)
	{
		Serializers.LONG.toBytes(this, l);
		
	}
	
	public void writeShort(short s)
	{
		Serializers.SHORT.toBytes(this, s);
		
	}
	
	public void writeString(String str)
	{
		Serializers.STRING.toBytes(this, str);
		
	}
	
	public void writeUUID(UUID uuid)
	{
		Serializers.UUID.toBytes(this, uuid);
		
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
		
		this.data.flip();
		
	}
	
}
