
package com.elusivehawk.engine.network;

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
import com.elusivehawk.util.Logger;
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
	private final IPacketHandler handler;
	private final UUID connectId;
	
	private final ConnectionType type;
	private final AbstractSelectableChannel channel;
	
	private final PublicKey pub;
	private final PrivateKey priv;
	
	private final Cipher cipher;
	
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
		
		Cipher ciph = null;
		
		try
		{
			ciph = Cipher.getInstance("RSA");
			
		}
		catch (Exception e)
		{
			Logger.log().err(e);
			
		}
		
		assert ciph != null;
		
		cipher = ciph;
		
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
		
		cipher = con.cipher;
		
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
	
	public byte[] decryptData(byte[] bytes) throws NetworkException
	{
		if (this.pub_rec == null)
		{
			if (this.readPubKey)
			{
				throw new NetworkException("Cannot read bytes!");
			}
			
			//TODO Investigate usage.
			X509EncodedKeySpec keyspec = new X509EncodedKeySpec(bytes);
			
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
			byte[] tmp;
			
			this.cipher.init(Cipher.DECRYPT_MODE, this.pub_rec);
			tmp = this.cipher.doFinal(bytes);
			
			this.cipher.init(Cipher.DECRYPT_MODE, this.priv);
			ret = this.cipher.doFinal(tmp);
			
		}
		catch (Throwable e)
		{
			throw new NetworkException("Cannot decrypt data:", e);
		}
		
		return ret;
	}
	
	public byte[] encryptData(byte[] in) throws NetworkException
	{
		if (this.pub_rec == null)
		{
			throw new NetworkException("Have not yet received public key, cannot encrypt!");
		}
		
		byte[] ret = null;
		
		try
		{
			byte[] tmp;
			
			Cipher cipher = Cipher.getInstance("RSA");
			
			cipher.init(Cipher.ENCRYPT_MODE, this.pub_rec);
			tmp = cipher.doFinal(in);
			
			cipher.init(Cipher.ENCRYPT_MODE, this.priv);
			ret = cipher.doFinal(tmp);
			
		}
		catch (Exception e)
		{
			throw new NetworkException("Cannot encrypt data:", e);
		}
		
		return ret;
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
