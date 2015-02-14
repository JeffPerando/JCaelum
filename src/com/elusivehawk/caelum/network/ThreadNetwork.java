
package com.elusivehawk.caelum.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import com.elusivehawk.util.Internal;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.concurrent.ThreadStoppable;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Internal
public class ThreadNetwork extends ThreadStoppable
{
	protected final IPacketHandler handler;
	protected final Map<UUID, Connection> connections;
	
	//NIO channel things
	
	protected final Selector selector;
	
	protected final byte[]
			bin = new byte[NetworkConst.DATA_LENGTH * 4],
			bout = new byte[NetworkConst.DATA_LENGTH];
	
	protected final ByteBuffer
			bin_buf = BufferHelper.createWrapper(this.bin),
			bout_buf = BufferHelper.createWrapper(this.bout);
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadNetwork(IPacketHandler h, int playerCount)
	{
		assert h != null;
		assert playerCount > 0;
		
		handler = h;
		connections = new HashMap<UUID, Connection>(playerCount);
		
		Selector s = null;
		
		try
		{
			
			s = Selector.open();
			
		}
		catch (Exception e){}
		
		selector = s;
		
	}
	
	@Override
	public void rawUpdate() throws Throwable
	{
		int select = this.selector.selectNow();
		
		if (select == 0)
		{
			Thread.sleep(1);
			return;
		}
		
		Set<SelectionKey> keys = this.selector.selectedKeys();
		Iterator<SelectionKey> keyItr = keys.iterator();
		Iterator<Packet> pktItr;
		Packet pkt;
		
		SelectionKey key;
		ByteChannel io;
		Connection con;
		
		int bytesRead = -1;
		byte[] pktBytes;
		
		while (keyItr.hasNext())
		{
			key = keyItr.next();
			
			if (!key.isValid())
			{
				continue;
			}
			
			io = (ByteChannel)key.channel();
			
			con = (Connection)key.attachment();
			
			if (key.isReadable())
			{
				while ((bytesRead = io.read(this.bin_buf)) != -1)
				{
					pktBytes = new byte[bytesRead];
					
					System.arraycopy(this.bin, 0, pktBytes, 0, bytesRead);
					
					try
					{
						con.decryptData(pktBytes);
						
					}
					catch (NetworkException e)
					{
						this.handleException(e);
						
					}
					
					this.bin_buf.clear();//Clear the incoming bytes to prepare for the next packet.
					
				}
				
			}
			
			if (key.isWritable())
			{
				List<Packet> outPkts = con.getOutgoingPackets();
				
				if (outPkts.isEmpty())
				{
					continue;
				}
				
				pktItr = outPkts.iterator();
				
				while (pktItr.hasNext())
				{
					pkt = pktItr.next();
					
					if (pkt.canWrite())
					{
						pktItr.remove();
						
						this.handler.onPacketDropped(pkt);
						
						continue;
					}
					
					this.bout_buf.put(con.encryptData(pkt.getBytes()));
					
					con.flushPacket(pkt);
					
					this.bout_buf.flip();
					io.write(this.bout_buf);
					this.bout_buf.clear();
					
				}
				
			}
			
		}
		
	}
	
	@Override
	public void onThreadStopped(boolean failure)
	{
		try
		{
			this.rawUpdate();
			
			this.selector.close();
			
		}
		catch (Throwable e){}
		
	}
	
	public synchronized void sendPackets(UUID id, Packet... pkts)
	{
		if (pkts == null || pkts.length == 0)
		{
			return;
		}
		
		Connection connect = this.connections.get(id);
		
		if (connect != null)
		{
			connect.sendPackets(pkts);
			
		}
		
	}
	
	public synchronized void connect(Connection con) throws NetworkException
	{
		if (this.connections.get(con.getId()) == null)
		{
			this.connections.put(con.getId(), con);
			
		}
		
		SelectableChannel ch = con.getChannel();
		
		try
		{
			ch.configureBlocking(false);
			ch.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, con);
			
		}
		catch (Exception e){}
		
		ByteChannel io = (ByteChannel)ch;
		
		PublicKey key = con.getPubKey();
		
		ByteBuffer buf = BufferHelper.makeByteBuffer(key.getEncoded());
		
		try
		{
			io.write(buf);
			
		}
		catch (IOException e)
		{
			throw new NetworkException("Cannot send public key! This is a bug!", e);
			
		}
		
		//TODO Transmit key.
		
	}
	
	public synchronized void disconnect(Connection con)
	{
		if (this.connections.remove(con.getId()) != null)
		{
			try
			{
				con.getChannel().close();
				
			}
			catch (IOException e)
			{
				Logger.err(e);
				
			}
			
		}
		
	}
	
}
