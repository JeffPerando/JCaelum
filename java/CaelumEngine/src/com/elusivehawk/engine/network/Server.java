
package com.elusivehawk.engine.network;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import com.elusivehawk.engine.util.SemiFinalStorage;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Server extends HostImpl
{
	protected final int ups;
	protected final IP ip;
	protected final IConnectionMaster master;
	protected final ThreadJoinListener listener;
	
	protected final List<HandshakeClient> handshakers = new ArrayList<HandshakeClient>();
	protected final List<Connection> clients = new ArrayList<Connection>();
	protected final SemiFinalStorage<Boolean> disabled = new SemiFinalStorage<Boolean>(false);
	
	protected int nextConnectionId = 0;
	
	public Server(String ip, IConnectionMaster m)
	{
		this(IP.create(ip), m);
		
	}
	
	public Server(String ip, IConnectionMaster m, int ups)
	{
		this(IP.create(ip), m, ups);
		
	}
	
	public Server(IP ip, IConnectionMaster m)
	{
		this(ip, m, 30);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Server(IP ipAddress, IConnectionMaster m, int updCount)
	{
		assert ipAddress != null;
		assert m != null;
		assert updCount > 0;
		
		ip = ipAddress;
		master = m;
		listener = new ThreadJoinListener(this, ip.getPort());
		ups = updCount;
		
	}
	
	@Override
	public void beginCommunication()
	{
		this.listener.start();
		
	}
	
	@Override
	public void onPacketsReceived(Connection origin, List<Packet> pkts)
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
	public void onHandshakeEnd(boolean success, Connection connection, List<Packet> pkts)
	{
		this.master.onHandshakeEnd(success, connection, pkts);
		
		connection.close(!success);
		
		if (success)
		{
			Connection connect = Connection.create(this, connection.getSocket(), ++this.nextConnectionId, this.ups);
			
			if (connect != null)
			{
				this.clients.add(connect);
				
			}
			
		}
		
	}
	
	public synchronized void connectClient(Socket s)//TODO Check if this causes a deadlock.
	{
		++this.nextConnectionId;
		
		HandshakeClient next = new HandshakeClient(this, s, this.nextConnectionId, this.ups, this.master.getHandshakeProtocol());
		
		this.handshakers.add(next);
		
		next.start();
		
	}
	
	@Override
	public void close() throws IOException
	{
		this.listener.stopThread();
		
		
	}
	
}
