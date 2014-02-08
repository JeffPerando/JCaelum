
package com.elusivehawk.engine.tag;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ITag<T>
{
	public byte getType();
	
	public T getData();
	
	public String getName();
	
	public byte[] save();
	
}
