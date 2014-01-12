
package com.elusivehawk.engine.network;

import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IPacketListener
{
	public Side getSide();
	
	public void onPacketsReceived(Connection origin, List<Packet> pkts);
	
}
