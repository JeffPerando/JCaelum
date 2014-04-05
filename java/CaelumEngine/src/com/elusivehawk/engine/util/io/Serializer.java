
package com.elusivehawk.engine.util.io;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface Serializer<T>
{
	public int toBytes(IByteWriter w, T obj);
	
	public T fromBytes(IByteReader r);
	
}
