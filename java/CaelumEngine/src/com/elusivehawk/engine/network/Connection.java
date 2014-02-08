
package com.elusivehawk.engine.network;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.UUID;
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
	private final UUID connectId;
	private final int updCount;
	private final SemiFinalStorage<Boolean> closed = new SemiFinalStorage<Boolean>(false);
	
	private ThreadNetwork io = null;
	private SocketChannel sch = null;
	
	public Connection(IPacketHandler h, int ups)
	{
		this(h, null, ups);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Connection(IPacketHandler h, UUID id, int ups)
	{
		assert h != null;
		assert ups > 0;
		
		handler = h;
		connectId = id;
		updCount = ups;
		
	}
	
	public SocketChannel getChannel()
	{
		return this.sch;
	}
	
	public UUID getConnectionId()
	{
		return this.connectId;
	}
	
	public void sendPackets(Packet... pkts)
	{
		this.io.sendPackets(pkts);
		
	}
	
	@Override
	public void connect(IP ip){}//TODO Never used
	
	@Override
	public void connect(SocketChannel s)
	{
		if (s != null)
		{
			return;
		}
		
		this.sch = s;
		this.io = new ThreadNetwork(this.handler, this, this.updCount);
		
	}
	
	@Override
	public void beginComm()
	{
		this.io.start();
		
	}
	
	public void setPaused(boolean pause)
	{
		this.io.setPaused(pause);
		
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
				CaelumEngine.instance().getLog().log(EnumLogType.ERROR, null, e);
				
			}
			
		}
		
	}
	
}
