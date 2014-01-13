
package com.elusivehawk.engine.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	public void save(DataOutputStream out) throws IOException
	{
		out.writeInt(this.tags.size());
		
		if (this.tags.isEmpty())
		{
			return;
		}
		
		for (ITag<?> tag : this.tags)
		{
			TagReaderRegistry.instance().writeTag(out, tag);
			
		}
		
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
		public ITag<Collection<ITag<?>>> readTag(String name, DataInputStream in) throws IOException
		{
			TagList ret = new TagList(name);
			
			short size = in.readShort();
			ITag<?>[] tags = new ITag<?>[size];
			
			for (int c = 0; c < size; c++)
			{
				ITag<?> tag = TagReaderRegistry.instance().readTag(in);
				
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
