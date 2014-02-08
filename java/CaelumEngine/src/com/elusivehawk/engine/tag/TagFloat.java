
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.util.io.ByteWrapper;
import com.elusivehawk.engine.util.io.ByteWriter;
import com.elusivehawk.engine.util.io.Serializer;

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
	public int save(ByteWriter w)
	{
		return Serializer.FLOAT.toBytes(w, this.f);
	}
	
	public static class FloatReader implements ITagReader<Float>
	{
		@Override
		public ITag<Float> readTag(String name, ByteWrapper wrap)
		{
			return new TagFloat(name, Serializer.FLOAT.fromBytes(wrap));
		}
		
	}
	
}
