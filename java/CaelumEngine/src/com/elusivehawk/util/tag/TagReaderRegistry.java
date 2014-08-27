
package com.elusivehawk.util.tag;

import com.elusivehawk.util.Logger;
import com.elusivehawk.util.io.IByteReader;
import com.elusivehawk.util.io.IByteWriter;
import com.elusivehawk.util.io.Serializer;
import com.elusivehawk.util.io.Serializers;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class TagReaderRegistry implements Serializer<Tag<?>>
{
	public static final byte BYTE_ID = 0x00;
	public static final byte SHORT_ID = 0x01;
	public static final byte INT_ID = 0x02;
	public static final byte LONG_ID = 0x03;
	public static final byte FLOAT_ID = 0x04;
	public static final byte DOUBLE_ID = 0x05;
	public static final byte STRING_ID = 0x06;
	public static final byte LIST_ID = 0x07;
	
	private static final TagReaderRegistry INSTANCE = new TagReaderRegistry();
	
	private final Serializer<?>[] serializers = new Serializer[256];
	
	private TagReaderRegistry()
	{
		this.registerReader(BYTE_ID, Serializers.BYTE);
		this.registerReader(SHORT_ID, Serializers.SHORT);
		this.registerReader(INT_ID, Serializers.INTEGER);
		this.registerReader(LONG_ID, Serializers.LONG);
		this.registerReader(FLOAT_ID, Serializers.FLOAT);
		this.registerReader(DOUBLE_ID, Serializers.DOUBLE);
		this.registerReader(STRING_ID, Serializers.STRING);
		this.registerReader(LIST_ID, new TagListReader());
		
	}
	
	public static TagReaderRegistry instance()
	{
		return INSTANCE;
	}
	
	public <T> Tag<T> read(IByteReader r)
	{
		String name = Serializers.STRING.fromBytes(r);
		
		byte id;
		Serializer<T> s = null;
		
		try
		{
			id = r.read();
			s  = this.getSerializer(id);
			
		}
		catch (Throwable e)
		{
			Logger.log().err(e);
			return null;
		}
		
		return new Tag<T>(name, id, s.fromBytes(r));
	}
	
	public <T> int write(IByteWriter w, Tag<T> tag)
	{
		Serializer<T> s = null;
		int length = 0;
		
		try
		{
			s  = this.getSerializer(tag.getReaderId());
			
		}
		catch (ClassCastException e)
		{
			Logger.log().err(e);
			
		}
		
		if (s != null)
		{
			length += Serializers.STRING.toBytes(w, tag.getName());
			length += s.toBytes(w, tag.getData());
			
		}
		
		return length;
	}
	
	@SuppressWarnings("unchecked")
	public <T> Serializer<T> getSerializer(byte id) throws ClassCastException
	{
		return (Serializer<T>)this.serializers[id];
	}
	
	public void registerReader(byte id, Serializer<?> s)
	{
		this.serializers[id] = s;
		
	}
	
	@Override
	public int toBytes(IByteWriter w, Tag<?> tag)
	{
		return this.write(w, tag);
	}
	
	@Override
	public Tag<?> fromBytes(IByteReader b)
	{
		return this.read(b);
	}
	
}
