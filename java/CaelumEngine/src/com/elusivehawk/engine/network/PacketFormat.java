
package com.elusivehawk.engine.network;

import java.nio.ByteBuffer;
import com.elusivehawk.engine.util.io.ByteBuf;
import com.elusivehawk.engine.util.io.ByteWrapper;
import com.elusivehawk.engine.util.io.ByteWriter;
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
		
		side = s;
		pktId = id;
		format = ImmutableList.copyOf(f);
		
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
	
	public int write(Packet pkt, ByteBuffer buf)
	{
		ByteWriter w = new ByteBuf(buf);
		DataType type;
		int length = Serializer.SHORT.toBytes(w, this.pktId);
		
		buf.position(buf.position() + 2);
		buf.mark();
		
		for (int c = 0; c < this.format.size(); c++)
		{
			type = this.format.get(c);
			
			if (c > 0 && this.format.get(c - 1) == DataType.ARRAY)
			{
				continue;
			}
			
			length += type.encode(this.format, c, pkt.getData().get(c), w);
			
		}
		
		buf.rewind();
		
		Serializer.SHORT.toBytes(w, (short)length);
		
		buf.reset();
		
		return length;
	}
	
}
