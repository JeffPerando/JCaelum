
package com.elusivehawk.engine.network;

import java.net.Socket;
import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class HandshakeClient implements IPacketHandler
{
	private final IHost owner;
	private final Connection connect;
	private final short[] expectPkts;
	
	@SuppressWarnings("unqualified-field-access")
	public HandshakeClient(IHost master, Socket s, int id, int ups, short... pktsReq)
	{
		assert master != null;
		assert s != null;
		
		owner = master;
		expectPkts = pktsReq;
		
		Connection con = null;
		
		try
		{
			con = new Connection(this, s, id, ups);
			
		}
		catch (Exception e){}
		
		connect = con;
		
	}
	
	public void start()
	{
		if (this.connect == null)
		{
			return;
		}
		
		this.connect.start();
		
	}
	
	@Override
	public void onPacketsReceived(Connection origin, List<Packet> pkts)
	{
		boolean fail = false;
		
		if (this.expectPkts == null)
		{
			fail = true;
			
		}
		
		if (pkts.size() != this.expectPkts.length)
		{
			fail = true;
			
		}
		
		for (int c = 0; c < this.expectPkts.length; c++)
		{
			if (pkts.get(c).pktId != this.expectPkts[c])
			{
				fail = true;
				
			}
			
		}
		
		this.owner.onHandshakeEnd(fail, this.connect, pkts);
		
	}
	
	@Override
	public Side getSide()
	{
		return this.owner.getSide();
	}
	
	@Override
	public void addPacketFormat(PacketFormat format){}
	
	@Override
	public PacketFormat getPacketFormat(short id)
	{
		return this.owner.getPacketFormat(id);
	}
	
}
