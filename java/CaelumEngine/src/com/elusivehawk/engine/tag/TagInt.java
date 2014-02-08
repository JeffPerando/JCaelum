
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.util.io.ByteWrapper;
import com.elusivehawk.engine.util.io.Serializer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TagInt implements ITag<Integer>
{
	protected final String name;
	protected final int i;
	
	@SuppressWarnings("unqualified-field-access")
	public TagInt(String title, int in)
	{
		name = title;
		i = in;
		
	}
	
	@Override
	public byte getType()
	{
		return TagReaderRegistry.INT_ID;
	}
	
	@Override
	public Integer getData()
	{
		return this.i;
	}

	@Override
	public String getName()
	{
		return this.name;
	}
	
	@Override
	public byte[] save()
	{
		return Serializer.INTEGER.toBytes(this.i);
	}
	
	public static class IntReader implements ITagReader<Integer>
	{
		@Override
		public ITag<Integer> readTag(String name, ByteWrapper wrap)
		{
			return new TagInt(name, Serializer.INTEGER.fromBytes(wrap));
		}
		
	}
	
}
