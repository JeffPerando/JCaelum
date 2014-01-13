
package com.elusivehawk.engine.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
	public void save(DataOutputStream out) throws IOException
	{
		out.writeLong(this.l);
		
	}
	
	public static class LongReader implements ITagReader<Long>
	{
		@Override
		public ITag<Long> readTag(String name, DataInputStream in) throws IOException
		{
			return new TagLong(name, in.readLong());
		}
		
	}
	
}
