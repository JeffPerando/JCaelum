
package com.elusivehawk.engine.network;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import com.elusivehawk.engine.util.SemiFinalStorage;
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
	
	protected final SemiFinalStorage<HandshakeConnection> handshake = new SemiFinalStorage<HandshakeConnection>(null);
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
		if (this.handshake.locked())
		{
			return;
		}
		
		this.connect(ip.toSocket());
		
	}
	
	@Override
	public void connect(Socket s)
	{
		if (s == null || this.handshake.locked())
		{
			return;
		}
		
		this.handshake.set(new HandshakeConnection(this, s, 0, this.ups, this.master.getHandshakeProtocol()));
		
	}
	
	@Override
	public void beginComm()
	{
		this.handshake.get().start();
		
	}
	
	@Override
	public void sendPackets(int client, Packet... pkts)
	{
		if (this.connection == null)
		{
			return;
		}
		
		this.connection.sendPackets(pkts);
		
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
	public void onHandshakeEnd(boolean success, Connection connection, List<Packet> pkts)
	{
		this.master.onHandshakeEnd(success, connection, pkts);
		
		connection.close(!success);
		
		if (success)
		{
			this.connection = new Connection(this, this.ups);
			
			this.connection.connect(connection.getSocket());
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
