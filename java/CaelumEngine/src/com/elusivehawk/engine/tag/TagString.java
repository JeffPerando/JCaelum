
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.math.BitHelper;
import com.elusivehawk.engine.util.Buffer;

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
	public void save(Buffer<Byte> buf)
	{
		if (this.str == null || "".equals(this.str))
		{
			buf.add((byte)0);
			return;
		}
		
		buf.add(BitHelper.createBytes((short)this.str.length()));
		
		for (int c = 0; c < this.str.length(); c++)
		{
			buf.add(BitHelper.createBytes((short)this.str.charAt(c)));
			
		}
		
	}
	
	public static class StringReader implements ITagReader<String>
	{
		@Override
		public ITag<String> readTag(String name, Buffer<Byte> buf)
		{
			String info = "";
			
			short length = BitHelper.createShort(buf);
			
			for (int c = 0; c < length; c++)
			{
				info += (char)BitHelper.createShort(buf);
				
			}
			
			return new TagString(name, info);
		}
		
	}
	
}
