
package com.elusivehawk.engine.network;

import java.io.Closeable;
import java.util.UUID;
import com.elusivehawk.util.IPausable;
import com.elusivehawk.util.storage.SemiFinalStorage;
import com.google.common.collect.ImmutableList;

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
	
	default Object getAttachment(UUID client)
	{
		if (client == null)
		{
			return null;
		}
		
		SemiFinalStorage<Object> ret = SemiFinalStorage.create(null);
		
		this.forEveryConnection(((con) ->
		{
			if (con.getId().equals(client))
			{
				ret.set(con.getAttachment());
				
			}
			
			return !ret.locked();
		}));
		
		return ret.get();
	}
	
	default boolean setAttachment(UUID client, Object a)
	{
		if (client == null)
		{
			return false;
		}
		
		SemiFinalStorage<Boolean> found = SemiFinalStorage.create(false);
		
		this.forEveryConnection(((con) ->
		{
			if (con.getId().equals(client))
			{
				con.setAttachment(a);
				found.set(true);
				
			}
			
			return !found.locked();
		}));
		
		return found.get();
	}
	
	public void forEveryConnection(IConnectionUser user);
	
	public int getMaxPlayerCount();
	
	public int getPlayerCount();
	
	public void onHandshake(Connection connection, ImmutableList<Packet> pkts);
	
}
