
package com.elusivehawk.engine.util.storage;

import java.util.Iterator;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ImmutableArray<T> implements Iterable<T>
{
	protected final T[] array;
	
	@SuppressWarnings("unqualified-field-access")
	public ImmutableArray(T[] a)
	{
		array = a;
		
	}
	
	public int length()
	{
		return this.array.length;
	}
	
	public T get(int i)
	{
		return this.array[i];
	}
	
	public static <T> ImmutableArray<T> create(T[] array)
	{
		return new ImmutableArray<T>(array);
	}
	
	@Override
	public Iterator<T> iterator()
	{
		return new Buffer<T>(this.array);
	}
	
}
