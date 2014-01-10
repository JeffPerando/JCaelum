
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.math.BitHelper;
import com.elusivehawk.engine.util.Buffer;

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
	public void save(Buffer<Byte> buf)
	{
		buf.put(BitHelper.createBytes(this.f));
		
	}
	
	public static class FloatReader implements ITagReader<Float>
	{
		@Override
		public ITag<Float> readTag(String name, Buffer<Byte> buf)
		{
			return new TagFloat(name, Float.intBitsToFloat(BitHelper.createIntFromBytes(buf)));
		}
		
	}
	
}
