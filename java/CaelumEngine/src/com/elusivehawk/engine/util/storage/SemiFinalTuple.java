
package com.elusivehawk.engine.util.storage;

/**
 * 
 * Created to help size down extremely large constructors when using {@link SemiFinalStorage} and {@link Tuple}.
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
	
	public static <O, T> SemiFinalTuple<O, T> create(O one, T two, int changeCount)
	{
		return new SemiFinalTuple<O, T>(one, two, changeCount);
	}
	
}
