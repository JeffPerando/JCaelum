
package com.elusivehawk.engine.network;

import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.ImmutableList;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class Packet
{
	public final short pktId;
	private final List<Object> data = new ArrayList<Object>();
	
	@SuppressWarnings("unqualified-field-access")
	public Packet(short id)
	{
		pktId = id;
		
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
	
}
