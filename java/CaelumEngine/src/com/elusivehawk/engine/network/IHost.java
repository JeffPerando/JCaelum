
package com.elusivehawk.engine.network;

import java.io.Closeable;

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
	 * @param client The connection ID to use, for 0 if it's client -> server communication.
	 * @param pkts The packets to send.
	 */
	public void sendPackets(int client, Packet... pkts);
	
	public void pauseConnections(boolean pause);
	
}
