
package com.elusivehawk.engine.network;

import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.UUID;
import com.elusivehawk.engine.util.SemiFinalStorage;
import com.elusivehawk.engine.util.SimpleList;
import com.google.common.collect.ImmutableList;

/**
 * 
 * Primary class for server-sided interfacing.
 * 
 * @author Elusivehawk
 */
public class Server implements IHost
{
	protected final int port, maxPlayers;
	protected final INetworkMaster master;
	protected final ThreadJoinListener listener;
	protected final ThreadNetwork network;
	
	protected final List<IConnection> clients;
	protected final List<UUID> ids;
	protected final SemiFinalStorage<Boolean> disabled = new SemiFinalStorage<Boolean>(false);
	
	protected int playerCount = 0;
	protected boolean paused = false;
	
	@SuppressWarnings("unqualified-field-access")
	public Server(int p, INetworkMaster m, int players)
	{
		assert m != null;
		assert players > 0;
		
		port = p;
		master = m;
		listener = new ThreadJoinListener(this, p);
		network = new ThreadNetwork(this, p);
		maxPlayers = players;
		clients = new SimpleList<IConnection>(players, false);
		ids = new SimpleList<UUID>(players, false);
		
	}
	
	@Override
	public void beginComm()
	{
		this.listener.start();
		
	}
	
	@Override
	public UUID connect(UUID origin, IP ip, ConnectionType type)
	{
		if (type.isTcp())
		{
			return this.connect((SocketChannel)ip.toChannel(type));
		}
		else if (type.isUdp())
		{
			for (IConnection client : this.clients)
			{
				if (client.getId().equals(origin))
				{
					client.connect(type, ip);
					
					return client.getId();
				}
				
			}
			
		}
		
		return null;
	}
	
	@SuppressWarnings("resource")
	@Override
	public UUID connect(SocketChannel s)
	{
		assert s != null;
		
		IConnection next = new HSConnection(this, UUID.randomUUID());
		
		if (this.clients.add(next))
		{
			next.connect(ConnectionType.TCP, s);
			
			return next.getId();
		}
		
		try
		{
			next.close();
			
		}
		catch (Exception e){}
		
		return null;
	}
	
	@Override
	public void onPacketsReceived(IConnection origin, ImmutableList<Packet> pkts)
	{
		this.master.onPacketsReceived(origin, pkts);
		
	}
	
	@Override
	public Side getSide()
	{
		return Side.SERVER;
	}
	
	@Override
	public void sendPackets(UUID client, Packet... pkts)
	{
		if (client == null)
		{
			return;
		}
		
		for (IConnection connect : this.clients)
		{
			if (connect.getId().equals(client))
			{
				connect.sendPackets(pkts);
				break;
			}
			
		}
		
	}
	
	@Override
	public void sendPacketsExcept(UUID client, Packet... pkts)
	{
		for (IConnection connect : this.clients)
		{
			if (connect == null)
			{
				continue;
			}
			
			if (connect.getId().equals(client))
			{
				continue;
			}
			
			connect.sendPackets(pkts);
			
		}
		
	}
	
	@Override
	public int getMaxPlayerCount()
	{
		return this.maxPlayers;
	}
	
	@Override
	public int getPlayerCount()
	{
		return this.playerCount;
	}
	
	@Override
	public ImmutableList<UUID> getConnectionIds()
	{
		return ImmutableList.copyOf(this.ids);
	}
	
	@Override
	public void setPaused(boolean pause)
	{
		this.network.setPaused(pause);
		this.paused = pause;
		
	}
	
	@Override
	public boolean isPaused()
	{
		return this.paused;
	}
	
	@Override
	public short[] getHandshakeProtocol()
	{
		return this.master.getHandshakeProtocol();
	}
	
	@Override
	public void onHandshakeEnd(boolean success, IConnection connection, List<Packet> pkts)
	{
		this.master.onHandshakeEnd(success, connection, pkts);
		
		connection.close(!success);
		
		if (success)
		{
			IConnection connect = new Connection(this, UUID.randomUUID());
			
			boolean finish = true;
			
			if (connect.connect(ConnectionType.TCP, connection.getTcp()))
			{
				finish = false;
				
			}
			
			ImmutableList<DatagramChannel> udp = connection.getUdp();
			
			if (udp != null && !udp.isEmpty())
			{
				for (DatagramChannel ch : udp)
				{
					connect.connect(ConnectionType.UDP, ch);
					
				}
				
			}
			
			if (finish)
			{
				this.clients.add(connect);
				this.ids.add(connect.getId());
				this.playerCount++;
				
			}
			
		}
		
	}
	
	@Override
	public void close()
	{
		this.listener.stopThread();
		
		for (IConnection client : this.clients)
		{
			if (client != null)
			{
				client.close(true);
				
			}
			
		}
		
		for (int c = 0; c < this.maxPlayers; c++)
		{
			this.clients.set(c, null);
			
		}
		
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
		int i = this.clients.indexOf(connect);
		
		if (i == -1)
		{
			return;
		}
		
		this.clients.set(i, null);
		this.ids.remove(connect.getId());
		
		int players = 0;
		
		for (int c = 0; c < this.clients.size(); c++)
		{
			if (this.clients.get(c) != null)
			{
				players++;
				
			}
			
		}
		
		this.playerCount = players;
		
	}
	
}
