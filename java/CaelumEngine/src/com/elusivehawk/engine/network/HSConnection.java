
package com.elusivehawk.engine.network;

import java.nio.ByteBuffer;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.UUID;
import com.google.common.collect.ImmutableList;

/**
 * 
 * Internal class for handshaking.
 * <p>
 * If you're going to attempt coding voodoo/shenanigans, feel free to use it; Otherwise, please don't bother.
 * 
 * @author Elusivehawk
 */
public class HSConnection implements IPacketHandler, IConnection
{
	private final IHost master;
	private final IConnection connect;
	
	@SuppressWarnings("unqualified-field-access")
	public HSConnection(IHost owner, UUID id, AbstractSelectableChannel ch, int bits)
	{
		assert owner != null;
		
		master = owner;
		connect = new Connection(this, id, ch, bits);
		
	}
	
	@Override
	public void onPacketsReceived(IConnection origin, ImmutableList<Packet> pkts)
	{
		this.master.onHandshake(this, pkts);
		
	}
	
	@Override
	public Side getSide()
	{
		return this.master.getSide();
	}
	
	@Override
	public void onDisconnect(IConnection connect)
	{
		this.master.onDisconnect(connect);
		
	}
	
	@Override
	public void onPacketDropped(Packet pkt)
	{
		this.master.onPacketDropped(pkt);
		
	}
	
	@Override
	public ConnectionType getType()
	{
		return this.connect.getType();
	}
	
	@Override
	public UUID getId()
	{
		return this.connect.getId();
	}
	
	@Override
	public AbstractSelectableChannel getChannel()
	{
		return this.connect.getChannel();
	}
	
	@Override
	public IPacketHandler getListener()
	{
		return this.connect.getListener();
	}
	
	@Override
	public PublicKey getPubKey()
	{
		return this.connect.getPubKey();
	}
	
	@Override
	public PrivateKey getPrivKey()
	{
		return this.connect.getPrivKey();
	}
	
	@Override
	public ImmutableList<Packet> getOutgoingPackets()
	{
		return this.connect.getOutgoingPackets();
	}
	
	@Override
	public void sendPackets(Packet... pkts)
	{
		this.connect.sendPackets(pkts);
		
	}
	
	@Override
	public void flushPacket(Packet pkt)
	{
		this.connect.flushPacket(pkt);
		
	}
	
	@Override
	public ByteBuffer decryptData(ByteBuffer buf)
	{
		return this.connect.decryptData(buf);
	}
	
	@Override
	public void encryptData(ByteBuffer in, ByteBuffer out)
	{
		this.connect.encryptData(in, out);
		
	}
	
}
