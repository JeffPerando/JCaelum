
package com.elusivehawk.engine.network;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.elusivehawk.engine.util.SemiFinalStorage;
import com.google.common.collect.ImmutableList;

/**
 * 
 * Primary class for server-sided interfacing.
 * 
 * @author Elusivehawk
 */
public class Server implements IHost
{
	protected final int ups, port, maxPlayers;
	protected final IConnectionMaster master;
	protected final ThreadJoinListener listener;
	
	protected final List<IConnection> clients;
	protected final List<UUID> ids;
	protected final SemiFinalStorage<Boolean> disabled = new SemiFinalStorage<Boolean>(false);
	
	protected int playerCount = 0;
	protected boolean paused = false;
	
	public Server(int p, IConnectionMaster m, int players)
	{
		this(p, m, 30, players);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Server(int p, IConnectionMaster m, int updCount, int players)
	{
		assert m != null;
		assert updCount > 0;
		assert players > 0;
		
		port = p;
		master = m;
		listener = new ThreadJoinListener(this, p);
		ups = updCount;
		maxPlayers = players;
		clients = new ArrayList<IConnection>(players);
		ids = new ArrayList<UUID>(players);
		
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
			return this.connect(ip.toChannel());
		}
		else if (type.isUdp())
		{
			for (IConnection client : this.clients)
			{
				if (client.getId().equals(origin))
				{
					client.connect(origin, ip, type);
					
					return client.getId();
				}
				
			}
			
		}
		
		return null;
	}
	
	@Override
	public UUID connect(SocketChannel s)
	{
		assert s != null;
		
		IConnection next = ConnectionFactory.factory().createHS(this, s, UUID.randomUUID(), this.ups);
		int i = this.clients.indexOf(null);
		
		if (i == -1)
		{
			this.clients.set(i, next);
			
			next.beginComm();
			
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
		for (IConnection connect : this.clients)
		{
			if (connect == null)
			{
				continue;
			}
			
			connect.setPaused(pause);
			
		}
		
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
			IConnection connect = ConnectionFactory.factory().create(this, UUID.randomUUID(), this.ups);
			int i = this.clients.indexOf(null);
			
			if (i == -1)
			{
				try
				{
					connect.close();
					
				}
				catch (Exception e){}
				
				return;
			}
			
			this.clients.set(i, connect);
			this.ids.set(i, connect.getId());
			this.playerCount++;
			
			connect.connect(connection.getChannel());
			connect.beginComm();
			
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
