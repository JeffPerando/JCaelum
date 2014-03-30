
package com.elusivehawk.engine.network;

import java.util.ArrayList;
import java.util.List;
import com.elusivehawk.engine.network.PacketFormat.PktFormatItr;
import com.elusivehawk.engine.util.io.ByteLists;
import com.elusivehawk.engine.util.io.IByteReader;
import com.elusivehawk.engine.util.io.Serializer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

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
public final class Packet
{
	public final PacketFormat format;
	private final List<Object> data;
	
	@SuppressWarnings({"unqualified-field-access", "unused"})
	public Packet(PacketFormat f)
	{
		assert f != null;
		
		format = f;
		
		int pktSize = 0;
		
		for (DataType type : f)
		{
			pktSize++;
			
		}
		
		data = Lists.newArrayListWithCapacity(pktSize);
		
	}
	
	public void addData(Object obj)
	{
		this.data.add(obj);
		
	}
	
	public int getDataSize()
	{
		return this.data.size();
	}
	
	public ImmutableList<Object> getData()
	{
		return ImmutableList.copyOf(this.data);
	}
	
	public IByteReader toBytes()
	{
		ByteLists l = new ByteLists(new ArrayList<Byte>());
		int length = Serializer.SHORT.toBytes(l, this.format.pktId);
		
		l.outPos = 4;
		
		PktFormatItr itr = this.format.iterator();
		
		while (itr.hasNext())
		{
			length += itr.next().encode(this.format.format, itr.position(), this.data.get(itr.actualPos()), l);
			
		}
		
		l.outPos = 2;
		
		Serializer.SHORT.toBytes(l, (short)length);
		
		return l;
	}
	
}
