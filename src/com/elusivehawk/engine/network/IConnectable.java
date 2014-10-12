
package com.elusivehawk.engine.network;

import java.nio.channels.spi.AbstractSelectableChannel;
import java.util.UUID;

/**
 * 
 * Interface for standardising connecting.
 * <p>
 * For clients: Call connect() to initiate communication threads, then call {@link #beginComm()} to begin communication.
 * For servers: Call {@link #beginComm()} to initiate server listening; connect() is only used internally.<br>
 * 
 * @author Elusivehawk
 */
public interface IConnectable
{
	default UUID connect(ConnectionType type, int port)
	{
		return this.connect(type, new IP(port));
	}
	
	default UUID connect(ConnectionType type, IP ip)
	{
		return this.connect(ip.toChannel(type));
	}
	
	UUID connect(AbstractSelectableChannel ch);
	
	void beginComm();
	
}
