
package com.elusivehawk.engine.tag;

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
	
	public Boolean readBoolean(String name, Boolean d);
	
	public Byte readByte(String name, Byte d);
	
	public Float readFloat(String name, Float d);
	
	public Double readDouble(String name, Double d);
	
	public Integer readInt(String name, Integer d);
	
	public ITagList readList(String name, ITagList d);
	
	public Long readLong(String name, Long d);
	
	public Short readShort(String name, Short d);
	
	public String readString(String name, String d);
	
	public <T> T readOther(String name, T d);
	
}
