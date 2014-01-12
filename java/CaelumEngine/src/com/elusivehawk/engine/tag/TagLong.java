
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.math.BitHelper;
import com.elusivehawk.engine.util.Buffer;

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
	public void save(Buffer<Byte> buf)
	{
		buf.add(BitHelper.createBytes(this.l));
		
	}
	
	public static class LongReader implements ITagReader<Long>
	{
		@Override
		public ITag<Long> readTag(String name, Buffer<Byte> buf)
		{
			return new TagLong(name, BitHelper.createLongFromBytes(buf));
		}
		
	}
	
}
