
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.core.Buffer;
import com.elusivehawk.engine.core.GameLog;
import com.elusivehawk.engine.math.BitHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class TagReaderRegistry
{
	public static final byte BYTE_ID = 0;
	public static final byte SHORT_ID = 1;
	public static final byte INT_ID = 2;
	public static final byte LONG_ID = 3;
	public static final byte FLOAT_ID = 4;
	public static final byte DOUBLE_ID = 5;
	public static final byte STRING_ID = 6;
	public static final byte LIST_ID = 7;
	
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
		this.readers[Math.abs(id)] = reader;
		
	}
	
	public ITagReader<?> get(byte id)
	{
		return this.readers[Math.abs(id)];
	}
	
	public ITag<?> readTag(Buffer<Byte> buf)
	{
		byte length = buf.next();
		String name = "";
		
		for (int c = 0; c < length; c++)
		{
			name += (char)BitHelper.createShort(buf);
			
		}
		
		byte id = buf.next();
		ITagReader<?> r = this.get(id);
		
		if (r == null)
		{
			GameLog.error("Could not continue reading tag, invalid tag ID: " + id);
			
			return null;
		}
		
		return r.readTag(name, buf);
	}
	
	public void writeTag(Buffer<Byte> buf, ITag<?> tag)
	{
		String name = tag.getName();
		buf.put((byte)name.length());
		
		for (int c = 0; c < name.length(); c++)
		{
			buf.put(BitHelper.createBytes((short)name.charAt(c)));
			
		}
		
		byte type = tag.getType();
		
		if (this.get(type) == null)
		{
			GameLog.warn("Tag " + name + "has invalid type ID " + type + ", please rectify.");
			
		}
		
		buf.put(type);
		
		tag.save(buf);
		
	}
	
}
