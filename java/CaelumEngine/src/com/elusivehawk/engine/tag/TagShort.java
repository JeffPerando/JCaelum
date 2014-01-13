
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
	public void save(DataOutputStream out) throws IOException
	{
		out.writeShort(this.s);
		
	}
	
	public static class ShortReader implements ITagReader<Short>
	{
		@Override
		public ITag<Short> readTag(String name, DataInputStream in) throws IOException
		{
			return new TagShort(name, in.readShort());
		}
		
	}
	
}
