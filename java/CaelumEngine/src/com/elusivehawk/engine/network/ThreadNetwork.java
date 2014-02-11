
package com.elusivehawk.engine.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.elusivehawk.engine.util.SyncBuffer;
import com.elusivehawk.engine.util.ThreadTimed;
import com.google.common.collect.ImmutableList;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ThreadNetwork extends ThreadTimed
{
	protected final int updateCount;
	protected final IPacketHandler handler;
	protected final IConnection connect;
	
	//NIO channel things
	
	protected final Selector selector;
	protected final SocketChannel tcp;
	protected final List<DatagramChannel> udps = new ArrayList<DatagramChannel>();
	
	//Incoming
	
	protected final ByteBuffer head = ByteBuffer.allocate(4), bin = ByteBuffer.allocate(8192);
	
	//Outgoing
	
	protected final SyncBuffer<Packet> outPkts = new SyncBuffer<Packet>(32);
	protected final ByteBuffer bout = ByteBuffer.allocate(8192 * 32);
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadNetwork(IPacketHandler h, IConnection con, int ups)
	{
		assert con != null;
		assert h != null;
		assert ups > 0;
		
		handler = h;
		connect = con;
		updateCount = ups;
		tcp = con.getChannel();
		
		Selector s = null;
		
		try
		{
			tcp.configureBlocking(false);
			
			s = Selector.open();
			tcp.register(s, SelectionKey.OP_READ | SelectionKey.OP_WRITE, ConnectionType.TCP);
			
		}
		catch (Exception e){}
		
		selector = s;
		
	}
	
	@Override
	public boolean initiate()
	{
		try
		{
			while(!this.tcp.finishConnect()){}
			
		}
		catch (IOException e){}
		
		return true;
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		int i;
		short type, length;
		List<Packet> pkts = null;
		Packet pkt;
		PacketFormat format;
		
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
					
					if (key.isReadable())
					{
						while (io.read(this.head) != -1)
						{
							type = this.head.getShort();//Get the packet type
							length = this.head.getShort();//Get the remaining packet length
							
							this.head.clear();//Clear the buffer for reuse
							
							format = this.handler.getPacketFormat(type);
							
							this.bin.limit(length);//Make sure we can't go over
							
							io.read(this.bin);//Read the data
							
							if (format != null && format.type.isCompatible((ConnectionType)key.attachment()))//NOW we check to see if the data in question is valid
							{
								//Huh, it is. Okay, let's read this thing...
								
								pkt = format.read(this.bin);//Excuse me Mr. Format, could you tell me what's going on?
								
								if (pkt != null)//Check if the packet has been read successfully.
								{
									if (pkts == null)//Dynamically load the packet list with a soft limit of 32 packets.
									{
										pkts = new ArrayList<Packet>(32);
										
									}
									
									//Schedule the packet to be sent to the game.
									
									i = pkts.indexOf(null);
									
									if (i == -1)
									{
										pkts.add(pkt);
										
									}
									else
									{
										pkts.set(i, pkt);
										
									}
									
								}
								
							}
							
							this.bin.clear();//Clear the incoming bytes to prepare for the next packet.
							
						}
						
						if (pkts != null)
						{
							this.handler.onPacketsReceived(this.connect, ImmutableList.copyOf(pkts));
							
						}
						
					}
					
					if (key.isWritable() && !this.outPkts.isEmpty())
					{
						Iterator<Packet> pktItr = this.outPkts.iterator();
						
						for (Packet pkt0 : this.outPkts)
						{
							pkt0 = pktItr.next();
							
							if (pkt0.format == null)
							{
								continue;
							}
							
							if (!pkt0.format.type.isCompatible((ConnectionType)key.attachment()))
							{
								continue;
							}
							
							if (!this.handler.validate(pkt0.format))
							{
								continue;
							}
							
							pkt0.format.write(pkt0, this.bout);
							
							this.outPkts.remove();
							
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
	public int getTargetUpdateCount()
	{
		return this.updateCount;
	}
	
	@Override
	public double getMaxDelta()
	{
		return 0.5;
	}
	
	@Override
	public void onThreadStopped()
	{
		try
		{
			this.update(0);
			
			this.selector.close();
			
			this.tcp.close();
			
			if (!this.udps.isEmpty())
			{
				for (DatagramChannel udp : this.udps)
				{
					udp.close();
					
				}
				
			}
			
		}
		catch (Throwable e){}
		
	}
	
	public synchronized void connectDatagram(IP ip)
	{
		DatagramChannel ch = null;
		
		try
		{
			ch = DatagramChannel.open();
			
			ch.bind(ip.toInet());
			ch.configureBlocking(false);
			
			ch.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, ConnectionType.UDP);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
		}
		
		if (ch != null)
		{
			this.udps.add(ch);
			
		}
		
	}
	
	public synchronized void sendPackets(Packet... pkts)
	{
		if (pkts == null || pkts.length == 0)
		{
			return;
		}
		
		this.outPkts.add(pkts);
		
	}
	
}
