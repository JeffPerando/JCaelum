
package com.elusivehawk.engine.network;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import javax.net.SocketFactory;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;

/**
 * 
 * Lightweight class for storing IP addresses.
 * <p>
 * Note: This supports static IPs (i.e. 255.255.255.255:7777), as well as hostnames (i.e. example.hostname.com:7777).
 * 
 * @author Elusivehawk
 */
public final class IP
{
	private final String ip;
	private final int port;
	
	private IP(byte[] b, int p)
	{
		this(b[0] + "." + b[1] + "." + b[2] + "." + b[3], p);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	private IP(String a, int p)
	{
		ip = a;
		port = p;
		
	}
	
	/**
	 * 
	 * @return The hostname this IP address contains.
	 */
	public String getHostname()
	{
		return this.ip;
	}
	
	/**
	 * 
	 * @return The port this IP address contains.
	 */
	public int getPort()
	{
		return this.port;
	}
	
	/**
	 * 
	 * Converts the IP address into an {@link InetSocketAddress}.
	 * 
	 * @return The equivalent socket address.
	 */
	public InetSocketAddress toInet()
	{
		return InetSocketAddress.createUnresolved(this.ip, this.port);
	}
	
	/**
	 * 
	 * Converts the IP address into a {@link Socket}.
	 * 
	 * @return A socket, connected to the IP address.
	 */
	public Socket toSocket()
	{
		Socket ret = null;
		
		try
		{
			ret = SocketFactory.getDefault().createSocket(this.ip, this.port);
			
		}
		catch (Exception e)
		{
			CaelumEngine.instance().getLog().log(EnumLogType.ERROR, null, e);
			
		}
		
		return ret;
	}
	
	public SocketChannel toChannel()
	{
		SocketChannel ret = null;
		
		try
		{
			ret = SocketChannel.open(this.toInet());
			
		}
		catch (Exception e)
		{
			CaelumEngine.instance().getLog().log(EnumLogType.ERROR, null, e);
			
		}
		
		return ret;
	}
	
	@Override
	public String toString()
	{
		return this.ip + ":" + this.port;
	}
	
	@Override
	public int hashCode()
	{
		return this.toString().hashCode();
	}
	
	public static IP create(SocketAddress sa)
	{
		return create(sa.toString());
	}
	
	public static IP create(String ip)
	{
		assert ip != null;
		assert !"".equals(ip);
		
		if (!ip.contains(":"))
		{
			return null;
		}
		
		String[] str = ip.split(":");
		
		if (str.length != 2)
		{
			return null;
		}
		
		int port = 0;
		
		try
		{
			port = Integer.valueOf(str[1]);
			
		}
		catch (Exception e)
		{
			return null;
		}
		
		String[] array = str[0].split(".");
		
		if (array.length == 4)
		{
			byte[] bytes = new byte[4];
			
			for (int c = 0; c < 4; c++)
			{
				try
				{
					bytes[c] = Byte.valueOf(array[c]);
					
				}
				catch (Exception e)
				{
					return null;
				}
				
			}
			
			return new IP(bytes, port);
		}
		
		if (array.length == 3)
		{
			return new IP(str[0], port);
		}
		
		return null;
	}
	
}
