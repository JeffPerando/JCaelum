
package com.elusivehawk.engine.network;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;
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
	private BufferedInputStream bis = null;
	private DataInputStream in = null;
	
	public ThreadNetworkIncoming(IHost host, Socket skt, int ups) throws Exception
	{
		super(host, skt, ups);
		
	}
	
	@Override
	public boolean initiate()
	{
		InputStream is = null;
		
		try
		{
			is = this.skt.getInputStream();
			
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
			
			PacketFormat format = this.host.getPacketFormat(id);
			
			if (format == null || format.getId() != id)
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
		
		this.host.onPacketsReceived(pkts);
		
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
