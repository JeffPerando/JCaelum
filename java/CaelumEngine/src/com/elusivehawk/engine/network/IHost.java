
package com.elusivehawk.engine.network;

import java.io.Closeable;
import java.util.UUID;

/**
 * 
 * Interface for "host" object (Client, server, etc.)
 * <p>
 * Note: It's recommended that you not implement this yourself.
 * 
 * @author Elusivehawk
 */
public interface IHost extends IConnectable, IPacketHandler, IHandshaker, Closeable
{
	/**
	 * 
	 * Called when something wants to send packets.
	 * 
	 * @param client The connection ID to use, null if it's client -> server communication.
	 * @param pkts The packets to send.
	 */
	public void sendPackets(UUID client, Packet... pkts);
	
	public void sendPacketsExcept(UUID client, Packet... pkts);
	
	public int getPlayerCount();
	
	public UUID[] getConnectionIds();
	
	public void pauseConnections(boolean pause);
	
}
