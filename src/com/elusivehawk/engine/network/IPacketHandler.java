
package com.elusivehawk.engine.network;


/**
 * 
 * Interface for overall packet handling, except for handshaking.
 * 
 * @author Elusivehawk
 */
public interface IPacketHandler
{
	/**
	 * 
	 * Called during packet I/O.
	 * 
	 * @return The side this handler is on.
	 */
	Side getSide();
	
	/**
	 * 
	 * Called when a connection is about to close. Send any last minute packets via this.
	 * 
	 * @param connect The connection about to be closed.
	 */
	void onDisconnect(Connection connect);
	
	default void onPacketDropped(Packet pkt){}
	
}
