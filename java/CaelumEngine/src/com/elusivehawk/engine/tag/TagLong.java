
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.util.io.ByteWrapper;
import com.elusivehawk.engine.util.io.Serializer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TagLong implements ITag<Long>
{
	protected final String name;
	protected final long l;
	
	@SuppressWarnings("unqualified-field-access")
	public TagLong(String title, long lng)
	{
		name = title;
		l = lng;
		
	}
	
	@Override
	public byte getType()
	{
		return TagReaderRegistry.LONG_ID;
	}
	
	@Override
	public Long getData()
	{
		return this.l;
	}
	
	@Override
	public String getName()
	{
		return this.name;
	}
	
	@Override
	public byte[] save()
	{
		return Serializer.LONG.toBytes(this.l);
	}
	
	public static class LongReader implements ITagReader<Long>
	{
		@Override
		public ITag<Long> readTag(String name, ByteWrapper wrap)
		{
			return new TagLong(name, Serializer.LONG.fromBytes(wrap));
		}
		
	}
	
}
