
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.util.io.ByteWrapper;
import com.elusivehawk.engine.util.io.Serializer;

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
	public byte[] save()
	{
		return Serializer.DOUBLE.toBytes(this.d);
	}
	
	public static class DoubleReader implements ITagReader<Double>
	{
		@Override
		public ITag<Double> readTag(String name, ByteWrapper wrap)
		{
			return new TagDouble(name, Serializer.DOUBLE.fromBytes(wrap));
		}
		
	}
	
}
