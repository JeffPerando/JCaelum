
package com.elusivehawk.engine.tag;

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
	public Boolean readBoolean(String name)
	{
		return this.<Boolean>readOther(name);
	}
	
	@Override
	public Byte readByte(String name)
	{
		return this.<Byte>readOther(name);
	}
	
	@Override
	public Float readFloat(String name)
	{
		return this.<Float>readOther(name);
	}
	
	@Override
	public Double readDouble(String name)
	{
		return this.<Double>readOther(name);
	}
	
	@Override
	public Integer readInt(String name)
	{
		return this.<Integer>readOther(name);
	}
	
	@Override
	public ITagList readList(String name)
	{
		return this.<ITagList>readOther(name);
	}
	
	@Override
	public Long readLong(String name)
	{
		return this.<Long>readOther(name);
	}
	
	@Override
	public Short readShort(String name)
	{
		return this.<Short>readOther(name);
	}
	
	@Override
	public String readString(String name)
	{
		return this.<String>readOther(name);
	}
	
	@Override
	public <T> T readOther(String name)
	{
		Tag<T> ret = this.<T>getTag(name);
		
		return ret == null ? null : ret.getData();
	}
	
}
