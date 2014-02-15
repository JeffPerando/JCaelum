
package com.elusivehawk.engine.tag;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ITagList
{
	public void addTag(Tag<?> tag);
	
	public <T> Tag<T> getTag(String name);
	
	public Boolean readBoolean(String name);
	
	public Byte readByte(String name);
	
	public Float readFloat(String name);
	
	public Double readDouble(String name);
	
	public Integer readInt(String name);
	
	public ITagList readList(String name);
	
	public Long readLong(String name);
	
	public Short readShort(String name);
	
	public String readString(String name);
	
	public <T> T readOther(String name);
	
}
