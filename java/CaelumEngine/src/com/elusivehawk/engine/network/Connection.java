
package com.elusivehawk.engine.network;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.NetworkChannel;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.UUID;
import com.elusivehawk.engine.util.SemiFinalStorage;
import com.elusivehawk.engine.util.SimpleList;
import com.google.common.collect.ImmutableList;

/**
 * 
 * Handles interaction with the networking thread.
 * 
 * @author Elusivehawk
 */
public class Connection implements IConnection
{
	protected final IPacketHandler handler;
	protected final UUID connectId;
	
	private final SemiFinalStorage<Boolean> closed = new SemiFinalStorage<Boolean>(false);
	
	private SocketChannel tcp = null;
	private List<DatagramChannel> udp = SimpleList.newList();
	
	private List<Packet> incoming = SimpleList.newList();
	
	@SuppressWarnings("unqualified-field-access")
	public Connection(IPacketHandler h, UUID id)
	{
		assert h != null;
		assert id != null;
		
		handler = h;
		connectId = id;
		
	}
	
	@Override
	public boolean connect(ConnectionType type, IP ip)
	{
		return this.connect(type, ip.toChannel(type));
	}
	
	@Override
	public boolean connect(ConnectionType type, NetworkChannel s)
	{
		if (s == null)
		{
			return false;
		}
		
		if (type.isTcp())
		{
			if (this.tcp != null)
			{
				return false;
			}
			
			this.tcp = (SocketChannel)s;
			
		}
		else
		{
			this.udp.add((DatagramChannel)s);
			
		}
		
		return true;
	}
	
	@Override
	public void close()
	{
		this.close(true);
		
	}
	
	@Override
	public UUID getId()
	{
		return this.connectId;
	}
	
	@Override
	public SocketChannel getTcp()
	{
		return this.tcp;
	}
	
	@Override
	public ImmutableList<DatagramChannel> getUdp()
	{
		return ImmutableList.copyOf(this.udp);
	}
	
	@Override
	public ImmutableList<Packet> getOutgoingPackets()
	{
		if (this.incoming.isEmpty())
		{
			return null;
		}
		
		return ImmutableList.copyOf(this.incoming);
	}
	
	@Override
	public void clearPkt(Packet pkt)
	{
		this.incoming.remove(pkt);
		
	}
	
	@Override
	public synchronized void sendPackets(Packet... pkts)
	{
		for (Packet pkt : pkts)
		{
			this.incoming.add(pkt);
			
		}
		
	}
	
	@Override
	public boolean isClosed()
	{
		return this.closed.get();
	}
	
	@Override
	public void close(boolean closeSkt)
	{
		if (this.isClosed())
		{
			return;
		}
		
		this.handler.onDisconnect(this);
		
		this.closed.set(true);
		
		if (closeSkt)
		{
			try
			{
				this.tcp.close();
				
				for (DatagramChannel ch : this.udp)
				{
					ch.close();
					
				}
				
			}
			catch (IOException e)
			{
				e.printStackTrace();
				
			}
			
		}
		
	}

}
