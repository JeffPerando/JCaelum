
package com.elusivehawk.engine.network;

import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.ImmutableList;

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
		
		data = new ArrayList<Object>(pktSize);
		
	}
	
	public void addData(Object obj)
	{
		this.data.set(this.data.indexOf(null), obj);
		
	}
	
	public int getDataSize()
	{
		return this.data.size();
	}
	
	public ImmutableList<Object> getData()
	{
		return ImmutableList.copyOf(this.data);
	}
	
}
