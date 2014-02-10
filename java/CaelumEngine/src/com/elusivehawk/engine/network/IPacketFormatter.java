
package com.elusivehawk.engine.network;

/**
 * 
 * Interface for handling {@link PacketFormat}s.
 * 
 * @author Elusivehawk
 */
public interface IPacketFormatter
{
	/**
	 * 
	 * (Usually) called during packet I/O.
	 * 
	 * @param id The ID of the packet format to return.
	 * @return The packet format to use.
	 */
	public PacketFormat getPacketFormat(short id);
	
	public boolean validate(PacketFormat format);
	
}
