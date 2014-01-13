
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
public class TagFloat implements ITag<Float>
{
	protected final String name;
	protected final float f;
	
	@SuppressWarnings("unqualified-field-access")
	public TagFloat(String title, float flt)
	{
		name = title;
		f = flt;
		
	}
	
	@Override
	public byte getType()
	{
		return TagReaderRegistry.FLOAT_ID;
	}
	
	@Override
	public Float getData()
	{
		return this.f;
	}
	
	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeFloat(this.f);
		
	}
	
	public static class FloatReader implements ITagReader<Float>
	{
		@Override
		public ITag<Float> readTag(String name, DataInputStream in) throws IOException
		{
			return new TagFloat(name, in.readFloat());
		}
		
	}
	
}
