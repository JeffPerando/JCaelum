
package com.elusivehawk.engine.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class PacketFormat
{
	private final Side side;
	private final short pktId;
	private final DataType[] format;
	
	@SuppressWarnings("unqualified-field-access")
	public PacketFormat(Side s, short id, DataType... f)
	{
		assert s != null;
		assert id != 0;
		assert f != null;
		
		side = s;
		pktId = id;
		format = f;
		
	}
	
	public Side getSide()
	{
		return this.side;
	}
	
	public short getId()
	{
		return this.pktId;
	}
	
	public DataType[] getFormat()
	{
		return this.format;
	}
	
	public Packet decodePkt(DataInputStream in) throws IOException
	{
		Packet ret = new Packet(this.pktId);
		
		for (int c = 0; c < this.format.length; c++)
		{
			Object obj = this.decodeObj(c, in);
			
			if (obj == null)
			{
				return null;
			}
			
			ret.addData(obj);
			
		}
		
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	private Object decodeObj(int i, DataInputStream in) throws IOException
	{
		Object obj = null;
		DataType type = this.format[i];
		
		switch (type)
		{
			case BYTE: obj = in.readByte(); break;
			case SHORT: obj = in.readShort(); break;
			case INT: obj = in.readInt(); break;
			case LONG: obj = in.readLong(); break;
			case FLOAT: obj = in.readFloat(); break;
			case DOUBLE: obj = in.readDouble(); break;
			case STRING: obj = in.readUTF(); break;
			case LIST: if (i == this.format.length - 1){return null;} obj = new ArrayList<Object>(); int size = in.readInt(); for (int c = 0; c < size; c++){((List<Object>)obj).add(this.decodeObj(i + 1, in));}; break;
		}
		
		return obj;
	}
	
	public void encodePkt(Packet pkt, DataOutputStream out) throws IOException
	{
		for (int c = 0; c < this.format.length; c++)
		{
			List<Object> info = pkt.getData();
			
			this.encodeObj(info.get(c), c, out);
			
		}
		
	}
	
	private void encodeObj(Object obj, int i, DataOutputStream out) throws IOException
	{
		DataType type = this.format[i];
		
		switch (type)
		{
			case BYTE: out.writeByte((Byte)obj); break;
			case SHORT: out.writeShort((Short)obj); break;
			case INT: out.writeInt((Integer)obj); break;
			case LONG: out.writeLong((Long)obj); break;
			case FLOAT: out.writeFloat((Float)obj); break;
			case DOUBLE: out.writeDouble((Double)obj); break;
			case STRING: out.writeUTF((String)obj); break;
			case LIST: if (i == this.format.length - 1){return;} out.writeInt(((List<?>)obj).size()); for (Object object : (List<?>)obj){encodeObj(object, i + 1, out);} break;
			
		}
		
	}
	
	
}
