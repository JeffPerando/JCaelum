
package com.elusivehawk.engine.network;

import java.net.InetSocketAddress;
import java.net.Socket;
import javax.net.SocketFactory;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;

/**
 * 
 * 
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
	
	public String getAddress()
	{
		return this.ip;
	}
	
	public int getPort()
	{
		return this.port;
	}
	
	public InetSocketAddress toInet()
	{
		return InetSocketAddress.createUnresolved(this.ip, this.port);
	}
	
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
	
	@Override
	public String toString()
	{
		return this.ip + ":" + this.port;
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
