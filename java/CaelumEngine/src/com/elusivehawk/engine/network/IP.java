
package com.elusivehawk.engine.network;


/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class IP
{
	private final byte[] ip;
	private final int port;
	
	@SuppressWarnings("unqualified-field-access")
	private IP(byte[] b, int p)
	{
		assert b != null;
		assert b.length == 4;
		
		ip = b;
		port = p;
		
	}
	
	public String getAddress()
	{
		String ret = "";
		
		for (int c = 0; c < 4; c++)
		{
			ret += this.ip[c];
			
			if (c != 3)
			{
				ret += ".";
				
			}
			
		}
		
		return ret;
	}
	
	public int getPort()
	{
		return this.port;
	}
	
	@Override
	public String toString()
	{
		String ret = this.getAddress();
		
		ret += ":";
		ret += this.port;
		
		return ret;
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
		
		String[] array = str[0].split(".");
		
		if (array.length != 4)
		{
			return null;
		}
		
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
		
		int port = 0;
		
		try
		{
			port = Integer.valueOf(str[1]);
			
		}
		catch (Exception e)
		{
			return null;
		}
		
		return new IP(bytes, port);
	}
	
}
