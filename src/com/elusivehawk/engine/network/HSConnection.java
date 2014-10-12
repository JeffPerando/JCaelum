
package com.elusivehawk.engine.network;

import java.nio.channels.spi.AbstractSelectableChannel;
import com.elusivehawk.util.Internal;

/**
 * 
 * Internal class for handshaking.
 * <p>
 * If you're going to attempt coding voodoo/shenanigans, feel free to use it; Otherwise, please don't bother.
 * 
 * @author Elusivehawk
 */
@Internal
public class HSConnection extends Connection implements IPacketHandler
{
	private final IHost master;
	
	@SuppressWarnings("unqualified-field-access")
	public HSConnection(IHost owner, AbstractSelectableChannel ch, int bits)
	{
		super(null, ch, bits);
		
		assert owner != null;
		
		master = owner;
		
	}
	
	@Override
	public Side getSide()
	{
		return this.master.getSide();
	}
	
	@Override
	public void onDisconnect(Connection connect)
	{
		this.master.onDisconnect(connect);
		
	}
	
	@Override
	public void onPacketReceived(Connection origin, Packet pkt)
	{
		this.master.onHandshake(this, pkt);
		
	}
	
	@Override
	public void onPacketDropped(Packet pkt)
	{
		this.master.onPacketDropped(pkt);
		
	}
	
}
