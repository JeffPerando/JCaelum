
package com.elusivehawk.engine.network;

import java.nio.ByteBuffer;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.UUID;
import com.google.common.collect.ImmutableList;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IConnection
{
	public ConnectionType getType();
	
	public UUID getId();
	
	public AbstractSelectableChannel getChannel();
	
	public IPacketHandler getListener();
	
	public PublicKey getPubKey();
	
	public PrivateKey getPrivKey();
	
	public ImmutableList<Packet> getOutgoingPackets();
	
	public void sendPackets(Packet... pkts);
	
	public void flushPacket(Packet pkt);
	
	public ByteBuffer decryptData(ByteBuffer buf);
	
	public void encryptData(ByteBuffer in, ByteBuffer out);
	
}
