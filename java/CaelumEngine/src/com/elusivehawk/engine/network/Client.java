
package com.elusivehawk.engine.network;

import java.io.IOException;
import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Client extends HostImpl
{
	protected final IConnectionMaster master;
	
	@SuppressWarnings("unqualified-field-access")
	public Client(IConnectionMaster mstr)
	{
		assert mstr != null;
		
		master = mstr;
		
	}
	
	@Override
	public Side getSide()
	{
		return Side.CLIENT;
	}
	
	@Override
	public void onPacketsReceived(Connection origin, List<Packet> pkts)
	{
		this.master.onPacketsReceived(origin, pkts);
		
	}
	
	@Override
	public void beginCommunication()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void sendPackets(int client, Packet... pkts)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onHandshakeEnd(boolean success, Connection connection,
			List<Packet> pkts)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void close() throws IOException
	{
		// TODO Auto-generated method stub
		
	}
	
}
