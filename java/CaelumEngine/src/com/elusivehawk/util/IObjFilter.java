
package com.elusivehawk.util;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IObjFilter<T>
{
	public T filter(int index, T obj);
	
}
