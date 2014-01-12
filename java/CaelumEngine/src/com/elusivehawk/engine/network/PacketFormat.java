
package com.elusivehawk.engine.network;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class PacketFormat
{
	private final Side side;
	private final short pktId;
	private final DataType[] format;
	
	@SuppressWarnings("unqualified-field-access")
	public PacketFormat(Side s, short id, DataType... f)
	{
		assert s != null;
		assert id != 0;
		assert f != null;
		
		side = s;
		pktId = id;
		format = f;
		
	}
	
	public Side getSide()
	{
		return this.side;
	}
	
	public short getId()
	{
		return this.pktId;
	}
	
	public DataType[] getFormat()
	{
		return this.format;
	}
	
}
