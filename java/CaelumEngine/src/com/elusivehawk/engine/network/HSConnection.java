
package com.elusivehawk.engine.network;

import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.NetworkChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.elusivehawk.engine.util.io.IByteReader;
import com.google.common.collect.ImmutableList;

/**
 * 
 * Internal class for handshaking.
 * <p>
 * If you're going to attempt coding voodoo/shenanigans, feel free to use it; Otherwise, please don't bother.
 * 
 * @author Elusivehawk
 */
public class HSConnection implements IPacketHandler, IConnection
{
	private final IHost master;
	private final IConnection connect;
	private final short[] expectPkts;
	
	@SuppressWarnings("unqualified-field-access")
	public HSConnection(IHost owner, UUID id)
	{
		assert owner != null;
		
		master = owner;
		connect = new Connection(this, id);
		expectPkts = owner.getHandshakeProtocol();
		
	}
	
	@Override
	public void onPacketsReceived(IConnection origin, ImmutableList<Packet> pkts)
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
				index = pktList.indexOf(pkt.format.pktId);
				
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
	public void onDisconnect(IConnection connect)
	{
		this.master.onDisconnect(connect);
		
	}
	
	@Override
	public ByteBuffer decryptData(ByteBuffer buf)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void encryptData(IByteReader r, ByteBuffer buf)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public PacketFormat getPacketFormat(short id)
	{
		return this.master.getPacketFormat(id);
	}
	
	@Override
	public boolean connect(ConnectionType type, IP ip)
	{
		return this.connect.connect(type, ip);
	}
	
	@Override
	public boolean connect(ConnectionType type, NetworkChannel ch)
	{
		return this.connect.connect(type, ch);
	}
	
	@Override
	public void close()
	{
		try
		{
			this.connect.close();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
		}
		
	}
	
	@Override
	public UUID getId()
	{
		return this.connect.getId();
	}
	
	@Override
	public SocketChannel getTcp()
	{
		return this.connect.getTcp();
	}
	
	@Override
	public ImmutableList<DatagramChannel> getUdp()
	{
		return this.connect.getUdp();
	}
	
	@Override
	public ImmutableList<Packet> getOutgoingPackets()
	{
		return this.connect.getOutgoingPackets();
	}
	
	@Override
	public void clearPkt(Packet pkt)
	{
		this.connect.clearPkt(pkt);
		
	}
	
	@Override
	public void sendPackets(Packet... pkts)
	{
		this.connect.sendPackets(pkts);
		
	}

	@Override
	public boolean isClosed()
	{
		return this.connect.isClosed();
	}
	
	@Override
	public void close(boolean closeSkt)
	{
		this.connect.close(closeSkt);
		
	}
	
}
