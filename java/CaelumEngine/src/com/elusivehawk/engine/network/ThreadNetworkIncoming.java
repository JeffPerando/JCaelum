
package com.elusivehawk.engine.network;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ThreadNetworkIncoming extends ThreadNetwork 
{
	private final IPacketHandler receiver;
	
	private BufferedInputStream bis = null;
	private DataInputStream in = null;
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadNetworkIncoming(IPacketHandler r, Connection con, int ups) throws Exception
	{
		super(con, ups);
		
		assert r != null;
		
		receiver = r;
		
	}
	
	@Override
	public boolean initiate()
	{
		InputStream is = null;
		
		try
		{
			is = this.connect.getSocket().getInputStream();
			
		}
		catch (Exception e)
		{
			return false;
		}
		
		this.bis = new BufferedInputStream(is);
		this.in = new DataInputStream(this.bis);
		
		return false;
	}
	
	@Override
	public void update(double delta) throws Exception
	{
		List<Packet> pkts = null;
		
		if (this.in.available() > 0)
		{
			pkts = new ArrayList<Packet>();
			
		}
		
		if (pkts == null)
		{
			return;
		}
		
		while (this.in.available() > 0)
		{
			short id = this.in.readShort();
			
			PacketFormat format = this.receiver.getPacketFormat(id);
			
			if (format == null || format.getId() != id || !format.getSide().belongsOnSide(this.receiver.getSide()))
			{
				this.in.skip(this.in.available());
				return;
			}
			
			Packet pkt = new Packet(id);
			
			for (DataType type : format.getFormat())
			{
				if (type == null)
				{
					continue;
				}
				
				Object obj = null;
				
				switch (type)
				{
					case BYTE: obj = this.in.readByte(); break;
					case SHORT: obj = this.in.readShort(); break;
					case INT: obj = this.in.readInt(); break;
					case LONG: obj = this.in.readLong(); break;
					case FLOAT: obj = this.in.readFloat(); break;
					case DOUBLE: obj = this.in.readDouble(); break;
					case STRING: obj = this.in.readUTF(); break;
					default: continue;
				}
				
				pkt.addData(obj);
				
			}
			
			pkts.add(pkt);
			
		}
		
		this.receiver.onPacketsReceived(this.connect, pkts);
		
	}
	
	@Override
	public void onThreadStopped()
	{
		try
		{
			this.in.close();
			
		}
		catch (Exception e)
		{
			this.handleException(e);
			
		}
		
	}
	
}
