
package com.elusivehawk.engine.util;

/**
 * 
 * Stores an object, which can only be changed a specific number of times.
 * 
 * @author Elusivehawk
 */
public class SemiFinalStorage<T> extends DirtableStorage<T>
{
	protected final int maxChanges;
	protected int count = 0;
	protected final IStorageListener<T> lis;
	
	public SemiFinalStorage(T object)
	{
		this(object, 1);
	}
	
	public SemiFinalStorage(T object, int changeCount)
	{
		this(object, changeCount, null);
		
	}
	
	public SemiFinalStorage(T object, IStorageListener<T> listener)
	{
		this(object, 1, listener);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public SemiFinalStorage(T object, int changeCount, IStorageListener<T> listener)
	{
		super(object);
		
		maxChanges = Math.max(changeCount, 1);
		lis = listener;
		
	}
	
	@Override
	public boolean set(T object)
	{
		if (this.locked())
		{
			return false;
		}
		
		if (this.lis != null && !this.lis.canChange(this))
		{
			return false;
		}
		
		super.set(object);
		
		this.count++;
		
		return true;
	}
	
	public boolean locked()
	{
		return this.count == this.maxChanges;
	}
	
	public int getChangeCount()
	{
		return this.count;
	}
	
	public static interface IStorageListener<T>
	{
		public boolean canChange(SemiFinalStorage<T> stor);
		
	}
	
}
