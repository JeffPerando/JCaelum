
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.util.io.ByteWrapper;

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
	public byte[] save()
	{
		return new byte[this.b];
	}
	
	public static class ByteReader implements ITagReader<Byte>
	{
		@Override
		public ITag<Byte> readTag(String name, ByteWrapper wrap)
		{
			return new TagByte(name, wrap.read());
		}
		
	}
	
}
