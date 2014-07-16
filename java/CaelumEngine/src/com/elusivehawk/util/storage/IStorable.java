
package com.elusivehawk.util.storage;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IStorable<T>
{
	public void store(Buffer<T> buf);
	
}
