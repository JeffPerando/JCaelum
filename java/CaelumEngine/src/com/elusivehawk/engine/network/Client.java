
package com.elusivehawk.engine.network;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
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
		
		/*KeyPairGenerator kpg = null;
		
		try
		{
			kpg = KeyPairGenerator.getInstance("RSA");
			
		}
		catch (NoSuchAlgorithmException e){}
		
		kpg.initialize(bits);
		
		KeyPair kp = kpg.genKeyPair();
		
		pub = kp.getPublic();
		priv = kp.getPrivate();*/
		
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
	
	/*
	@Override
	public ByteBuffer decryptData(ByteBuffer buf)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ByteBuffer encryptData(ByteBuffer buf)
	{
		// TODO Auto-generated method stub
		return null;
	}
	*/
	@Override
	public void onPacketsReceived(IConnection origin, ImmutableList<Packet> pkts)
	{
		this.master.onPacketsReceived(origin, pkts);
		
	}
	
	@Override
	public UUID connect(UUID id, IP ip, ConnectionType type)
	{
		if (this.connection != null)
		{
			if (type.isUdp() && this.connection.getId().equals(id))
			{
				this.connection.connect(type, ip);
				
				return this.connection.getId();
			}
			
		}
		
		if (type.isUdp())
		{
			return null;
		}
		
		return this.connect((SocketChannel)ip.toChannel(type));
	}
	
	@Override
	public UUID connect(SocketChannel s)
	{
		if (s == null || (this.connection != null || this.hasHS))
		{
			return null;
		}
		
		this.connection = new HSConnection(this, UUID.randomUUID());
		this.thr.connect(this.connection, ConnectionType.TCP, s);
		
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
	public void sendPackets(UUID client, Packet... pkts)
	{
		if (this.connection == null)
		{
			return;
		}
		
		if (!this.connection.getId().equals(client))
		{
			return;
		}
		
		this.connection.sendPackets(pkts);
		
	}
	
	@Override
	public void sendPacketsExcept(UUID client, Packet... pkts)
	{
		assert client != null;
		
		if (!client.equals(this.connection.getId()))
		{
			this.sendPackets(this.connection.getId(), pkts);
			
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
	public ImmutableList<UUID> getConnectionIds()
	{
		return this.connection == null ? null : ImmutableList.copyOf(new UUID[]{this.connection.getId()});
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
		
		boolean success = this.master.handshake(connection, pkts);
		
		connection.close(!success);
		
		if (success)
		{
			this.connection = new Connection(this, UUID.randomUUID());
			
			boolean finish = true;
			
			if (this.connection.connect(ConnectionType.TCP, connection.getTcp()))
			{
				finish = false;
				
			}
			
			ImmutableList<DatagramChannel> udp = connection.getUdp();
			
			if (udp != null && !udp.isEmpty())
			{
				for (DatagramChannel ch : udp)
				{
					this.connection.connect(ConnectionType.UDP, ch);
					
				}
				
			}
			
			this.hasHS = finish;
			
		}
		
		if (!this.hasHS)
		{
			this.connection = null;
			
		}
		
	}
	
	@Override
	public void close() throws IOException
	{
		this.connection.close(true);
		this.connection = null;
		
	}
	
}
