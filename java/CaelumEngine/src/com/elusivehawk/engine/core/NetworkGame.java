
package com.elusivehawk.engine.core;

import java.io.IOException;
import com.elusivehawk.engine.network.Client;
import com.elusivehawk.engine.network.IHost;
import com.elusivehawk.engine.network.INetworkMaster;
import com.elusivehawk.engine.network.Server;
import com.elusivehawk.engine.network.Side;

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
	
	public abstract int getMaxPlayerCount(GameArguments args);
	
	@Override
	protected void initiate(GameArguments args) throws Throwable
	{
		if (this.side == Side.CLIENT)
		{
			this.host = new Client(this, this.port);
			
		}
		else if (this.side == Side.SERVER)
		{
			this.host = new Server(this, this.port, this.getMaxPlayerCount(args));
			
		}
		
	}
	
	@Override
	protected void onGameShutdown()
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
