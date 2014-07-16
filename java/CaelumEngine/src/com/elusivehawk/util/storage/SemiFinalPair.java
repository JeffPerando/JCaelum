
package com.elusivehawk.util.storage;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class SemiFinalPair<T> extends Pair<SemiFinalStorage<T>>
{
	public SemiFinalPair(T first, T second)
	{
		super(SemiFinalStorage.create(first), SemiFinalStorage.create(second));
		
	}
	
	public SemiFinalPair(T first, T second, int c)
	{
		super(SemiFinalStorage.create(first, c), SemiFinalStorage.create(second, c));
		
	}
	
}
