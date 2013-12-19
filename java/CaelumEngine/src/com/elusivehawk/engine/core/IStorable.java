
package com.elusivehawk.engine.core;

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
