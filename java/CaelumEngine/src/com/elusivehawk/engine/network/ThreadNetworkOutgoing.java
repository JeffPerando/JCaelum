
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
	private final IPacketHandler handler;
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadNetworkOutgoing(IPacketHandler h, Connection con, int ups)
	{
		super(con, ups);
		handler = h;
		
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
			
			if (!format.getSide().belongsOnSide(this.handler.getSide()))
			{
				remove = true;
				
			}
			
			if (pkt.getData().size() != format.getFormat().length)
			{
				remove = true;
				
			}
			
			if (!remove)
			{
				this.out.writeShort(pkt.pktId);
				
				for (int c = 0; c < format.getFormat().length; c++)
				{
					DataType type = format.getFormat()[c];
					Object obj = pkt.getData().get(c);
					
					switch (type)
					{
						case BYTE: this.out.writeByte((Byte)obj); break;
						case SHORT: this.out.writeShort((Short)obj); break;
						case INT: this.out.writeInt((Integer)obj); break;
						case LONG: this.out.writeLong((Long)obj); break;
						case FLOAT: this.out.writeFloat((Float)obj); break;
						case DOUBLE: this.out.writeDouble((Double)obj); break;
						case STRING: this.out.writeUTF((String)obj); break;
						default: continue;
					}
					
				}
				
			}
			
			this.outgoingPkts.remove();
			
		}
		
		this.out.flush();
		
	}
	
	@Override
	public void onThreadStopped()
	{
		try
		{
			this.out.close();
			
		}
		catch (Exception e)
		{
			this.handleException(e);
			
		}
		
	}
	
	public void sendPackets(Packet... pkts)
	{
		this.outgoingPkts.add(pkts);
		
	}
	
}
