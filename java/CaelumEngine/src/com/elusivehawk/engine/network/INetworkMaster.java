
package com.elusivehawk.engine.network;

import java.util.List;

/**
 * 
 * The core interface for networking; Implement this to your game instance, or a dedicated networking manager.
 * 
 * @author Elusivehawk
 */
public interface INetworkMaster extends IPacketListener
{
	public int getEncryptionBitCount();
	
	public boolean handshake(IConnection connection, List<Packet> sentPkts);
	
}
