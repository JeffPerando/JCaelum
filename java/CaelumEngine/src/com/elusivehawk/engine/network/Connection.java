
package com.elusivehawk.engine.network;

import java.io.IOException;
import java.net.Socket;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class Connection
{
	private final ThreadNetworkIncoming in;
	private final ThreadNetworkOutgoing out;
	private final Socket skt;
	private final int connectId;
	
	public Connection(IPacketHandler h, Socket s, int ups) throws Exception
	{
		this(h, s, 0, ups);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Connection(IPacketHandler h, Socket s, int id, int ups) throws Exception
	{
		assert h != null;
		assert s != null;
		assert ups > 0;
		
		connectId = id;
		skt = s;
		in = new ThreadNetworkIncoming(h, this, ups);
		out = new ThreadNetworkOutgoing(h, this, ups);
		
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
	
	public void start()
	{
		this.in.start();
		this.out.start();
		
	}
	
	public void close(boolean closeSkt)
	{
		this.in.stopThread();
		this.out.stopThread();
		
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
	
	public static Connection create(IPacketHandler h, Socket s, int ups)
	{
		return create(h, s, 0, ups);
	}
	
	public static Connection create(IPacketHandler h, Socket s, int id, int ups)
	{
		Connection ret = null;
		
		try
		{
			ret = new Connection(h, s, id, ups);
			
		}
		catch (Exception e)
		{
			CaelumEngine.instance().getLog().log(EnumLogType.ERROR, null, e);
			
		}
		
		return ret;
	}
	
}
