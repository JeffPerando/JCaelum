
package com.elusivehawk.engine.network;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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
	protected final int ups, port;
	protected final IConnectionMaster master;
	protected final ThreadJoinListener listener;
	
	protected final List<HandshakeConnection> handshakers = new ArrayList<HandshakeConnection>();
	protected final List<Connection> clients = new ArrayList<Connection>();
	protected final SemiFinalStorage<Boolean> disabled = new SemiFinalStorage<Boolean>(false);
	
	protected int nextConnectionId = 0;
	
	public Server(int p, IConnectionMaster m)
	{
		this(p, m, 30);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Server(int p, IConnectionMaster m, int updCount)
	{
		assert m != null;
		assert updCount > 0;
		
		port = p;
		master = m;
		listener = new ThreadJoinListener(this, p);
		ups = updCount;
		
	}
	
	@Override
	public void beginComm()
	{
		this.listener.start();
		
	}
	
	@Override
	public void connect(IP ip){}
	
	@Override
	public synchronized void connect(Socket s)//TODO Check if this causes a deadlock.
	{
		++this.nextConnectionId;
		
		HandshakeConnection next = new HandshakeConnection(this, s, this.nextConnectionId, this.ups, this.master.getHandshakeProtocol());
		
		this.handshakers.add(next);
		
		next.start();
		
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
	public void sendPackets(int client, Packet... pkts)
	{
		for (Connection connect : this.clients)
		{
			if (connect.getConnectionId() == client)
			{
				connect.sendPackets(pkts);
				
			}
			
		}
		
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
	public void onHandshakeEnd(boolean success, Connection connection, List<Packet> pkts)
	{
		this.master.onHandshakeEnd(success, connection, pkts);
		
		connection.close(!success);
		
		if (success)
		{
			Connection connect = Connection.create(this, ++this.nextConnectionId, this.ups);
			
			if (connect != null)
			{
				this.clients.add(connect);
				
				connect.connect(connection.getSocket());
				connect.beginComm();
				
			}
			
		}
		
	}
	
	@Override
	public void close() throws IOException
	{
		this.listener.stopThread();
		
	}
	
	@Override
	public PacketFormat getPacketFormat(short id)
	{
		return this.master.getPacketFormat(id);
	}
	
	@Override
	public void onDisconnect(Connection connect)
	{
		this.clients.remove(connect);
		
	}
	
}
