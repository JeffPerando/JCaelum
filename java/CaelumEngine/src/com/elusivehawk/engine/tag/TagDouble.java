
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.math.BitHelper;
import com.elusivehawk.engine.util.Buffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TagDouble implements ITag<Double>
{
	protected final String name;
	protected final double d;
	
	@SuppressWarnings("unqualified-field-access")
	public TagDouble(String title, double dbl)
	{
		 name = title;
		 d = dbl;
		 
	}
	
	@Override
	public byte getType()
	{
		return TagReaderRegistry.DOUBLE_ID;
	}
	
	@Override
	public Double getData()
	{
		return this.d;
	}
	
	@Override
	public String getName()
	{
		return this.name;
	}
	
	@Override
	public void save(Buffer<Byte> buf)
	{
		buf.add(BitHelper.createBytes(this.d));
		
	}
	
	public static class DoubleReader implements ITagReader<Double>
	{
		@Override
		public ITag<Double> readTag(String name, Buffer<Byte> buf)
		{
			return new TagDouble(name, Double.longBitsToDouble(BitHelper.createLongFromBytes(buf)));
		}
		
	}
	
}
