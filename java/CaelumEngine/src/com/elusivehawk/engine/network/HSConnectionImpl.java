
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
public class HSConnectionImpl implements IPacketHandler, IConnection
{
	private final IHost master;
	private final IConnection connect;
	private final short[] expectPkts;
	
	@SuppressWarnings("unqualified-field-access")
	public HSConnectionImpl(IHost owner, UUID id, int ups)
	{
		assert owner != null;
		
		master = owner;
		connect = ConnectionFactory.factory().create(this, id, ups);
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
	public PacketFormat getPacketFormat(short id)
	{
		return this.master.getPacketFormat(id);
	}
	
	@Override
	public boolean validate(PacketFormat format)
	{
		return this.master.validate(format);
	}
	
	@Override
	public void onDisconnect(IConnection connect)
	{
		this.master.onDisconnect(connect);
		
	}
	
	@Override
	public UUID connect(UUID origin, IP ip, ConnectionType type)
	{
		return this.connect.connect(origin, ip, type);
	}
	
	@Override
	public UUID connect(SocketChannel sch)
	{
		return this.connect.connect(sch);
	}
	
	@Override
	public void beginComm()
	{
		if (this.connect == null)
		{
			return;
		}
		
		this.connect.beginComm();
		
	}
	
	@Override
	public boolean isPaused()
	{
		return this.connect.isPaused();
	}
	
	@Override
	public void setPaused(boolean p)
	{
		this.connect.setPaused(p);
		
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
	public SocketChannel getChannel()
	{
		return this.connect == null ? null : this.connect.getChannel();
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
