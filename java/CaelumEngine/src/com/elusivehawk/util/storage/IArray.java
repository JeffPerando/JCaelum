
package com.elusivehawk.util.storage;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IArray<T>
{
	public int length();
	
	public boolean isImmutable();
	
	public T get(int i);
	
	public IArray<T> set(int i, T obj);
	
}
