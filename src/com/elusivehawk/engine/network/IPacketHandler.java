
package com.elusivehawk.engine.network;


/**
 * 
 * Interface for overall packet handling, except for handshaking.
 * 
 * @author Elusivehawk
 */
public interface IPacketHandler extends IPacketListener
{
	/**
	 * 
	 * Called during packet I/O.
	 * 
	 * @return The side this handler is on.
	 */
	public Side getSide();
	
	/**
	 * 
	 * Called when a connection is about to close. Send any last minute packets via this.
	 * 
	 * @param connect The connection about to be closed.
	 */
	public void onDisconnect(Connection connect);
	
	@SuppressWarnings("unused")
	default void onPacketDropped(Packet pkt){}
	
}
