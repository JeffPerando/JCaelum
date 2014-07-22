
package com.elusivehawk.util.tag;

import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TagList implements ITagList
{
	protected final List<Tag<?>> tags;
	
	@SuppressWarnings("unqualified-field-access")
	public TagList(List<Tag<?>> info)
	{
		tags = info;
		
	}
	
	@Override
	public int size()
	{
		return this.tags.size();
	}
	
	@Override
	public void addTag(Tag<?> tag)
	{
		int i = -1;
		
		for (int c = 0; c < this.tags.size(); c++)
		{
			if (this.tags.get(c).getName() == tag.getName())
			{
				i = c;
				
				break;
			}
			
		}
		
		if (i == -1)
		{
			this.tags.add(tag);
			
		}
		else
		{
			this.tags.set(i, tag);
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Tag<T> getTag(String name)
	{
		if (this.tags.isEmpty())
		{
			return null;
		}
		
		Tag<T> ret = null;
		
		for (Tag<?> tag : this.tags)
		{
			if (tag.getName() == name)
			{
				try
				{
					ret = (Tag<T>)tag;
					
				}
				catch (ClassCastException e)
				{
					e.printStackTrace();
					
				}
				
			}
			
		}
		
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Tag<T> getTag(int index)
	{
		Tag<T> ret = null;
		
		try
		{
			ret = (Tag<T>)this.tags.get(index);
			
		}
		catch(ClassCastException e)
		{
			e.printStackTrace();
			
		}
		
		return ret;
	}
	
	@Override
	public <T> T readOther(String name, T d)
	{
		Tag<T> ret = this.<T>getTag(name);
		
		return ret == null ? d : ret.getData();
	}
	
}
