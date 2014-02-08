
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.util.io.ByteWrapper;
import com.elusivehawk.engine.util.io.Serializer;

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
	public byte[] save()
	{
		return Serializer.SHORT.toBytes(this.s);
	}
	
	public static class ShortReader implements ITagReader<Short>
	{
		@Override
		public ITag<Short> readTag(String name, ByteWrapper wrap)
		{
			return new TagShort(name, Serializer.SHORT.fromBytes(wrap));
		}
		
	}
	
}
