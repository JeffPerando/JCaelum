
package com.elusivehawk.engine.network;

import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.NetworkChannel;
import java.nio.channels.SocketChannel;
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
	public HSConnection(IHost owner, UUID id)
	{
		assert owner != null;
		
		master = owner;
		connect = new Connection(this, id);
		
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
	public ByteBuffer decryptData(ByteBuffer buf)
	{
		return this.connect.decryptData(buf);
	}
	
	@Override
	public void encryptData(ByteBuffer in, ByteBuffer out)
	{
		this.connect.encryptData(in, out);
		
	}
	
	@Override
	public boolean connect(ConnectionType type, NetworkChannel ch)
	{
		return this.connect.connect(type, ch);
	}
	
	@Override
	public UUID getId()
	{
		return this.connect.getId();
	}
	
	@Override
	public SocketChannel getTcp()
	{
		return this.connect.getTcp();
	}
	
	@Override
	public ImmutableList<DatagramChannel> getUdp()
	{
		return this.connect.getUdp();
	}
	
	@Override
	public ImmutableList<Packet> getOutgoingPackets()
	{
		return this.connect.getOutgoingPackets();
	}
	
	@Override
	public void flushPacket(Packet pkt)
	{
		this.connect.flushPacket(pkt);
		
	}
	
	@Override
	public void sendPackets(Packet... pkts)
	{
		this.connect.sendPackets(pkts);
		
	}
	
	@Override
	public boolean isClosed()
	{
		return this.connect.isClosed();
	}
	
	@Override
	public void close(boolean closeSkt)
	{
		this.connect.close(closeSkt);
		
	}
	
}
