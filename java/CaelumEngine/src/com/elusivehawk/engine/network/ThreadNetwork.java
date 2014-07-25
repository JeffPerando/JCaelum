
package com.elusivehawk.engine.network;

import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.NetworkChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import com.elusivehawk.engine.math.MathHelper;
import com.elusivehawk.util.BufferHelper;
import com.elusivehawk.util.concurrent.ThreadStoppable;
import com.elusivehawk.util.storage.Tuple;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ThreadNetwork extends ThreadStoppable
{
	protected final IPacketHandler handler;
	protected final Map<UUID, IConnection> connections;
	
	//NIO channel things
	
	protected final Selector selector;
	
	protected final ByteBuffer bin = BufferHelper.createByteBuffer(NetworkConst.HEADER_LENGTH + NetworkConst.DATA_LENGTH * 4),
			bout = BufferHelper.createByteBuffer(NetworkConst.HEADER_LENGTH + NetworkConst.DATA_LENGTH * NetworkConst.PKT_LIMIT),
			tmp = BufferHelper.createByteBuffer(NetworkConst.HEADER_LENGTH + NetworkConst.DATA_LENGTH);
	
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
		for (IConnection con : this.connections.values())
		{
			if (con.isClosed())
			{
				this.connections.remove(con.getId());
				
			}
			
		}
		
		byte s;
		int length;
		ByteBuffer b;
		List<Packet> pkts = null;
		Packet pkt;
		
		int select = this.selector.selectNow();
		
		if (select > 0)
		{
			Set<SelectionKey> keys = this.selector.selectedKeys();
			Iterator<SelectionKey> itr = keys.iterator();
			SelectionKey key;
			ByteChannel io;
			
			while (itr.hasNext())
			{
				key = itr.next();
				
				if (key.isValid())
				{
					io = (ByteChannel)key.channel();
					
					@SuppressWarnings("unchecked")
					Tuple<ConnectionType, IConnection> info = (Tuple<ConnectionType, IConnection>)key.attachment();
					
					if (key.isReadable())
					{
						if (io.read(this.bin) == -1)
						{
							continue;
						}
						
						b = info.two.decryptData(this.bin);//Decrypt the data
						
						if (b == null)//Unlikely, but...
						{
							continue;
						}
						
						while (b.remaining() > 0)
						{
							s = b.get();
							length = MathHelper.clamp(this.bin.getInt(), 1, NetworkConst.DATA_LENGTH);//Get the remaining packet length
							
							if (!this.handler.getSide().canReceive(Side.values()[s]))//If the packet isn't meant for this side to receive it...
							{
								continue;
							}
							
							if (pkts == null)//Dynamically load the packet list with a soft limit of 32 packets.
							{
								pkts = Lists.newArrayListWithCapacity(32);
								
							}
							
							//Schedule the packet to be sent to the game.
							
							pkt = new Packet(b);
							
							pkts.add(pkt);
							pkt.markForReading();
							
							if (b.capacity() != length)//TODO Investigate this.
							{
								pkt.markPotentiallyCorrupt();
								
							}
							
						}
						
						this.bin.clear();//Clear the incoming bytes to prepare for the next packet.
						
						if (pkts != null)
						{
							this.handler.onPacketsReceived(info.two, ImmutableList.copyOf(pkts));
							
						}
						
					}
					
					if (key.isWritable())
					{
						ImmutableList<Packet> outPkts = info.two.getOutgoingPackets();
						
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
							
							b = pkt.getBytes();
							
							this.tmp.put((byte)this.handler.getSide().ordinal());
							this.tmp.putInt(b.capacity() - b.remaining());
							this.tmp.put(b);
							
							info.two.encryptData(this.tmp, this.bout);
							
							info.two.flushPacket(pkt);
							
							this.tmp.clear();
							
						}
						
						this.bout.flip();
						io.write(this.bout);
						
						for (int c = 0; c < this.bout.limit(); c++)
						{
							this.bout.put(c, (byte)0);
							
						}
						
						this.bout.clear();
						
					}
					
				}
				
				itr.remove();
				
				io = null;
				
			}
			
		}
		
	}
	
	@Override
	public void onThreadStopped()
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
		
		IConnection connect = this.connections.get(id);
		
		if (connect != null)
		{
			connect.sendPackets(pkts);
			
		}
		
	}
	
	public void connect(IConnection con, ConnectionType type, SelectableChannel ch)
	{
		if (!con.connect(type, (NetworkChannel)ch))
		{
			return;
		}
		
		this.connections.put(con.getId(), con);
		
		try
		{
			ch.configureBlocking(false);
			ch.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, Tuple.create(type, con));
			
		}
		catch (Exception e){}
		
	}
	
}
