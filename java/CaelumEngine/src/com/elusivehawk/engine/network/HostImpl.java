
package com.elusivehawk.engine.network;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class HostImpl implements IHost
{
	protected final List<PacketFormat> protocol = new ArrayList<PacketFormat>();
	
	@Override
	public void addPacketFormat(PacketFormat format)
	{
		int in = -1;
		
		for (int c = 0; c < this.protocol.size(); c++)
		{
			if (this.protocol.get(c).getId() == format.getId())
			{
				in = c;
				break;
			}
			
		}
		
		if (in == -1)
		{
			this.protocol.add(format);
			
		}
		else
		{
			this.protocol.add(in, format);
			
		}
		
	}
	
	@Override
	public PacketFormat getPacketFormat(short id)
	{
		for (PacketFormat format : this.protocol)
		{
			if (format.getId() == id)
			{
				return format;
			}
			
		}
		
		return null;
	}
	
}
