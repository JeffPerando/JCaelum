
package com.elusivehawk.engine.network;

import java.nio.channels.SocketChannel;
import java.util.UUID;

/**
 * 
 * Interface for standardising connecting.
 * <p>
 * For servers: Call {@link #beginComm()} to initiate server listening; connect() is only used internally.<br>
 * For everything else: Call connect() to initiate communication threads, then call {@link #beginComm()} to begin communication.
 * 
 * @author Elusivehawk
 */
public interface IConnectable
{
	public UUID connect(UUID origin, IP ip, ConnectionType type);
	
	public UUID connect(SocketChannel sch);
	
	public void beginComm();
	
}
