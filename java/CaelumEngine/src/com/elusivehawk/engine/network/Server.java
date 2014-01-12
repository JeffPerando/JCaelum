
package com.elusivehawk.engine.network;

import java.util.Collection;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Server implements IHost
{
	protected final IP ip;
	protected final IPacketListener receiver;
	protected final int ups;
	
	protected Connection con = null;
	
	public Server(String ip, IPacketListener r)
	{
		this(IP.create(ip), r);
		
	}
	
	public Server(String ip, IPacketListener r, int ups)
	{
		this(IP.create(ip), r, ups);
		
	}
	
	public Server(IP ip, IPacketListener r)
	{
		this(ip, r, 30);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Server(IP ipAddress, IPacketListener r, int updCount)
	{
		assert ipAddress != null;
		assert r != null;
		assert updCount > 0;
		
		ip = ipAddress;
		receiver = r;
		ups = updCount;
		
	}
	
	public void connect()
	{
		
	}
	
	@Override
	public void onPacketsReceived(Collection<Packet> pkts)
	{
		this.receiver.onPacketsReceived(pkts);
		
	}
	
	@Override
	public Side getSide()
	{
		return Side.SERVER;
	}
	
	@Override
	public void sendPackets(Packet... pkts)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addPacketFormat(PacketFormat format)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public PacketFormat getPacketFormat(short id)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}
