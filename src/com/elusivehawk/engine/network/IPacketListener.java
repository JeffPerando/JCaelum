
package com.elusivehawk.engine.network;

import com.google.common.collect.ImmutableList;

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
	 * @param pkts An immutable list of the packets sent.
	 */
	public void onPacketsReceived(Connection origin, ImmutableList<Packet> pkts);
	
}
