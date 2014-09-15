
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
	
	public IArray<T> set(int i, T obj);
	
	default IArray<T> setImmutable()
	{
		return this;
	};
	
	default boolean isImmutable()
	{
		return false;
	}
	
}
