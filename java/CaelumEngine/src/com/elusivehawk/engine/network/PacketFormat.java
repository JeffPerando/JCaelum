
package com.elusivehawk.engine.network;

import java.nio.ByteBuffer;
import java.util.Iterator;
import com.elusivehawk.engine.util.io.ByteBuf;
import com.elusivehawk.engine.util.io.IByteReader;
import com.google.common.collect.ImmutableList;

/**
 * 
 * The core class for how packets are interpreted.
 * 
 * @author Elusivehawk
 */
public final class PacketFormat implements Iterable<DataType>
{
	public final Side side;
	public final ConnectionType type;
	public final short pktId;
	public final ImmutableList<DataType> format;
	
	/**
	 * 
	 * The primary constructor.
	 * 
	 * @param s The side the read packet (should) hail from. (i.e. Client -> Server should be CLIENT, Client <- Server should be SERVER.)
	 * @param conType The type of connection this format is meant for.
	 * @param id The ID for this format.
	 * @param f The format itself.
	 */
	@SuppressWarnings("unqualified-field-access")
	public PacketFormat(Side s, ConnectionType conType, short id, DataType... f)
	{
		assert s != null;
		assert conType != null;
		assert f != null && f.length > 0;
		assert f[f.length - 1] != DataType.ARRAY;
		
		side = s;
		type = conType;
		pktId = id;
		format = ImmutableList.copyOf(f);
		
	}
	
	public Packet read(ByteBuffer buf)
	{
		if (buf == null)
		{
			return null;
		}
		
		Packet ret = new Packet(this);
		IByteReader r = new ByteBuf(buf);
		PktFormatItr itr = this.iterator();
		Object obj;
		
		while (itr.hasNext())
		{
			obj = itr.next().decode(this.format, itr.position(), r);
			
			if (obj == null)
			{
				return null;
			}
			
			ret.addData(obj);
			
		}
		
		return ret;
	}
	
	@Override
	public PktFormatItr iterator()
	{
		return new PktFormatItr(this);
	}
	
	public static class PktFormatItr implements Iterator<DataType>
	{
		protected final ImmutableList<DataType> format;
		protected int pos = 0, nextPos = 0, actualPosition = 0;
		protected DataType last = null;
		
		@SuppressWarnings("unqualified-field-access")
		public PktFormatItr(PacketFormat f)
		{
			assert f != null;
			
			format = f.format;
			
		}
		
		@Override
		public boolean hasNext()
		{
			return this.format.size() > this.pos;
		}
		
		@Override
		public DataType next()
		{
			DataType ret = null;
			
			this.pos = this.nextPos;
			
			if (this.last != null)
			{
				this.actualPosition = this.nextPos;
				this.nextPos += 1 + this.last.getSkipCount(this.pos, this.format);
				
			}
			
			ret = this.format.get(this.pos);
			this.last = ret;
			
			return ret;
		}
		
		@Override
		public void remove(){}
		
		public int position()
		{
			return this.pos;
		}
		
		public int actualPos()
		{
			return this.actualPosition;
		}
		
	}
	
}
