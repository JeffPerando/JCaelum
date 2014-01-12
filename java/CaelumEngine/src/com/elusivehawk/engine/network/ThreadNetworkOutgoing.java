
package com.elusivehawk.engine.network;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ThreadNetworkOutgoing extends ThreadNetwork
{
	private BufferedOutputStream bos = null;
	private DataOutputStream out = null;
	
	public ThreadNetworkOutgoing(IHost host, Socket skt, int ups)
	{
		super(host, skt, ups);
		
	}
	
	@Override
	public boolean initiate()
	{
		OutputStream os = null;
		
		try
		{
			os = this.skt.getOutputStream();
			
		}
		catch (Exception e)
		{
			return false;
		}
		
		this.bos = new BufferedOutputStream(os);
		this.out = new DataOutputStream(this.bos);
		
		return false;
	}
	
	@Override
	public void update(double delta)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onThreadStopped()
	{
		try
		{
			this.out.close();
			
		}
		catch (Exception e)
		{
			this.handleException(e);
			
		}
		
	}
	
}
