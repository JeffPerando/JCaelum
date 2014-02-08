
package com.elusivehawk.engine.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import com.elusivehawk.engine.util.ThreadTimed;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ThreadJoinListener extends ThreadTimed
{
	protected final IHost svr;
	protected final ServerSocketChannel chnl;
	protected final int updCount;
	
	public ThreadJoinListener(IHost server, int port)
	{
		this(server, port, 30);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadJoinListener(IHost server, int port, int ups)
	{
		assert server != null;
		assert server.getSide().isServer();
		
		svr = server;
		updCount = ups;
		
		ServerSocketChannel ch = null;
		
		try
		{
			ch = ServerSocketChannel.open();
			ch.configureBlocking(false);
			
			ch.socket().bind(new InetSocketAddress(port));
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
			
		}
		
		chnl = ch;
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		SocketChannel sch = this.chnl.accept();
		
		if (sch != null)
		{
			this.svr.connect(sch);
			
		}
		
	}
	
	@Override
	public int getTargetUpdateCount()
	{
		return this.updCount;
	}
	
	@Override
	public double getMaxDelta()
	{
		return 0.5;
	}
	
	@Override
	public void onThreadStopped()
	{
		try
		{
			this.chnl.close();
			
		}
		catch (IOException e){}
		
	}
	
}
