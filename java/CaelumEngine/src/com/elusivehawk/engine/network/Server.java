
package com.elusivehawk.engine.network;

import java.net.Socket;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Server
{
	protected final IP ip;
	protected final int ups;
	
	protected Socket skt = null;
	
	public Server(String ip)
	{
		this(IP.create(ip));
		
	}
	
	public Server(String ip, int ups)
	{
		this(IP.create(ip), ups);
		
	}
	
	public Server(IP ip)
	{
		this(ip, 30);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Server(IP ipAddress, int updCount)
	{
		assert ipAddress != null;
		assert updCount > 0;
		
		ip = ipAddress;
		ups = updCount;
		
	}
	
	public void connect()
	{
		
	}
	
}
