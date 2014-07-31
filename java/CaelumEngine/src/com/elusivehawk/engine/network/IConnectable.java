
package com.elusivehawk.engine.network;

import java.nio.channels.spi.AbstractSelectableChannel;
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
	default UUID connect(ConnectionType type, int port)
	{
		return this.connect(type, new IP(port));
	}
	
	default UUID connect(ConnectionType type, IP ip)
	{
		return this.connect(ip.toChannel(type));
	}
	
	public UUID connect(AbstractSelectableChannel ch);
	
	public void beginComm();
	
}
