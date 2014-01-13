
package com.elusivehawk.engine.network;

import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ServerSocketFactory;
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
	protected final ServerSocket skt;
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadJoinListener(IHost server, int port)
	{
		assert server != null;
		assert server.getSide().isServer();
		
		ServerSocket s = null;
		
		try
		{
			s = ServerSocketFactory.getDefault().createServerSocket(port);
			
		}
		catch (Exception e)
		{
			handleException(e);
			
		}
		
		svr = server;
		skt = s;
		
	}
	
	@Override
	public void rawUpdate()
	{
		try
		{
			Socket s = this.skt.accept();
			
			this.svr.connect(s);
			
		}
		catch (Exception e)
		{
			this.handleException(e);
			
		}
		
	}
	
}
