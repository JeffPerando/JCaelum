
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
public class TagByte implements ITag<Byte>
{
	protected final String name;
	protected final byte b;
	
	@SuppressWarnings("unqualified-field-access")
	public TagByte(String title, byte bite)
	{
		name = title;
		b = bite;
		
	}
	
	@Override
	public byte getType()
	{
		return TagReaderRegistry.BYTE_ID;
	}
	
	@Override
	public Byte getData()
	{
		return this.b;
	}
	
	@Override
	public String getName()
	{
		return this.name;
	}
	
	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeByte(this.b);
		
	}
	
	public static class ByteReader implements ITagReader<Byte>
	{
		@Override
		public ITag<Byte> readTag(String name, DataInputStream in) throws IOException
		{
			return new TagByte(name, in.readByte());
		}
		
	}
	
}
