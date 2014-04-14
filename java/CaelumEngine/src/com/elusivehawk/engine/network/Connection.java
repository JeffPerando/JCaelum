
package com.elusivehawk.engine.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.NetworkChannel;
import java.nio.channels.SocketChannel;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;
import java.util.UUID;
import javax.crypto.Cipher;
import com.elusivehawk.engine.util.storage.SemiFinalStorage;
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
	protected final PublicKey pub;
	protected final PrivateKey priv;
	
	private final SemiFinalStorage<Boolean> closed = new SemiFinalStorage<Boolean>(false);
	
	private SocketChannel tcp = null;
	private List<DatagramChannel> udp = Lists.newArrayList();
	
	private List<Packet> incoming = Lists.newArrayList();
	
	public Connection(IPacketHandler h, UUID id)
	{
		this(h, id, 1024);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Connection(IPacketHandler h, UUID id, int bits)
	{
		assert h != null;
		assert id != null;
		
		handler = h;
		connectId = id;
		
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
	
	@Override
	public boolean connect(ConnectionType type, IP ip)
	{
		return this.connect(type, ip.toChannel(type));
	}
	
	@Override
	public boolean connect(ConnectionType type, NetworkChannel s)
	{
		if (s == null)
		{
			return false;
		}
		
		if (type.isTcp())
		{
			if (this.tcp != null)
			{
				return false;
			}
			
			this.tcp = (SocketChannel)s;
			
		}
		else
		{
			this.udp.add((DatagramChannel)s);
			
		}
		
		return true;
	}
	
	@Override
	public void close()
	{
		this.close(true);
		
	}
	
	@Override
	public UUID getId()
	{
		return this.connectId;
	}
	
	@Override
	public SocketChannel getTcp()
	{
		return this.tcp;
	}
	
	@Override
	public ImmutableList<DatagramChannel> getUdp()
	{
		return ImmutableList.copyOf(this.udp);
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
	public void clearPkt(Packet pkt)
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
	public boolean isClosed()
	{
		return this.closed.get();
	}
	
	@Override
	public void close(boolean closeSkt)
	{
		if (this.isClosed())
		{
			return;
		}
		
		this.handler.onDisconnect(this);
		
		this.closed.set(true);
		
		if (closeSkt)
		{
			try
			{
				this.tcp.close();
				
				for (DatagramChannel ch : this.udp)
				{
					ch.close();
					
				}
				
			}
			catch (IOException e)
			{
				e.printStackTrace();
				
			}
			
		}
		
	}
	
	@Override
	public byte[] decryptData(ByteBuffer buf)
	{
		return null;//FIXME
	}
	
	@Override
	public void encryptData(byte[] b, ByteBuffer buf)
	{
		byte[] info = null;
		
		try
		{
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, this.pub);
			info = cipher.doFinal(b);
			
		}
		catch (Exception e){}
		
		if (info != null)
		{
			buf.put(info);
			
		}
		
	}
	
}
