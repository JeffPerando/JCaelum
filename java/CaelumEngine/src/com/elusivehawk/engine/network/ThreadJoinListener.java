
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
	protected final Server svr;
	protected final ServerSocket skt;
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadJoinListener(Server server, int port)
	{
		assert server != null;
		
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
			
			this.svr.connectClient(s);
			
		}
		catch (Exception e)
		{
			this.handleException(e);
			
		}
		
	}
	
}
