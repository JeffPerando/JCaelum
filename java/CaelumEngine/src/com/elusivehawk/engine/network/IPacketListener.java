
package com.elusivehawk.engine.network;

import com.google.common.collect.ImmutableList;

/**
 * 
 * Interface for packet listening.
 * <p>
 * Note: This is never used by itself.
 * 
 * @author Elusivehawk
 */
public interface IPacketListener
{
	/**
	 * 
	 * Called after packet reading.
	 * 
	 * @param origin The connection that sent these packets.
	 * @param pkts An immutable list of the packets sent.
	 */
	public void onPacketsReceived(IConnection origin, ImmutableList<Packet> pkts);
	
	public boolean isPacketSafe(Packet pkt);
	
}
