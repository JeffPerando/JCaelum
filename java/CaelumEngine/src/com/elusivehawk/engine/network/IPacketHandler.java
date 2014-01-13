
package com.elusivehawk.engine.network;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IPacketHandler extends IPacketListener, IPacketFormatter
{
	public Side getSide();
	
	public void onDisconnect(Connection connect);
	
}
