
package com.elusivehawk.engine.network;

import java.io.Closeable;
import java.util.List;
import java.util.UUID;
import com.elusivehawk.util.IPausable;

/**
 * 
 * Interface for "host" objects (Client, server, etc.)
 * <p>
 * Note: It's recommended that you not implement this yourself.
 * 
 * @author Elusivehawk
 */
public interface IHost extends IConnectable, IPacketHandler, Closeable, IPausable
{
	/**
	 * 
	 * Called when something wants to send packets.
	 * 
	 * @param client The connection ID to use, null if it's client -> server communication.
	 * @param pkts The packets to send.
	 */
	default void sendPackets(UUID client, Packet... pkts)
	{
		this.forEveryConnection(((con) ->
		{
			if (client == null || con.getId().equals(client))
			{
				con.sendPackets(pkts);
				
				return false;
			}
			
			return true;
		}));
		
	}
	
	default void sendPacketsExcept(UUID client, Packet... pkts)
	{
		this.forEveryConnection(((con) ->
		{
			if (client == null || !con.getId().equals(client))
			{
				con.sendPackets(pkts);
				
			}
			
			return true;
		}));
		
	}
	
	public void forEveryConnection(IConnectionUser user);
	
	public int getMaxPlayerCount();
	
	public int getPlayerCount();
	
	public void onHandshake(IConnection connection, List<Packet> pkts);
	
}
