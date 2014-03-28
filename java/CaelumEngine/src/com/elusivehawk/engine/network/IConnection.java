
package com.elusivehawk.engine.network;

import java.io.Closeable;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.NetworkChannel;
import java.nio.channels.SocketChannel;
import java.util.UUID;
import com.elusivehawk.engine.util.io.IByteReader;
import com.google.common.collect.ImmutableList;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IConnection extends Closeable
{
	public UUID getId();
	
	public SocketChannel getTcp();
	
	public ImmutableList<DatagramChannel> getUdp();
	
	public ImmutableList<Packet> getOutgoingPackets();
	
	public void clearPkt(Packet pkt);
	
	public boolean connect(ConnectionType type, IP ip);
	
	public boolean connect(ConnectionType type, NetworkChannel ch);
	
	public void sendPackets(Packet... pkts);
	
	public boolean isClosed();
	
	public void close(boolean closeSkt);
	
	public ByteBuffer decryptData(ByteBuffer buf);
	
	public void encryptData(IByteReader in, ByteBuffer out);
	
}
