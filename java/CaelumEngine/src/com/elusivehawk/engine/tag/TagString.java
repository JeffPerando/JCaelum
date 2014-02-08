
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.util.io.ByteWrapper;
import com.elusivehawk.engine.util.io.ByteWriter;
import com.elusivehawk.engine.util.io.Serializer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TagString implements ITag<String>
{
	protected final String name, str;
	
	@SuppressWarnings("unqualified-field-access")
	public TagString(String title, String info)
	{
		name = title;
		str = info;
		
	}
	
	@Override
	public byte getType()
	{
		return TagReaderRegistry.STRING_ID;
	}
	
	@Override
	public String getData()
	{
		return this.str;
	}
	
	@Override
	public String getName()
	{
		return this.name;
	}
	
	@Override
	public int save(ByteWriter w)
	{
		return Serializer.STRING.toBytes(w, this.str);
	}
	
	public static class StringReader implements ITagReader<String>
	{
		@Override
		public ITag<String> readTag(String name, ByteWrapper wrap)
		{
			return new TagString(name, Serializer.STRING.fromBytes(wrap));
		}
		
	}
	
}
