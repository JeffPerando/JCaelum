
package com.elusivehawk.engine.network;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.ImmutableList;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ThreadNetworkIncoming extends ThreadNetwork 
{
	private BufferedInputStream bis = null;
	private DataInputStream in = null;
	
	public ThreadNetworkIncoming(IPacketHandler h, Connection con, int ups)
	{
		super(h, con, ups);
		
	}
	
	@Override
	public boolean initiate()
	{
		InputStream is = null;
		
		try
		{
			is = this.connect.getSocket().getInputStream();
			
		}
		catch (Exception e)
		{
			return false;
		}
		
		this.bis = new BufferedInputStream(is);
		this.in = new DataInputStream(this.bis);
		
		return true;
	}
	
	@Override
	public void update(double delta) throws Exception
	{
		if (this.in.available() == 0)
		{
			return;
		}
		
		List<Packet> pkts = new ArrayList<Packet>();
		
		while (this.in.available() > 0)
		{
			short id = this.in.readShort();
			
			PacketFormat format = this.handler.getPacketFormat(id);
			
			if (format == null || format.pktId != id || !this.handler.getSide().canReceive(format.side))
			{
				this.in.skip(this.in.available());
				return;
			}
			
			Packet pkt = format.decodePkt(this.in);
			
			if (pkt == null)
			{
				continue;
			}
			
			pkts.add(pkt);
			
		}
		
		if (pkts.isEmpty())
		{
			return;
		}
		
		this.handler.onPacketsReceived(this.connect, ImmutableList.copyOf(pkts));
		
	}
	
}
