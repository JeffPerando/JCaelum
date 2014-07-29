
package com.elusivehawk.engine.network;

import java.io.IOException;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.security.Key;
import java.util.List;
import java.util.UUID;
import com.elusivehawk.engine.math.MathHelper;
import com.google.common.collect.ImmutableList;

/**
 * 
 * Primary class for client-sided server interfacing.
 * <p>
 * Note: Because of the fact that it's a client, it only supports one connection at a time.
 * 
 * @author Elusivehawk
 */
public class Client implements IHost
{
	protected final INetworkMaster master;
	protected final ThreadNetwork thr;
	//protected final PublicKey pub;
	//protected final PrivateKey priv;
	
	protected IConnection connection = null;
	protected boolean hasHS = false;
	protected Key outgoing = null;
	
	@SuppressWarnings("unqualified-field-access")
	public Client(INetworkMaster mstr, int bits)
	{
		assert mstr != null;
		assert MathHelper.bounds(bits, 0, 4096);
		
		master = mstr;
		thr = new ThreadNetwork(this, 1);
		
	}
	
	@Override
	public Side getSide()
	{
		return Side.CLIENT;
	}
	
	@Override
	public void onDisconnect(IConnection connect)
	{
		if (this.connection != connect)
		{
			throw new RuntimeException("Please quit pulling off coding voodoo.");
			
		}
		
		this.connection = null;
		this.hasHS = false;
		
	}
	
	@Override
	public void onPacketDropped(Packet pkt)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onPacketsReceived(IConnection origin, ImmutableList<Packet> pkts)
	{
		this.master.onPacketsReceived(origin, pkts);
		
	}
	
	@Override
	public UUID connect(ConnectionType type, IP ip)
	{
		if (this.connection != null)
		{
			return null;
		}
		
		return this.connect(ip.toChannel(type));
	}
	
	@Override
	public UUID connect(AbstractSelectableChannel ch)
	{
		if (ch == null)
		{
			return null;
		}
		
		this.connection = new HSConnection(this, UUID.randomUUID(), ch, this.master.getEncryptionBitCount());
		this.thr.connect(this.connection);
		
		return this.connection.getId();
	}
	
	@Override
	public void beginComm()
	{
		if (this.connection != null)
		{
			this.thr.start();
			
		}
		
	}
	
	@Override
	public void forEveryConnection(IConnectionUser user)
	{
		if (this.connection != null)
		{
			user.processConnection(this.connection);
			
		}
		
	}
	
	@Override
	public int getMaxPlayerCount()
	{
		return 1;
	}
	
	@Override
	public int getPlayerCount()
	{
		return this.connection == null ? 0 : 1;
	}
	
	@Override
	public void setPaused(boolean pause)
	{
		if (this.connection != null)
		{
			this.thr.setPaused(pause);
			
		}
		
	}
	
	@Override
	public boolean isPaused()
	{
		return this.connection == null ? false : this.thr.isPaused();
	}
	
	@Override
	public void onHandshake(IConnection connection, List<Packet> pkts)
	{
		if (this.hasHS)
		{
			return;
		}
		
		if (this.master.handshake(connection, pkts))
		{
			this.connection = new Connection(connection);
			
		}
		
	}
	
	@Override
	public void close() throws IOException
	{
		this.thr.stopThread();
		
	}
	
}
