
package com.elusivehawk.engine.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import com.elusivehawk.engine.util.ThreadStoppable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ThreadJoinListener extends ThreadStoppable
{
	protected final IHost svr;
	protected final ServerSocketChannel chnl;
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadJoinListener(IHost server, int port)
	{
		assert server != null;
		assert server.getSide().isServer();
		
		svr = server;
		
		ServerSocketChannel ch = null;
		
		try
		{
			ch = ServerSocketChannel.open();
			ch.configureBlocking(false);
			
			ch.bind(new InetSocketAddress(port));
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
			
		}
		
		chnl = ch;
		
	}
	
	@Override
	public void rawUpdate() throws Throwable
	{
		SocketChannel sch = this.chnl.accept();
		
		if (sch != null)
		{
			this.svr.connect(sch);
			
		}
		
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
