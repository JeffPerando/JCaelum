
package com.elusivehawk.engine.network;

import java.io.IOException;
import java.net.Socket;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;
import com.elusivehawk.engine.util.SemiFinalStorage;

/**
 * 
 * Handles interaction with connection threads.
 * 
 * @author Elusivehawk
 */
public final class Connection implements IConnectable
{
	private final IPacketHandler handler;
	private final int connectId;
	private final int updCount;
	private final SemiFinalStorage<Boolean> closed = new SemiFinalStorage<Boolean>(false);
	
	private ThreadNetworkIncoming in = null;
	private ThreadNetworkOutgoing out = null;
	private Socket skt = null;
	
	public Connection(IPacketHandler h, int ups)
	{
		this(h, 0, ups);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Connection(IPacketHandler h, int id, int ups)
	{
		assert h != null;
		assert ups > 0;
		
		handler = h;
		connectId = id;
		updCount = ups;
		
	}
	
	public Socket getSocket()
	{
		return this.skt;
	}
	
	public int getConnectionId()
	{
		return this.connectId;
	}
	
	public void sendPackets(Packet... pkts)
	{
		this.out.sendPackets(pkts);
		
	}
	
	@Override
	public void connect(IP ip)
	{
		this.connect(ip.toSocket());
		
	}
	
	@Override
	public void connect(Socket s)
	{
		if (s == null)
		{
			return;
		}
		
		this.skt = s;
		this.in = new ThreadNetworkIncoming(this.handler, this, this.updCount);
		this.out = new ThreadNetworkOutgoing(this.handler, this, this.updCount);
		
	}
	
	@Override
	public void beginComm()
	{
		this.in.start();
		this.out.start();
		
	}
	
	public void setPaused(boolean pause)
	{
		this.in.setPaused(pause);
		this.out.setPaused(pause);
		
	}
	
	public boolean isClosed()
	{
		return this.closed.get();
	}
	
	public void close(boolean closeSkt)
	{
		if (this.isClosed())
		{
			return;
		}
		
		this.handler.onDisconnect(this);
		
		this.in.stopThread();
		this.out.stopThread();
		
		this.in = null;
		this.out = null;
		
		this.closed.set(true);
		
		if (closeSkt)
		{
			try
			{
				this.skt.close();
				
			}
			catch (IOException e)
			{
				CaelumEngine.instance().getLog().log(EnumLogType.ERROR, null, e);
				
			}
			
		}
		
	}
	
}
