
package com.elusivehawk.engine.network;

import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;
import java.util.UUID;
import javax.crypto.Cipher;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * 
 * Handles interaction with the networking thread.
 * 
 * @author Elusivehawk
 */
public class Connection implements IConnection
{
	protected final IPacketHandler handler;
	protected final UUID connectId;
	protected final ConnectionType type;
	protected final AbstractSelectableChannel channel;
	protected final PublicKey pub;
	protected final PrivateKey priv;
	
	protected PublicKey pub_rec = null;
	
	private List<Packet> incoming = Lists.newArrayList();
	
	public Connection(IPacketHandler h, UUID id, AbstractSelectableChannel ch)
	{
		this(h, id, ch, 1024);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Connection(IPacketHandler h, UUID id, AbstractSelectableChannel ch, int bits)
	{
		assert h != null;
		assert id != null;
		
		handler = h;
		connectId = id;
		channel = ch;
		
		if (ch instanceof SocketChannel)
		{
			type = ConnectionType.TCP;
			
		}
		else if (ch instanceof DatagramChannel)
		{
			type = ConnectionType.UDP;
			
		}
		else type = ConnectionType.UNKNOWN;
		
		KeyPairGenerator kpg = null;
		
		try
		{
			kpg = KeyPairGenerator.getInstance("RSA");
			
		}
		catch (Exception e){}
		
		kpg.initialize(bits);
		KeyPair kp = kpg.genKeyPair();
		
		pub = kp.getPublic();
		priv = kp.getPrivate();
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Connection(IConnection con)
	{
		handler = con.getListener();
		connectId = con.getId();
		channel = con.getChannel();
		type = con.getType();
		pub = con.getPubKey();
		priv = con.getPrivKey();
		
	}
	
	@Override
	public ConnectionType getType()
	{
		return this.type;
	}
	
	@Override
	public UUID getId()
	{
		return this.connectId;
	}
	
	@Override
	public AbstractSelectableChannel getChannel()
	{
		return this.channel;
	}
	
	@Override
	public IPacketHandler getListener()
	{
		return this.handler;
	}
	
	@Override
	public PublicKey getPubKey()
	{
		return this.pub;
	}
	
	@Override
	public PrivateKey getPrivKey()
	{
		return this.priv;
	}
	
	@Override
	public ImmutableList<Packet> getOutgoingPackets()
	{
		if (this.incoming.isEmpty())
		{
			return null;
		}
		
		return ImmutableList.copyOf(this.incoming);
	}
	
	@Override
	public void flushPacket(Packet pkt)
	{
		this.incoming.remove(pkt);
		
	}
	
	@Override
	public synchronized void sendPackets(Packet... pkts)
	{
		for (Packet pkt : pkts)
		{
			this.incoming.add(pkt);
			
		}
		
	}
	
	@Override
	public ByteBuffer decryptData(ByteBuffer buf)
	{
		return null;//FIXME
	}
	
	@Override
	public void encryptData(ByteBuffer in, ByteBuffer out)
	{
		try
		{
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, this.priv);
			cipher.doFinal(in, out);
			
		}
		catch (Exception e){}
		
	}
	
}
