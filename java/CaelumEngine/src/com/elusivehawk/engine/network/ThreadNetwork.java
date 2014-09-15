
package com.elusivehawk.engine.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.security.PublicKey;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import com.elusivehawk.util.BufferHelper;
import com.elusivehawk.util.Internal;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.concurrent.ThreadStoppable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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
			bin = new byte[NetworkConst.HEADER_LENGTH + NetworkConst.DATA_LENGTH * 4],
			bout = new byte[NetworkConst.HEADER_LENGTH + NetworkConst.DATA_LENGTH * NetworkConst.PKT_LIMIT];
	
	protected final ByteBuffer
			bin_buf = BufferHelper.createWrapper(this.bin),
			bout_buf = BufferHelper.createWrapper(this.bout);
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadNetwork(IPacketHandler h, int playerCount)
	{
		assert h != null;
		assert playerCount > 0;
		
		handler = h;
		connections = Maps.newHashMapWithExpectedSize(playerCount);
		
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
		boolean read = false;
		int bytesRead = -1;
		
		byte[] b = null;
		List<Packet> pkts = null;
		Packet pkt;
		
		int select = this.selector.selectNow();
		
		if (select > 0)
		{
			Set<SelectionKey> keys = this.selector.selectedKeys();
			Iterator<SelectionKey> itr = keys.iterator();
			SelectionKey key;
			ByteChannel io;
			Connection con;
			
			while (itr.hasNext())
			{
				key = itr.next();
				
				if (key.isValid())
				{
					io = (ByteChannel)key.channel();
					
					con = (Connection)key.attachment();
					
					if (key.isReadable())
					{
						while ((bytesRead = io.read(this.bin_buf)) != -1)
						{
							read = false;
							
							try
							{
								b = con.decryptData(this.bin, bytesRead);//Decrypt the data
								read = b != null;
								
							}
							catch (NetworkException e)
							{
								this.handleException(e);
								
							}
							
							if (read)
							{
								if (pkts == null)//Dynamically load the packet list with a soft limit of 32 packets.
								{
									pkts = Lists.newArrayListWithCapacity(32);
									
								}
								
								pkt = new Packet(b);
								
								//Schedule the packet to be sent to the game.
								
								pkts.add(pkt);
								pkt.markForReading();
								
							}
							
							this.bin_buf.clear();//Clear the incoming bytes to prepare for the next packet.
							
						}
						
						if (pkts != null)
						{
							this.handler.onPacketsReceived(con, ImmutableList.copyOf(pkts));
							
						}
						
					}
					
					if (key.isWritable())
					{
						ImmutableList<Packet> outPkts = con.getOutgoingPackets();
						
						if (outPkts != null && !outPkts.isEmpty())
						{
							Iterator<Packet> pktItr = outPkts.iterator();
							
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
								
							}
							
							this.bout_buf.flip();
							io.write(this.bout_buf);
							
							for (int c = 0; c < this.bout.length; c++)
							{
								this.bout[c] = 0;
								
							}
							
							this.bout_buf.clear();
							
						}
						
					}
					
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
				Logger.log().err(e);
				
			}
			
		}
		
	}
	
}
