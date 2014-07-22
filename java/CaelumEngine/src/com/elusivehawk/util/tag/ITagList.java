
package com.elusivehawk.util.tag;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ITagList
{
	public int size();
	
	public void addTag(Tag<?> tag);
	
	public <T> Tag<T> getTag(String name);
	
	public <T> Tag<T> getTag(int index);
	
	default Boolean readBoolean(String name, Boolean d)
	{
		return this.readOther(name, d);
	}
	
	default Byte readByte(String name, Byte d)
	{
		return this.readOther(name, d);
	}
	
	default Float readFloat(String name, Float d)
	{
		return this.readOther(name, d);
	}
	
	default Double readDouble(String name, Double d)
	{
		return this.readOther(name, d);
	}
	
	default Integer readInt(String name, Integer d)
	{
		return this.readOther(name, d);
	}
	
	default ITagList readList(String name, ITagList d)
	{
		return this.readOther(name, d);
	}
	
	default Long readLong(String name, Long d)
	{
		return this.readOther(name, d);
	}
	
	default Short readShort(String name, Short d)
	{
		return this.readOther(name, d);
	}
	
	default String readString(String name, String d)
	{
		return this.readOther(name, d);
	}
	
	public <T> T readOther(String name, T d);
	
}
