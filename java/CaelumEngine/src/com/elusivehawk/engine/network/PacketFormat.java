
package com.elusivehawk.engine.network;

import java.nio.ByteBuffer;
import com.elusivehawk.engine.util.BufferHelper;
import com.elusivehawk.engine.util.io.ByteBuf;
import com.elusivehawk.engine.util.io.ByteWrapper;
import com.elusivehawk.engine.util.io.Serializer;
import com.google.common.collect.ImmutableList;

/**
 * 
 * The core class for how packets are interpreted.
 * 
 * @author Elusivehawk
 */
public final class PacketFormat
{
	public final Side side;
	public final short pktId;
	public final ImmutableList<DataType> format;
	
	private final int bArrCount;
	
	/**
	 * 
	 * The primary constructor.
	 * 
	 * @param s The side the read packet (should) hail from. (i.e. Client -> Server should be CLIENT, Client <- Server should be SERVER.)
	 * @param id The ID for this format.
	 * @param f The format itself.
	 */
	@SuppressWarnings("unqualified-field-access")
	public PacketFormat(Side s, short id, DataType... f)
	{
		assert s != null;
		assert id != 0;
		assert f != null && f.length > 0;
		assert f[f.length - 1] != DataType.ARRAY;
		
		DataType type;
		int count = 0;
		
		for (int c = 0; c < f.length; c++)
		{
			type = f[c];
			
			if (type == null)
			{
				throw new IllegalArgumentException(new NullPointerException());
			}
			
			if (type != DataType.ARRAY)
			{
				if (c > 0)
				{
					if (f[c - 1] == DataType.ARRAY)
					{
						continue;
					}
					
				}
				
				count++;
				
			}
			
		}
		
		side = s;
		pktId = id;
		format = ImmutableList.copyOf(f);
		bArrCount = count;
		
	}
	
	public Packet read(ByteBuffer in)
	{
		Packet ret = new Packet(this.pktId);
		ByteWrapper wrap = new ByteBuf(in);
		int pos = 0;
		DataType type;
		
		for (int c = 0; c < this.format.size(); c++)
		{
			type = this.format.get(c);
			
			if (pos > 0 && this.format.get(pos - 1) == DataType.ARRAY)
			{
				continue;
			}
			
			Object obj = type.decode(this.format, pos, wrap);
			
			if (obj == null)
			{
				return null;
			}
			
			ret.addData(obj);
			
		}
		
		return ret;
	}
	
	public byte[] write(Packet pkt)
	{
		byte[][] ret = new byte[this.bArrCount + 1][];
		int next = 1;
		DataType type;
		
		byte[] b = Serializer.SHORT.toBytes(this.pktId);
		ret[0] = new byte[]{b[0], b[1], 0, 0};
		
		for (int c = 0; c < this.format.size(); c++)
		{
			type = this.format.get(c);
			
			if (c > 0 && this.format.get(c - 1) == DataType.ARRAY)
			{
				continue;
			}
			
			ret[next++] = type.encode(this.format, c, pkt.getData().get(c));
			
		}
		
		byte[] fin = BufferHelper.condense(ret);
		b = Serializer.SHORT.toBytes((short)(fin.length - 2));
		fin[2] = b[0];
		fin[3] = b[1];
		
		return fin;
	}
	
}
