
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.util.Buffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TagByte implements ITag<Byte>
{
	protected final String name;
	protected final byte b;
	
	@SuppressWarnings("unqualified-field-access")
	public TagByte(String title, byte bite)
	{
		name = title;
		b = bite;
		
	}
	
	@Override
	public byte getType()
	{
		return TagReaderRegistry.BYTE_ID;
	}
	
	@Override
	public Byte getData()
	{
		return this.b;
	}
	
	@Override
	public String getName()
	{
		return this.name;
	}
	
	@Override
	public void save(Buffer<Byte> buf)
	{
		buf.put(this.getData());
		
	}
	
	public static class ByteReader implements ITagReader<Byte>
	{
		@Override
		public ITag<Byte> readTag(String name, Buffer<Byte> buf)
		{
			return new TagByte(name, buf.next());
		}
		
	}
	
}
