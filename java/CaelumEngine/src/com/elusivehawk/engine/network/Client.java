
package com.elusivehawk.engine.network;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import com.elusivehawk.engine.util.SemiFinalStorage;

/**
 * 
 * 
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
	public void onPacketsReceived(Connection origin, List<Packet> pkts)
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
	public void onHandshakeEnd(boolean success, Connection connection, List<Packet> pkts)
	{
		this.master.onHandshakeEnd(success, connection, pkts);
		
		connection.close(!success);
		
		if (success)
		{
			this.connection = Connection.create(this, this.ups);
			
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
	public void addPacketFormat(PacketFormat format)
	{
		this.master.addPacketFormat(format);
		
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
