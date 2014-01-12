
package com.elusivehawk.engine.network;

import java.util.Collection;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IPacketListener
{
	public void onPacketsReceived(Collection<Packet> pkts);
	
}
