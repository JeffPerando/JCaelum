
package com.elusivehawk.engine.network;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Server
{
	@SuppressWarnings("static-method")
	public void connect(String ip, int ups)
	{
		assert ip != null;
		
		if (ip.contains(":"))
		{
			String[] split = ip.split(":");
			
			assert split.length == 2;
			
			Short port = null;
			
			try
			{
				port = Short.valueOf(split[1]);
				
			}
			catch (Exception e){}
			
			if (port != null)
			{
				//TODO Connect
				
			}
			
		}
		
	}
	
}
