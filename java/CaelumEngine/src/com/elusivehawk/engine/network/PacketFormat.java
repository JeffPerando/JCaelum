
package com.elusivehawk.engine.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import com.elusivehawk.engine.tag.ITag;
import com.elusivehawk.engine.tag.TagReaderRegistry;
import com.google.common.collect.ImmutableList;

/**
 * 
 * The core class for how packets are interpreted.
 * 
 * @author Elusivehawk
 */
public final class PacketFormat
{
	public final Side side;
	public final short pktId;
	public final ImmutableList<DataType> format;
	
	/**
	 * 
	 * The primary constructor.
	 * 
	 * @param s The side the read packet (should) hail from. (i.e. Client -> Server should be CLIENT, Client <- Server should be SERVER.)
	 * @param id The ID for this format.
	 * @param f The format itself.
	 */
	@SuppressWarnings("unqualified-field-access")
	public PacketFormat(Side s, short id, DataType... f)
	{
		assert s != null;
		assert id != 0;
		assert f != null && f.length > 0;
		assert f[f.length - 1] != DataType.ARRAY;
		
		side = s;
		pktId = id;
		format = ImmutableList.copyOf(f);
		
	}
	
	public Packet decodePkt(DataInputStream in) throws IOException
	{
		Packet ret = new Packet(this.pktId);
		
		for (int c = 0; c < this.format.size(); c++)
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
	
	private Object decodeObj(int i, DataInputStream in) throws IOException
	{
		Object ret = null;
		DataType type = this.format.get(i);
		
		switch (type)
		{
			case BOOL: ret = in.readBoolean(); break;
			case BYTE: ret = in.readByte(); break;
			case SHORT: ret = in.readShort(); break;
			case INT: ret = in.readInt(); break;
			case LONG: ret = in.readLong(); break;
			case FLOAT: ret = in.readFloat(); break;
			case DOUBLE: ret = in.readDouble(); break;
			case STRING: ret = in.readUTF(); break;
			case ARRAY: ret = new Object[in.readInt()]; for (int c = 0; c < ((Object[])ret).length; c++){((Object[])ret)[c] = this.decodeObj(i + 1, in);}; break;
			case TAG: ret = TagReaderRegistry.instance().readTag(in); break;
			
		}
		
		return ret;
	}
	
	public void encodePkt(Packet pkt, DataOutputStream out) throws IOException
	{
		ImmutableList<Object> info = pkt.getData();
		
		for (int c = 0; c < this.format.size(); c++)
		{
			this.encodeObj(info.get(c), c, out);
			
		}
		
	}
	
	private void encodeObj(Object obj, int i, DataOutputStream out) throws IOException
	{
		DataType type = this.format.get(i);
		
		switch (type)
		{
			case BOOL: out.writeBoolean((Boolean)obj); break;
			case BYTE: out.writeByte((Byte)obj); break;
			case SHORT: out.writeShort((Short)obj); break;
			case INT: out.writeInt((Integer)obj); break;
			case LONG: out.writeLong((Long)obj); break;
			case FLOAT: out.writeFloat((Float)obj); break;
			case DOUBLE: out.writeDouble((Double)obj); break;
			case STRING: out.writeUTF((String)obj); break;
			case ARRAY: out.writeInt(((Object[])obj).length); for (Object object : (Object[])obj){encodeObj(object, i + 1, out);} break;
			case TAG: TagReaderRegistry.instance().writeTag(out, (ITag<?>)obj); break;
			
		}
		
	}
	
}
