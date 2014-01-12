
package com.elusivehawk.engine.network;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IPacketHandler extends IPacketListener
{
	public void addPacketFormat(PacketFormat format);
	
	public PacketFormat getPacketFormat(short id);
	
}
