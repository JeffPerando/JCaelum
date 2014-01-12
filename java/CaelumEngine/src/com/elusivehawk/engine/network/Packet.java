
package com.elusivehawk.engine.network;

import com.elusivehawk.engine.util.Buffer;
import com.elusivehawk.engine.util.Tuple;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class Packet
{
	public final short pktId;
	private final Buffer<Tuple<DataType, Object>> data = new Buffer<Tuple<DataType, Object>>();
	
	@SuppressWarnings("unqualified-field-access")
	public Packet(short id)
	{
		pktId = id;
		
	}
	
	public int getDataSize()
	{
		return this.data.size();
	}
	
	public void addData(DataType type, Object obj)
	{
		this.data.add(new Tuple<DataType, Object>(type, obj));
		
	}
	
	public DataType[] getFormatting()
	{
		DataType[] ret = new DataType[this.data.size()];
		
		for (int c = 0; c < this.data.size(); c++)
		{
			ret[c] = this.data.get(c).one;
			
		}
		
		return ret;
	}
	
	public Object next(DataType expected)
	{
		Tuple<DataType, Object> ret = this.data.next();
		
		return (ret.one == expected) ? ret.two : null;
	}
	
}
