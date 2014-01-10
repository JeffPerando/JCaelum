
package com.elusivehawk.engine.util;

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
