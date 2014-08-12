
package com.elusivehawk.util.storage;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class SyncStorage<T> extends DirtableStorage<T>
{
	public SyncStorage(T object)
	{
		super(object);
		
	}
	
	@Override
	public synchronized boolean set(T object)
	{
		return super.set(object);
	}
	
}
