
package com.elusivehawk.engine.tag;

import java.util.ArrayList;
import java.util.List;
import com.elusivehawk.engine.util.io.ByteReader;
import com.elusivehawk.engine.util.io.ByteWriter;
import com.elusivehawk.engine.util.io.Serializer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TagListReader implements Serializer<List<Tag<?>>>
{
	@Override
	public int toBytes(ByteWriter w, List<Tag<?>> obj)
	{
		int ret = INTEGER.toBytes(w, obj  == null ? 0 : obj.size());
		
		if (obj == null)
		{
			return ret;
		}
		
		for (Tag<?> tag : obj)
		{
			ret += TagReaderRegistry.instance().toBytes(w, tag);
			
		}
		
		return ret;
	}
	
	@Override
	public List<Tag<?>> fromBytes(ByteReader r)
	{
		int length = INTEGER.fromBytes(r);
		
		List<Tag<?>> ret = new ArrayList<Tag<?>>(length);
		
		for (int c = 0; c < length; c++)
		{
			ret.set(c, TagReaderRegistry.instance().fromBytes(r));
			
		}
		
		return ret;
	}
	
}
