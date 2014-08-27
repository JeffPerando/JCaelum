
package com.elusivehawk.engine.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import com.elusivehawk.util.Internal;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.concurrent.ThreadStoppable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Internal
public class ThreadJoinListener extends ThreadStoppable
{
	protected final IHost svr;
	protected final int port;
	protected ServerSocketChannel chnl;
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadJoinListener(IHost server, int p)
	{
		assert server != null;
		assert server.getSide().isServer();
		
		svr = server;
		port = p;
		
	}
	
	@Override
	public boolean initiate()
	{
		ServerSocketChannel ch = null;
		
		try
		{
			ch = ServerSocketChannel.open();
			ch.configureBlocking(false);
			
			ch.bind(new InetSocketAddress(this.port));
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		
		this.chnl = ch;
		
		return true;
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
	public void onThreadStopped(boolean failure)
	{
		if (!failure)
		{
			try
			{
				this.chnl.close();
				
			}
			catch (IOException e)
			{
				Logger.log().err(e);
				
			}
			
		}
		
	}
	
}
