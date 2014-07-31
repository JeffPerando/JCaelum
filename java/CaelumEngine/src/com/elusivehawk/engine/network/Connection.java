
package com.elusivehawk.engine.network;

import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;
import java.util.UUID;
import javax.crypto.Cipher;
import com.elusivehawk.util.BufferHelper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * 
 * Handles interaction with the networking thread.
 * 
 * @author Elusivehawk
 */
public class Connection
{
	protected final IPacketHandler handler;
	protected final UUID connectId;
	
	protected final ConnectionType type;
	protected final AbstractSelectableChannel channel;
	
	protected final PublicKey pub;
	protected final PrivateKey priv;
	
	protected PublicKey pub_rec = null;
	protected boolean readPubKey = false;
	protected Object attach = null;
	
	private final List<Packet> incoming = Lists.newArrayList();
	
	public Connection(IPacketHandler h, AbstractSelectableChannel ch)
	{
		this(h, ch, 1024);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Connection(IPacketHandler h, AbstractSelectableChannel ch, int bits)
	{
		assert h != null;
		
		if (h == null && this instanceof IPacketHandler)
		{
			handler = (IPacketHandler)this;
			
		}
		else handler = h;
		
		connectId = UUID.randomUUID();
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
	public Connection(Connection con)
	{
		handler = con.handler;
		connectId = con.connectId;
		channel = con.channel;
		type = con.type;
		
		pub = con.pub;
		priv = con.priv;
		pub_rec = con.pub_rec;
		readPubKey = con.readPubKey;
		
	}
	
	@Override
	public Connection clone()
	{
		return new Connection(this);
	}
	
	public ConnectionType getType()
	{
		return this.type;
	}
	
	public UUID getId()
	{
		return this.connectId;
	}
	
	public AbstractSelectableChannel getChannel()
	{
		return this.channel;
	}
	
	public PublicKey getPubKey()
	{
		return this.pub;
	}
	
	public ImmutableList<Packet> getOutgoingPackets()
	{
		if (this.incoming.isEmpty())
		{
			return null;
		}
		
		return ImmutableList.copyOf(this.incoming);
	}
	
	public synchronized void flushPacket(Packet pkt)
	{
		this.incoming.remove(pkt);
		
	}
	
	public synchronized void sendPackets(Packet... pkts)
	{
		for (Packet pkt : pkts)
		{
			this.incoming.add(pkt);
			
		}
		
	}
	
	public ByteBuffer decryptData(ByteBuffer buf) throws NetworkException
	{
		if (this.pub_rec == null)
		{
			if (this.readPubKey)
			{
				throw new NetworkException("Cannot read bytes!");
			}
			
			//TODO Investigate usage.
			X509EncodedKeySpec keyspec = new X509EncodedKeySpec(buf.array());
			
			try
			{
				KeyFactory fac = KeyFactory.getInstance("RSA");
				this.pub_rec = fac.generatePublic(keyspec);
				
			}
			catch (Throwable e)
			{
				throw new NetworkException("Error whilst reading public key:", e);
				
			}
			
			this.readPubKey = true;
			
			return null;
		}
		
		byte[] ret = null;
		
		try
		{
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, this.pub_rec);
			ret = cipher.doFinal(buf.array());
			
		}
		catch (Throwable e)
		{
			throw new NetworkException("Cannot decrypt data:", e);
		}
		
		return BufferHelper.makeByteBuffer(ret);
	}
	
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
	
	public Object getAttachment()
	{
		return this.attach;
	}
	
	public void setAttachment(Object a)
	{
		this.attach = a;
		
	}
	
}
