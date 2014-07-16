
package com.elusivehawk.util.storage;

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
	
	@Override
	public Pair<T> clone()
	{
		return new Pair<T>(this.one, this.two);
	}
	
	public static <T> Pair<T> createPair(T one, T two)
	{
		return new Pair<T>(one, two);
	}
	
}
