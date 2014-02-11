
package com.elusivehawk.engine.network;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.UUID;
import com.elusivehawk.engine.util.SemiFinalStorage;

/**
 * 
 * Handles interaction with connection threads.
 * 
 * @author Elusivehawk
 */
public class ConnectionImpl implements IConnection
{
	protected final IPacketHandler handler;
	
	private final UUID connectId;
	private final int updCount;
	private final SemiFinalStorage<Boolean> closed = new SemiFinalStorage<Boolean>(false);
	
	protected ThreadNetwork io = null;
	
	private SocketChannel sch = null;
	
	@SuppressWarnings("unqualified-field-access")
	public ConnectionImpl(IPacketHandler h, UUID id, int ups)
	{
		assert h != null;
		assert id != null;
		assert ups > 0;
		
		handler = h;
		connectId = id;
		updCount = ups;
		
	}
	
	@Override
	public UUID connect(UUID id, IP ip, ConnectionType type)
	{
		if (!this.connectId.equals(id))
		{
			return null;
		}
		
		if (type.isTcp())
		{
			return this.connect(ip.toChannel());
		}
		
		if (type.isUdp())
		{
			if (this.io == null)
			{
				return null;
			}
			
			this.io.connectDatagram(ip);
			
			return this.connectId;
		}
		
		return null;
	}
	
	@Override
	public UUID connect(SocketChannel s)
	{
		if (s == null || this.io != null)
		{
			return null;
		}
		
		this.sch = s;
		this.io = new ThreadNetwork(this.handler, this, this.updCount);
		
		return this.connectId;
	}
	
	@Override
	public void beginComm()
	{
		if (this.io.isAlive())
		{
			return;
		}
		
		this.io.start();
		
	}
	
	@Override
	public boolean isPaused()
	{
		return this.io.isPaused();
	}
	
	@Override
	public void setPaused(boolean p)
	{
		this.io.setPaused(p);
		
	}
	
	@Override
	public void close()
	{
		this.close(true);
		
	}
	
	@Override
	public UUID getId()
	{
		return this.connectId;
	}
	
	@Override
	public SocketChannel getChannel()
	{
		return this.sch;
	}
	
	@Override
	public void sendPackets(Packet... pkts)
	{
		this.io.sendPackets(pkts);
		
	}
	
	@Override
	public boolean isClosed()
	{
		return this.closed.get();
	}
	
	@Override
	public void close(boolean closeSkt)
	{
		if (this.isClosed())
		{
			return;
		}
		
		this.handler.onDisconnect(this);
		
		this.io.stopThread();
		
		this.io = null;
		
		this.closed.set(true);
		
		if (closeSkt)
		{
			try
			{
				this.sch.close();
				
			}
			catch (IOException e)
			{
				e.printStackTrace();
				
			}
			
		}
		
	}
	
}
