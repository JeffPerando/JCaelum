
package com.elusivehawk.engine.network;

import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IConnectionMaster extends IPacketListener
{
	public short[] getHandshakeProtocol();
	
	public void onHandshakeEnd(boolean success, Connection client, List<Packet> sentPkts);
	
}
