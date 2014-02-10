
package com.elusivehawk.engine.network;

import java.io.IOException;
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
	
	protected final List<HandshakeConnection> handshakers;
	protected final List<Connection> clients;
	protected final List<UUID> ids;
	protected final SemiFinalStorage<Boolean> disabled = new SemiFinalStorage<Boolean>(false);
	
	protected int playerCount = 0;
	
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
		handshakers = new ArrayList<HandshakeConnection>(players);
		clients = new ArrayList<Connection>(players);
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
			for (Connection client : this.clients)
			{
				if (client.getConnectionId().equals(origin))
				{
					client.connect(origin, ip, type);
					
					return client.getConnectionId();
				}
				
			}
			
		}
		
		return null;
	}
	
	@Override
	public UUID connect(SocketChannel s)
	{
		assert s != null;
		
		HandshakeConnection next = new HandshakeConnection(this, s, UUID.randomUUID(), this.ups, this.master.getHandshakeProtocol());
		int i = this.handshakers.indexOf(null);
		
		if (i == -1)
		{
			return null;
		}
		
		this.handshakers.set(i, next);
		
		next.start();
		
		return next.getConnection().getConnectionId();
	}
	
	@Override
	public void onPacketsReceived(Connection origin, ImmutableList<Packet> pkts)
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
		
		for (Connection connect : this.clients)
		{
			if (connect.getConnectionId().equals(client))
			{
				connect.sendPackets(pkts);
				break;
			}
			
		}
		
	}
	
	@Override
	public void sendPacketsExcept(UUID client, Packet... pkts)
	{
		for (Connection connect : this.clients)
		{
			if (connect == null)
			{
				continue;
			}
			
			if (connect.getConnectionId().equals(client))
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
	public void pauseConnections(boolean pause)
	{
		for (Connection connect : this.clients)
		{
			connect.setPaused(pause);
			
		}
		
	}
	
	@Override
	public short[] getHandshakeProtocol()
	{
		return this.master.getHandshakeProtocol();
	}
	
	@Override
	public void onHandshakeEnd(boolean success, HandshakeConnection connection, List<Packet> pkts)
	{
		this.master.onHandshakeEnd(success, connection, pkts);
		
		connection.getConnection().close(!success);
		
		if (success)
		{
			this.handshakers.remove(connection);
			
			Connection connect = new Connection(this, UUID.randomUUID(), this.ups);
			int i = this.clients.indexOf(null);
			
			if (i == -1)
			{
				return;
			}
			
			this.clients.set(i, connect);
			this.ids.set(i, connect.getConnectionId());
			this.playerCount++;
			
			connect.connect(connection.getConnection().getChannel());
			connect.beginComm();
			
		}
		
	}
	
	@Override
	public void close() throws IOException
	{
		this.listener.stopThread();
		
		for (Connection client : this.clients)
		{
			client.close(true);
			
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
	public void onDisconnect(Connection connect)
	{
		this.clients.remove(connect);
		this.ids.remove(connect.getConnectionId());
		this.playerCount--;
		
	}
	
}
