
package com.elusivehawk.engine.network;

import java.net.Socket;
import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class HandshakeConnection implements IPacketHandler
{
	private final IHost master;
	private final Socket skt;
	private final Connection connect;
	private final short[] expectPkts;
	
	@SuppressWarnings("unqualified-field-access")
	public HandshakeConnection(IHost owner, Socket s, int id, int ups, short... pktsReq)
	{
		assert owner != null;
		assert s != null;
		
		master = owner;
		skt = s;
		connect = Connection.create(this, id, ups);
		expectPkts = pktsReq;
		
	}
	
	public void start()
	{
		if (this.connect == null)
		{
			return;
		}
		
		this.connect.connect(this.skt);
		this.connect.beginComm();
		
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
		
		this.master.onHandshakeEnd(fail, this.connect, pkts);
		
	}
	
	@Override
	public Side getSide()
	{
		return this.master.getSide();
	}
	
	@Override
	public void addPacketFormat(PacketFormat format)
	{
		this.master.addPacketFormat(format);
		
	}
	
	@Override
	public PacketFormat getPacketFormat(short id)
	{
		return this.master.getPacketFormat(id);
	}
	
	@Override
	public void onDisconnect(Connection connect){}
	
}
