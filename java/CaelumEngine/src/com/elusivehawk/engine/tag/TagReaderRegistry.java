
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;
import com.elusivehawk.engine.util.BufferHelper;
import com.elusivehawk.engine.util.io.ByteWrapper;
import com.elusivehawk.engine.util.io.Serializer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class TagReaderRegistry implements Serializer<ITag<?>>
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
	
	private final ITagReader<?>[] readers = new ITagReader<?>[256];
	
	private TagReaderRegistry()
	{
		this.register(BYTE_ID, new TagByte.ByteReader());
		this.register(SHORT_ID, new TagShort.ShortReader());
		this.register(INT_ID, new TagInt.IntReader());
		this.register(LONG_ID, new TagLong.LongReader());
		this.register(FLOAT_ID, new TagFloat.FloatReader());
		this.register(DOUBLE_ID, new TagDouble.DoubleReader());
		this.register(STRING_ID, new TagString.StringReader());
		this.register(LIST_ID, new TagList.ListReader());
		
	}
	
	public static TagReaderRegistry instance()
	{
		return INSTANCE;
	}
	
	public void register(byte id, ITagReader<?> reader)
	{
		if (this.getReader(id) != null)
		{
			CaelumEngine.instance().getLog().log(EnumLogType.WARN, "Overriding tag reader ID #" + id);
			
		}
		
		this.readers[id] = reader;
		
	}
	
	public ITagReader<?> getReader(byte id)
	{
		return this.readers[id];
	}
	
	public ITag<?> readTag(ByteWrapper wrap)
	{
		String name = Serializer.STRING.fromBytes(wrap);
		
		byte id = wrap.read();
		ITagReader<?> r = this.getReader(id);
		
		if (r == null)
		{
			CaelumEngine.instance().getLog().log(EnumLogType.ERROR, "Could not continue reading tag, invalid tag ID: " + id);
			
			return null;
		}
		
		return r.readTag(name, wrap);
	}
	
	public byte[] writeTag(ITag<?> tag)
	{
		byte[][] ret = new byte[3][];
		
		ret[0] = Serializer.STRING.toBytes(tag.getName());
		
		byte type = tag.getType();
		
		if (this.getReader(type) == null)
		{
			CaelumEngine.instance().getLog().log(EnumLogType.WARN, "Tag " + tag.getName() + " has invalid type ID " + type + ", please rectify.");
			
		}
		
		ret[1] = new byte[]{type};
		
		ret[2] = tag.save();
		
		return BufferHelper.condense(ret);
	}
	
	@Override
	public byte[] toBytes(ITag<?> tag)
	{
		return this.writeTag(tag);
	}
	
	@Override
	public ITag<?> fromBytes(ByteWrapper b)
	{
		return this.readTag(b);
	}
	
}
