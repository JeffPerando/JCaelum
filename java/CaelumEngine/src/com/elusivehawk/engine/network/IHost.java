
package com.elusivehawk.engine.network;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IHost extends IPacketListener
{
	public Side getSide();
	
	public void sendPackets(Packet... pkts);
	
	public void addPacketFormat(PacketFormat format);
	
	public PacketFormat getPacketFormat(short id);
	
}
