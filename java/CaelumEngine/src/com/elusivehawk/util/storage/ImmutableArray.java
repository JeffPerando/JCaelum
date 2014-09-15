
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
	public boolean isImmutable()
	{
		return true;
	}
	
	@Override
	public T get(int i)
	{
		return this.array[i];
	}
	
	@Override
	public IArray<T> set(int i, T obj)
	{
		if (this.isImmutable())
		{
			throw new UnsupportedOperationException();
		}
		
		this.array[i] = obj;
		
		return this;
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
