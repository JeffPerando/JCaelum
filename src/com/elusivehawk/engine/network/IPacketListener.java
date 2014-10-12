
package com.elusivehawk.engine.network;

/**
 * 
 * Interface for packet listening.
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IPacketListener
{
	/**
	 * 
	 * Called after packet reading.
	 * 
	 * @param origin The connection that sent these packets.
	 * @param pkt A packet that was received.
	 */
	void onPacketReceived(Connection origin, Packet pkt);
	
}
