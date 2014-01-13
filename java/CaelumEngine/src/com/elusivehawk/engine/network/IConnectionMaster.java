
package com.elusivehawk.engine.network;

/**
 * 
 * The core interface for networking; Implement this to your game instance, or a dedicated networking manager.
 * 
 * @author Elusivehawk
 */
public interface IConnectionMaster extends IPacketListener, IPacketFormatter, IHandshaker
{
	
}
