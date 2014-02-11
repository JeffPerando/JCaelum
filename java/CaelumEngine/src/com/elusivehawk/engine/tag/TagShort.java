
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.util.io.ByteReader;
import com.elusivehawk.engine.util.io.ByteWriter;
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
	public int save(ByteWriter w)
	{
		return Serializer.SHORT.toBytes(w, this.s);
	}
	
	public static class ShortReader implements ITagReader<Short>
	{
		@Override
		public ITag<Short> readTag(String name, ByteReader wrap)
		{
			return new TagShort(name, Serializer.SHORT.fromBytes(wrap));
		}
		
	}
	
}
