
package com.elusivehawk.engine.network;

import java.util.ArrayList;
import java.util.List;

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
	
	public int getDataSize()
	{
		return this.data.size();
	}
	
	public void addData(Object obj)
	{
		this.data.add(obj);
		
	}
	
	public List<Object> getData()
	{
		return this.data;
	}
	
}
