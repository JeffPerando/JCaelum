
package com.elusivehawk.engine.util;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class SemiFinalTuple<T, E> extends Tuple<SemiFinalStorage<T>, SemiFinalStorage<E>>
{
	public SemiFinalTuple(T one, E two)
	{
		this(one, two, 1);
		
	}
	
	public SemiFinalTuple(T one, E two, int changes)
	{
		super(new SemiFinalStorage<T>(one, changes), new SemiFinalStorage<E>(two, changes));
		
	}
	
}
