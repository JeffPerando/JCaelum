
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
	public void save(DataOutputStream out) throws IOException
	{
		out.writeUTF(this.str);
		
	}
	
	public static class StringReader implements ITagReader<String>
	{
		@Override
		public ITag<String> readTag(String name, DataInputStream in) throws IOException
		{
			return new TagString(name, in.readUTF());
		}
		
	}
	
}
