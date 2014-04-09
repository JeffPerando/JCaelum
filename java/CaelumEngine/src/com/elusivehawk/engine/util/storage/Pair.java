
package com.elusivehawk.engine.util.storage;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Pair<T> extends Tuple<T, T>
{
	public Pair(T first, T second)
	{
		super(first, second);
		
	}
	
	public static <T> Pair<T> createPair(T one, T two)
	{
		return new Pair<T>(one, two);
	}
	
}
