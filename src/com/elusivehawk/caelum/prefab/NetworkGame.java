
package com.elusivehawk.caelum.prefab;

import java.io.IOException;
import com.elusivehawk.caelum.Game;
import com.elusivehawk.caelum.network.Client;
import com.elusivehawk.caelum.network.ConnectionType;
import com.elusivehawk.caelum.network.IHost;
import com.elusivehawk.caelum.network.INetworkMaster;
import com.elusivehawk.caelum.network.Server;
import com.elusivehawk.caelum.network.Side;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class NetworkGame extends Game implements INetworkMaster
{
	protected final Side side;
	protected final int port;
	
	protected IHost host = null;
	
	@SuppressWarnings("unqualified-field-access")
	protected NetworkGame(String title, Side s, int gameport)
	{
		super(title);
		
		if (s == null)
		{
			throw new NullPointerException("Side cannot be null!");
		}
		
		side = s;
		port = gameport;
		
	}
	
	public abstract int getMaxPlayerCount();
	
	@Override
	public void preInit()
	{
		switch (this.side)
		{
			case CLIENT: this.host = new Client(this);
			case SERVER: this.host = new Server(this, this.port, this.getMaxPlayerCount());
		}
		
	}
	
	@Override
	public void initiate() throws Throwable
	{
		if (this.side != Side.SERVER)
		{
			this.host.connect(ConnectionType.TCP, this.port);
			
		}
		
		this.host.beginComm();
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		if (this.host != null)
		{
			this.host.receivePackets();
			
		}
		
	}
	
	@Override
	public void onShutdown()
	{
		if (this.host != null)
		{
			try
			{
				this.host.close();
				
			}
			catch (IOException e){}
			
		}
		
	}
	
}
