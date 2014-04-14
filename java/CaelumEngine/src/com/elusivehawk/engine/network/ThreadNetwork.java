
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
import com.elusivehawk.engine.util.ThreadStoppable;
import com.elusivehawk.engine.util.storage.Tuple;
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
	public static final int HEADER_LENGTH = 3,
			DATA_LENGTH = 8192,
			TOTAL_PKT_LENGTH = HEADER_LENGTH + DATA_LENGTH,
			PKT_LIMIT = 32;
	
	protected final IPacketHandler handler;
	protected final Map<UUID, IConnection> connections;
	
	//NIO channel things
	
	protected final Selector selector;
	
	//Incoming
	
	protected final ByteBuffer head = ByteBuffer.allocate(HEADER_LENGTH),
			bin = ByteBuffer.allocate(DATA_LENGTH);
	
	//Outgoing
	
	protected final ByteBuffer bout = ByteBuffer.allocate(TOTAL_PKT_LENGTH * PKT_LIMIT);
	
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
		short length;
		byte[] b;
		List<Packet> pkts = null;
		Packet pkt;
		Side side;
		
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
						while (io.read(this.head) != -1)
						{
							s = this.head.get();
							length = (short)MathHelper.clamp(this.head.getShort(), 1, DATA_LENGTH);//Get the remaining packet length
							
							this.head.clear();//Clear the buffer for reuse
							
							this.bin.limit(length);//Make sure we can't go over
							
							io.read(this.bin);//Read the data
							
							b = info.two.decryptData(this.bin);//Decrypt the data
							
							if (b == null)//Unlikely, but...
							{
								continue;
							}
							
							side = Side.values()[s];
							
							if (!this.handler.getSide().canReceive(side))//Maintains the good ol' side check
							{
								continue;
							}
							
							pkt = new Packet(Side.values()[s], b);
							
							if (this.handler.isPacketSafe(pkt))//Is the packet is safe...
							{
								if (pkts == null)//Dynamically load the packet list with a soft limit of 32 packets.
								{
									pkts = Lists.newArrayListWithCapacity(32);
									
								}
								
								//Schedule the packet to be sent to the game.
								
								pkts.add(pkt);
								
							}
							
							this.bin.clear();//Clear the incoming bytes to prepare for the next packet.
							
						}
						
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
							
							if (!this.handler.getSide().canSend(pkt.getSide()))
							{
								continue;
							}
							
							if (!this.handler.isPacketSafe(pkt))
							{
								continue;
							}
							
							info.two.encryptData(pkt.getBytes(), this.bout);
							
							info.two.clearPkt(pkt);
							
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
