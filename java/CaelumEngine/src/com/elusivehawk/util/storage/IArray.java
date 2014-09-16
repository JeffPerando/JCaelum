
package com.elusivehawk.util.storage;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IArray<T>
{
	public int size();
	
	public T get(int i);
	
	public IArray<? extends T> set(int i, T obj);
	
	default IArray<? extends T> setImmutable()
	{
		return this;
	};
	
	default boolean isImmutable()
	{
		return false;
	}
	
}
