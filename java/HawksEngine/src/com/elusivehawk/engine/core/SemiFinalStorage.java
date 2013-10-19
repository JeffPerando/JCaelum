
package com.elusivehawk.engine.core;

/**
 * 
 * Stores an object, which can only be changed a specific number of times.
 * 
 * @author Elusivehawk
 */
public class SemiFinalStorage<T>
{
	protected T obj;
	protected final int maxChanges;
	protected int count = 0;
	
	public SemiFinalStorage(T object)
	{
		this(object, 1);
	}
	
	public SemiFinalStorage(T object, int changeCount)
	{
		obj = object;
		maxChanges = Math.max(changeCount, 1);
		
	}
	
	public boolean modify(T object)
	{
		if (this.locked())
		{
			return false;
		}
		
		this.count++;
		this.obj = object;
		return true;
	}
	
	public T get()
	{
		return this.obj;
	}
	
	public boolean locked()
	{
		return this.count == this.maxChanges;
	}
	
	public int getChangeCount()
	{
		return this.count;
	}
	
}
