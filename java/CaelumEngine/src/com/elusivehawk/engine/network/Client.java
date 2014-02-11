
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
	
	protected IConnection connection = null;
	protected boolean hasHS = false;
	
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
	public void onPacketsReceived(IConnection origin, ImmutableList<Packet> pkts)
	{
		this.master.onPacketsReceived(origin, pkts);
		
	}
	
	@Override
	public UUID connect(UUID id, IP ip, ConnectionType type)
	{
		if (this.connection != null)
		{
			if (type.isUdp() && this.connection.getId().equals(id))
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
		if (s == null || (this.connection != null || this.hasHS))
		{
			return null;
		}
		
		this.connection = ConnectionFactory.factory().createHS(this, UUID.randomUUID(), this.ups);
		
		this.connection.connect(s);
		
		return this.connection.getId();
	}
	
	@Override
	public void beginComm()
	{
		if (this.connection != null)
		{
			this.connection.beginComm();
			
		}
		
	}
	
	@Override
	public void sendPackets(UUID client, Packet... pkts)
	{
		if (this.connection == null)
		{
			return;
		}
		
		if (!this.connection.getId().equals(client))
		{
			return;
		}
		
		this.connection.sendPackets(pkts);
		
	}
	
	@Override
	public void sendPacketsExcept(UUID client, Packet... pkts)
	{
		assert client != null;
		
		if (!client.equals(this.connection.getId()))
		{
			this.sendPackets(this.connection.getId(), pkts);
			
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
		return this.connection == null ? null : ImmutableList.copyOf(new UUID[]{this.connection.getId()});
	}
	
	@Override
	public void setPaused(boolean pause)
	{
		if (this.connection != null)
		{
			this.connection.setPaused(pause);
			
		}
		
	}
	
	@Override
	public boolean isPaused()
	{
		return this.connection == null ? false : this.connection.isPaused();
	}
	
	@Override
	public short[] getHandshakeProtocol()
	{
		return this.master.getHandshakeProtocol();
	}
	
	@Override
	public void onHandshakeEnd(boolean success, IConnection connection, List<Packet> pkts)
	{
		if (this.hasHS)
		{
			return;
		}
		
		this.master.onHandshakeEnd(success, connection, pkts);
		
		connection.close(!success);
		
		if (success)
		{
			this.connection = ConnectionFactory.factory().create(this, UUID.randomUUID(), this.ups);
			
			this.connection.connect(connection.getChannel());
			this.connection.beginComm();
			
			this.hasHS = true;
			
		}
		else
		{
			this.connection = null;
			
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
	public void onDisconnect(IConnection connect)
	{
		if (this.connection != connect)
		{
			throw new RuntimeException("Please quit pulling off coding voodoo.");
			
		}
		
		this.connection = null;
		this.hasHS = false;
		
	}
	
}
