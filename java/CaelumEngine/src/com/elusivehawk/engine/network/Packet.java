
package com.elusivehawk.engine.network;

import com.elusivehawk.engine.math.MathHelper;
import com.elusivehawk.engine.util.io.IByteWriter;

/**
 * 
 * The core packet class.
 * <p>
 * Stores two things: The current format (Which is how it will be read on the other side), and the data the packet is taking along.
 * <p>
 * All data needs to have a corresponding {@link DataType}, no exceptions.
 * 
 * @author Elusivehawk
 */
public final class Packet implements IByteWriter
{
	private final Side side;
	private final byte[] data;
	private int pos = 0;
	
	public Packet(Side s, int length)
	{
		this(s, new byte[MathHelper.clamp(length, 1, NetworkConst.DATA_LENGTH) + NetworkConst.HEADER_LENGTH]);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Packet(Side s, byte[] b)
	{
		assert b != null;
		assert MathHelper.bounds(b.length, 1, NetworkConst.TOTAL_PKT_LENGTH);
		
		side = s;
		data = b;
		
		data[0] = (byte)s.ordinal();
		
		int l = this.getDataSize();
		
		data[1] = (byte)(l & 0xFF);
		data[2] = (byte)((l >> 8) & 0xFF);
		
	}
	
	@Override
	public void write(byte... bytes)
	{
		if (this.pos == this.getDataSize())
		{
			return;
		}
		
		for (byte b : bytes)
		{
			this.data[(NetworkConst.HEADER_LENGTH + this.pos++)] = b;
			
		}
		
	}
	
	public int getDataSize()
	{
		return this.data.length - 3;
	}
	
	public byte[] getBytes()
	{
		return this.data;
	}
	
	public Side getSide()
	{
		return this.side;
	}
	
}
