
package com.elusivehawk.engine.core;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Tuple<T, E>
{
	public T one;
	public E two;
	
	@SuppressWarnings("unqualified-field-access")
	public Tuple(T first, E second)
	{
		one = first;
		two = second;
		
	}
	
}
