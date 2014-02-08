
package com.elusivehawk.engine.tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.elusivehawk.engine.util.io.ByteWrapper;
import com.elusivehawk.engine.util.io.ByteWriter;
import com.elusivehawk.engine.util.io.Serializer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TagList implements ITag<Collection<ITag<?>>>, ITagList
{
	protected final String name;
	protected final List<ITag<?>> tags = new ArrayList<ITag<?>>();
	
	@SuppressWarnings("unqualified-field-access")
	public TagList(String title)
	{
		name = title;
		
	}
	
	@Override
	public byte getType()
	{
		return TagReaderRegistry.LIST_ID;
	}
	
	@Override
	public Collection<ITag<?>> getData()
	{
		return this.tags;
	}
	
	@Override
	public String getName()
	{
		return this.name;
	}
	
	@Override
	public int save(ByteWriter w)
	{
		int ret = Serializer.SHORT.toBytes(w, (short)this.tags.size());
		
		if (!this.tags.isEmpty())
		{
			for (ITag<?> tag : this.tags)
			{
				ret += TagReaderRegistry.instance().writeTag(w, tag);
				
			}
			
		}
		
		return ret;
	}
	
	@Override
	public ITag<?> getTag(String name)
	{
		for (ITag<?> tag : this.tags)
		{
			if (tag.getName().equals(name))
			{
				return tag;
			}
			
		}
		
		return null;
	}
	
	@Override
	public void addTag(ITag<?> tag)
	{
		this.tags.add(tag);
		
	}
	
	public static class ListReader implements ITagReader<Collection<ITag<?>>>
	{
		@Override
		public ITag<Collection<ITag<?>>> readTag(String name, ByteWrapper wrap)
		{
			TagList ret = new TagList(name);
			
			short size = Serializer.SHORT.fromBytes(wrap);
			ITag<?>[] tags = new ITag<?>[size];
			
			for (int c = 0; c < size; c++)
			{
				ITag<?> tag = TagReaderRegistry.instance().readTag(wrap);
				
				if (tag == null)
				{
					tags = null;
					
					break;
				}
				
				tags[c] = tag;
				
			}
			
			if (tags != null && tags.length > 0)
			{
				for (ITag<?> tag : tags)
				{
					ret.addTag(tag);
					
				}
				
			}
			
			return ret;
		}
		
	}
	
}
