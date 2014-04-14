
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
	public boolean isPacketSafe(Packet pkt)
	{
		return this.master.isPacketSafe(pkt);
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
	public byte[] decryptData(ByteBuffer buf)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void encryptData(byte[] r, ByteBuffer buf)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean connect(ConnectionType type, IP ip)
	{
		return this.connect.connect(type, ip);
	}
	
	@Override
	public boolean connect(ConnectionType type, NetworkChannel ch)
	{
		return this.connect.connect(type, ch);
	}
	
	@Override
	public void close()
	{
		try
		{
			this.connect.close();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
		}
		
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
	public void clearPkt(Packet pkt)
	{
		this.connect.clearPkt(pkt);
		
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
