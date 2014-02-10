
package com.elusivehawk.engine.network;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.UUID;
import com.google.common.collect.ImmutableList;

/**
 * 
 * Primary class for client-sided server interfacing.
 * <p>
 * Note: Because of the fact that it's a client, it only supports one connection at a time.
 * 
 * @author Elusivehawk
 */
public class Client implements IHost
{
	protected final IConnectionMaster master;
	protected final int ups;
	
	protected HandshakeConnection handshake = null;
	protected Connection connection = null;
	
	public Client(IConnectionMaster mstr)
	{
		this(mstr, 30);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Client(IConnectionMaster mstr, int updCount)
	{
		assert mstr != null;
		assert updCount > 0;
		
		master = mstr;
		ups = updCount;
		
	}
	
	@Override
	public Side getSide()
	{
		return Side.CLIENT;
	}
	
	@Override
	public void onPacketsReceived(Connection origin, ImmutableList<Packet> pkts)
	{
		this.master.onPacketsReceived(origin, pkts);
		
	}
	
	@Override
	public UUID connect(UUID id, IP ip, ConnectionType type)
	{
		if (this.connection != null)
		{
			if (type.isUdp() && this.connection.getConnectionId().equals(id))
			{
				this.connection.connect(id, ip, type);
				
			}
			
		}
		
		if (type.isUdp())
		{
			return null;
		}
		
		return this.connect(ip.toChannel());
	}
	
	@Override
	public UUID connect(SocketChannel s)
	{
		if (s == null || (this.handshake != null || this.connection != null))
		{
			return null;
		}
		
		this.handshake = new HandshakeConnection(this, s, UUID.randomUUID(), this.ups, this.master.getHandshakeProtocol());
		
		return this.handshake.getConnection().getConnectionId();
	}
	
	@Override
	public void beginComm()
	{
		if (this.handshake != null)
		{
			this.handshake.start();
			
		}
		
	}
	
	@Override
	public void sendPackets(UUID client, Packet... pkts)
	{
		if (this.connection == null)
		{
			return;
		}
		
		if (!this.connection.getConnectionId().equals(client))
		{
			return;
		}
		
		this.connection.sendPackets(pkts);
		
	}
	
	@Override
	public void sendPacketsExcept(UUID client, Packet... pkts)
	{
		assert client != null;
		
		if (!client.equals(this.connection.getConnectionId()))
		{
			this.sendPackets(this.connection.getConnectionId(), pkts);
			
		}
		
	}
	
	@Override
	public int getMaxPlayerCount()
	{
		return 1;
	}
	
	@Override
	public int getPlayerCount()
	{
		return this.connection == null ? 0 : 1;
	}
	
	@Override
	public ImmutableList<UUID> getConnectionIds()
	{
		return this.connection == null ? null : ImmutableList.copyOf(new UUID[]{this.connection.getConnectionId()});
	}
	
	@Override
	public void pauseConnections(boolean pause)
	{
		this.connection.setPaused(pause);
		
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
		
		this.handshake = null;
		
		connection.getConnection().close(!success);
		
		if (success)
		{
			this.connection = new Connection(this, this.ups);
			
			this.connection.connect(connection.getConnection().getChannel());
			this.connection.beginComm();
			
		}
		
	}
	
	@Override
	public void close() throws IOException
	{
		this.connection.close(true);
		this.connection = null;
		
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
		if (this.connection != connect)
		{
			throw new RuntimeException("Please quit pulling off coding voodoo.");
			
		}
		
		this.connection = null;
		
	}
	
}
