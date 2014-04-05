
package com.elusivehawk.engine.tag;

import java.util.ArrayList;
import java.util.List;
import com.elusivehawk.engine.util.io.IByteReader;
import com.elusivehawk.engine.util.io.IByteWriter;
import com.elusivehawk.engine.util.io.Serializer;
import com.elusivehawk.engine.util.io.Serializers;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TagListReader implements Serializer<List<Tag<?>>>
{
	@Override
	public int toBytes(IByteWriter w, List<Tag<?>> obj)
	{
		int ret = Serializers.INTEGER.toBytes(w, obj  == null ? 0 : obj.size());
		
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
	public List<Tag<?>> fromBytes(IByteReader r)
	{
		int length = Serializers.INTEGER.fromBytes(r);
		
		List<Tag<?>> ret = new ArrayList<Tag<?>>(length);
		
		for (int c = 0; c < length; c++)
		{
			ret.set(c, TagReaderRegistry.instance().fromBytes(r));
			
		}
		
		return ret;
	}
	
}
