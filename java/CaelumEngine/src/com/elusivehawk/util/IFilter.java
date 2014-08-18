
package com.elusivehawk.util;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IFilter<T>
{
	public T filter(int index, T obj);
	
}
