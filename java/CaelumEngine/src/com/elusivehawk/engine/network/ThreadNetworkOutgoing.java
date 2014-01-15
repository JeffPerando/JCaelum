
package com.elusivehawk.engine.network;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import com.elusivehawk.engine.util.SyncBuffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ThreadNetworkOutgoing extends ThreadNetwork
{
	private BufferedOutputStream bos = null;
	private DataOutputStream out = null;
	
	private final SyncBuffer<Packet> outgoingPkts = new SyncBuffer<Packet>();
	
	public ThreadNetworkOutgoing(IPacketHandler h, Connection con, int ups)
	{
		super(h, con, ups);
		
	}
	
	@Override
	public boolean initiate()
	{
		OutputStream os = null;
		
		try
		{
			os = this.connect.getSocket().getOutputStream();
			
		}
		catch (Exception e)
		{
			return false;
		}
		
		this.bos = new BufferedOutputStream(os);
		this.out = new DataOutputStream(this.bos);
		
		return false;
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		if (this.outgoingPkts.isEmpty())
		{
			return;
		}
		
		for (Packet pkt : this.outgoingPkts)
		{
			this.outgoingPkts.setIsDirty(false);
			
			PacketFormat format = this.handler.getPacketFormat(pkt.pktId);
			boolean remove = false;
			
			if (format == null)
			{
				remove = true;
				
			}
			
			if (!this.handler.getSide().canSend(format.side))
			{
				remove = true;
				
			}
			
			if (pkt.getData().size() != format.format.size())
			{
				remove = true;
				
			}
			
			if (!remove)
			{
				this.out.writeShort(pkt.pktId);
				
				format.encodePkt(pkt, this.out);
				
			}
			
			this.outgoingPkts.remove();
			
		}
		
		this.out.flush();
		
	}
	
	public void sendPackets(Packet... pkts)
	{
		this.outgoingPkts.add(pkts);
		
	}
	
}
