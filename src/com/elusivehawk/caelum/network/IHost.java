
package com.elusivehawk.caelum.network;

import java.io.Closeable;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.util.UUID;
import com.elusivehawk.util.IPausable;
import com.elusivehawk.util.storage.SemiFinalStorage;

/**
 * 
 * Interface for "host" objects (Client, server, etc.)
 * <p>
 * Note: It's recommended that you not implement this yourself.
 * 
 * @author Elusivehawk
 */
public interface IHost extends IPacketHandler, Closeable, IPausable
{
	UUID connect(AbstractSelectableChannel ch);
	
	void beginComm();
	
	void forEveryConnection(IConnectionUser user);
	
	int getMaxPlayerCount();
	
	int getPlayerCount();
	
	void onHandshake(Connection connection, Packet pkt);
	
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
	
	default void addPacketListener(UUID id, IPacketListener lis)
	{
		this.forEveryConnection(((con) ->
		{
			if (id == null || con.getId().equals(id))
			{
				con.addListener(lis);
				
				return id == null;
			}
			
			return true;
		}));
		
	}
	
	default void receivePackets()
	{
		this.forEveryConnection(((con) -> {con.receivePackets(); return true;}));
		
	}
	
	default UUID connect(ConnectionType type, int port)
	{
		return this.connect(type, new IP(port));
	}
	
	default UUID connect(ConnectionType type, IP ip)
	{
		return this.connect(ip.toChannel(type));
	}
	
}
