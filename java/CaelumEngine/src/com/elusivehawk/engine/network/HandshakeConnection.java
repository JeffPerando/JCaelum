
package com.elusivehawk.engine.network;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.google.common.collect.ImmutableList;

/**
 * 
 * Internal class for handshaking.
 * <p>
 * If you're going to attempt coding voodoo/shenanigans, feel free to use it; Otherwise, please don't bother.
 * 
 * @author Elusivehawk
 */
public class HandshakeConnection implements IPacketHandler
{
	private final IHost master;
	private final SocketChannel sch;
	private final Connection connect;
	private final short[] expectPkts;
	
	@SuppressWarnings("unqualified-field-access")
	public HandshakeConnection(IHost owner, SocketChannel s, UUID id, int ups, short... pktsReq)
	{
		assert owner != null;
		assert s != null;
		
		master = owner;
		sch = s;
		connect = new Connection(this, id, ups);
		expectPkts = pktsReq;
		
	}
	
	public void start()
	{
		if (this.connect == null)
		{
			return;
		}
		
		this.connect.connect(this.sch);
		this.connect.beginComm();
		
	}
	
	public Connection getConnection()
	{
		return this.connect;
	}
	
	@Override
	public void onPacketsReceived(Connection origin, ImmutableList<Packet> pkts)
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
		
		if (!fail)
		{
			List<Short> pktList = new ArrayList<Short>();
			
			for (short i : this.expectPkts)
			{
				pktList.add(i);
				
			}
			
			int index;
			
			for (Packet pkt : pkts)
			{
				index = pktList.indexOf(pkt.pktId);
				
				if (index < 0)
				{
					fail = true;
					
				}
				else
				{
					pktList.remove(index);
					
				}
				
			}
			
		}
		
		this.master.onHandshakeEnd(fail, this, pkts);
		
	}
	
	@Override
	public Side getSide()
	{
		return this.master.getSide();
	}
	
	@Override
	public PacketFormat getPacketFormat(short id)
	{
		return this.master.getPacketFormat(id);
	}
	
	@Override
	public void onDisconnect(Connection connect){}
	
}
