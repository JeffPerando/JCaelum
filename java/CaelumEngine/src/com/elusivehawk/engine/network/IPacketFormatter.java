
package com.elusivehawk.engine.network;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IPacketFormatter
{
	public void addPacketFormat(PacketFormat format);
	
	public PacketFormat getPacketFormat(short id);
	
}
