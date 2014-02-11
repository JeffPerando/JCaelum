
package com.elusivehawk.engine.network;

import java.io.Closeable;
import java.nio.channels.SocketChannel;
import java.util.UUID;
import com.elusivehawk.engine.util.IPausable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IConnection extends IConnectable, IPausable, Closeable
{
	public UUID getId();
	
	public SocketChannel getChannel();
	
	public void sendPackets(Packet... pkts);
	
	public boolean isClosed();
	
	public void close(boolean closeSkt);
	
}
