
package com.elusivehawk.engine.util;

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
	
	@Override
	public synchronized void setIsDirty(boolean b)
	{
		super.setIsDirty(b);
		
	}
	
}
