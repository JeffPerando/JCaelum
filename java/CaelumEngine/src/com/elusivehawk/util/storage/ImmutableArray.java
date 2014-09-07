
package com.elusivehawk.util.storage;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ImmutableArray<T> implements IArray<T>, Iterable<T>
{
	protected final T[] array;
	
	@SuppressWarnings("unqualified-field-access")
	public ImmutableArray(T[] a)
	{
		array = a;
		
	}
	
	@Override
	public Iterator<T> iterator()
	{
		return new Buffer<T>(this.array);
	}
	
	@Override
	public int length()
	{
		return this.array.length;
	}
	
	@Override
	public T get(int i)
	{
		return this.array[i];
	}
	
	public List<T> asList()
	{
		return Arrays.asList(this.array);
	}
	
	public static <T> ImmutableArray<T> create(T[] array)
	{
		return new ImmutableArray<T>(array);
	}
	
}
