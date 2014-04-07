
package com.elusivehawk.engine.util;

/**
 * 
 * Contains two objects, and that's IT.
 * 
 * @author Elusivehawk
 */
public class Tuple<O, T>
{
	public O one;
	public T two;
	
	@SuppressWarnings("unqualified-field-access")
	public Tuple(O first, T second)
	{
		one = first;
		two = second;
		
	}
	
	public static <O, T> Tuple<O, T> create(O one, T two)
	{
		return new Tuple<O, T>(one, two);
	}
	
}
