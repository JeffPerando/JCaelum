
package com.elusivehawk.engine.network;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class Connection implements Closeable
{
	private final ThreadNetworkIncoming in;
	private final ThreadNetworkOutgoing out;
	private final IHost host;
	private final Socket skt;
	
	@SuppressWarnings("unqualified-field-access")
	public Connection(IHost h, Socket s, int ups) throws Exception
	{
		assert h != null;
		assert s != null;
		
		host = h;
		skt = s;
		in = new ThreadNetworkIncoming(h, s, ups);
		out = new ThreadNetworkOutgoing(h, s, ups);
		
	}
	
	public void start()
	{
		this.in.start();
		this.out.start();
		
	}
	
	public void sendPackets(Packet... pkts)
	{
		this.out.sendPackets(pkts);
		
	}
	
	@Override
	public void close() throws IOException
	{
		this.in.stopThread();
		this.out.stopThread();
		this.skt.close();
		
	}
	
}
