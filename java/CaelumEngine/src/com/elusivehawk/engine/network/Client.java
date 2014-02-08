
package com.elusivehawk.engine.network;

import java.io.IOException;
import java.net.Socket;
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
	public void connect(IP ip)
	{
		if (this.handshake != null || this.connection != null)
		{
			return;
		}
		
		this.connect(ip.toSocket());
		
	}
	
	@Override
	public void connect(Socket s)
	{
		if (s == null || (this.handshake != null || this.connection != null))
		{
			return;
		}
		
		this.handshake = new HandshakeConnection(this, s, null, this.ups, this.master.getHandshakeProtocol());
		
	}
	
	@Override
	public void beginComm()
	{
		if (this.handshake == null)
		{
			return;
		}
		
		this.handshake.start();
		
	}
	
	@Override
	public void sendPackets(UUID client, Packet... pkts)
	{
		if (this.connection == null || client != null)
		{
			return;
		}
		
		this.connection.sendPackets(pkts);
		
	}
	
	@Override
	public void sendPacketsExcept(UUID client, Packet... pkts)
	{
		this.sendPackets(null, pkts);
		
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
			
			this.connection.connect(connection.getConnection().getSocket());
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
	public void onDisconnect(Connection connect)
	{
		if (this.connection != connect)
		{
			throw new RuntimeException("Please quit pulling off coding voodoo.");
			
		}
		
		this.connection = null;
		
	}
	
}
