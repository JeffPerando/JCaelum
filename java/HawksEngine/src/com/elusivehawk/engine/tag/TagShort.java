
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.core.Buffer;
import com.elusivehawk.engine.math.BitHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TagShort implements ITag<Short>
{
	protected final String name;
	protected final short s;
	
	@SuppressWarnings("unqualified-field-access")
	public TagShort(String title, short shrt)
	{
		name = title;
		s = shrt;
		
	}
	
	@Override
	public byte getType()
	{
		return TagReaderRegistry.SHORT_ID;
	}
	
	@Override
	public Short getData()
	{
		return this.s;
	}
	
	@Override
	public String getName()
	{
		return this.name;
	}
	
	@Override
	public void save(Buffer<Byte> buf)
	{
		buf.put(BitHelper.createBytes(this.s));
		
	}
	
	public static class ShortReader implements ITagReader<Short>
	{
		@Override
		public ITag<Short> readTag(String name, Buffer<Byte> buf)
		{
			return new TagShort(name, BitHelper.createShort(buf));
		}
		
	}
	
}
