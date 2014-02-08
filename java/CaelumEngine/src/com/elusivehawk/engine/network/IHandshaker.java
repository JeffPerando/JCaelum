
package com.elusivehawk.engine.network;

import java.util.List;

/**
 * 
 * Interface for handling handshaking.
 * 
 * @author Elusivehawk
 */
public interface IHandshaker
{
	/**
	 * 
	 * Called during handshaking.
	 * 
	 * @return The packet IDs to look for.
	 */
	public short[] getHandshakeProtocol();
	
	/**
	 * 
	 * Called after handshaking.
	 * 
	 * @param success If the packet IDs asked for were found; If true, then the connection will be shut down.
	 * @param connection The connection that was used; Send any "rejection" packets you want to send using it.
	 * @param sentPkts The packets sent by the connection; Inspect them if you wish.
	 */
	public void onHandshakeEnd(boolean success, HandshakeConnection connection, List<Packet> sentPkts);
	
}
