
package com.elusivehawk.engine.network;

import java.net.Socket;

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
	private final IHost host;
	private final Socket skt;
	
	@SuppressWarnings("unqualified-field-access")
	public Connection(IHost h, Socket s)
	{
		assert h != null;
		assert s != null;
		
		in = null;
		out = null;
		host = h;
		skt = s;
		
	}
	
}
