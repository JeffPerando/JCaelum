
package com.elusivehawk.engine.network;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;
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
	protected final Connection connect;
	protected final SocketChannel sch;
	
	//Incoming
	
	protected final ByteBuffer head = ByteBuffer.allocate(4), bin = ByteBuffer.allocate(8192);
	
	//Outgoing
	
	protected final List<Packet> out = new ArrayList<Packet>(32);
	protected final ByteBuffer bout = ByteBuffer.allocate(8192 * 32);
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadNetwork(IPacketHandler h, Connection con, int ups)
	{
		assert con != null;
		assert h != null;
		assert ups > 0;
		
		handler = h;
		connect = con;
		updateCount = ups;
		sch = con.getChannel();
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		short type, length;
		List<Packet> pkts = new ArrayList<Packet>(32);
		Packet pkt;
		PacketFormat format;
		
		while (this.sch.read(this.head) != -1)
		{
			type = this.head.getShort();//Get the packet type
			length = this.head.getShort();//Get the remaining packet length
			
			this.head.clear();//Clear the buffer for reuse
			
			format = this.handler.getPacketFormat(type);
			
			this.bin.limit(length);//Make sure we can't go over
			
			this.sch.read(this.bin);//Read the data
			
			if (format != null)//NOW we check to see if the data in question is valid
			{
				//Huh, it is. Okay, let's read the thing...
				
				pkt = format.read(this.bin);//Excuse me Mr. Format, could you tell me what's going on?
				
				if (pkt != null)//Check if the packet has been read successfully.
				{
					pkts.add(pkt);//Schedule the packet to be sent to the game.
					
				}
				
			}
			
			this.bin.clear();//Clear the incoming bytes to prepare for the next packet.
			
		}
		
		if (!pkts.isEmpty())
		{
			this.handler.onPacketsReceived(this.connect, ImmutableList.copyOf(pkts));
			
		}
		
		if (!this.out.isEmpty())
		{
			Iterator<Packet> pktItr = this.out.iterator();
			
			while (pktItr.hasNext())
			{
				pkt = pktItr.next();
				
				format = this.handler.getPacketFormat(pkt.pktId);
				
				if (format == null)
				{
					continue;
				}
				
				format.write(pkt, this.bout);
				
				pktItr.remove();
				
			}
			
			this.bout.flip();
			this.sch.write(this.bout);
			
			for (int c = 0; c < this.bout.limit(); c++)
			{
				this.bout.put(c, (byte)0);
				
			}
			
			this.bout.clear();
			
		}
		
	}
	
	@Override
	public void handleException(Throwable e)
	{
		CaelumEngine.instance().getLog().log(EnumLogType.ERROR, null, e);
		
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
			
		}
		catch (Throwable e){}
		
	}
	
	public synchronized int sendPackets(Packet... pkts)
	{
		if (pkts == null || pkts.length == 0)
		{
			return 0;
		}
		
		int c = 0, i = -1;
		
		while ((i = this.out.indexOf(null)) != -1)
		{
			this.out.set(i, pkts[c++]);
			
		}
		
		return c;
	}
	
}
