
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
	public void save(DataOutputStream out) throws IOException
	{
		out.writeDouble(this.d);
		
	}
	
	public static class DoubleReader implements ITagReader<Double>
	{
		@Override
		public ITag<Double> readTag(String name, DataInputStream in) throws IOException
		{
			return new TagDouble(name, in.readDouble());
		}
		
	}
	
}
