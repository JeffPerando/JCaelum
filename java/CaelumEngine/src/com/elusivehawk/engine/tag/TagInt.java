
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.math.BitHelper;
import com.elusivehawk.engine.util.Buffer;

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
	public void save(Buffer<Byte> buf)
	{
		buf.add(BitHelper.createBytes(this.i));
		
	}
	
	public static class IntReader implements ITagReader<Integer>
	{
		@Override
		public ITag<Integer> readTag(String name, Buffer<Byte> buf)
		{
			return new TagInt(name, BitHelper.createIntFromBytes(buf));
		}
		
	}
	
}
