
package com.elusivehawk.engine.tag;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Tag<T>
{
	protected final String name;
	protected final byte id;
	protected T data;
	
	public Tag(String title, byte sid)
	{
		this(title, sid, null);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Tag(String title, byte sid, T info)
	{
		name = title;
		data = info;
		id = sid;
		
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public byte getReaderId()
	{
		return this.id;
	}
	
	public T getData()
	{
		return this.data;
	}
	
	public void setData(T info)
	{
		this.data = info;
		
	}
	
}
