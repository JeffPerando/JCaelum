
package com.elusivehawk.engine.util;

/**
 * 
 * Stores an object, which if modified will set off the {@link #isDirty()} flag.
 * 
 * @author Elusivehawk
 */
public class DirtableStorage<T> implements IDirty
{
	protected T obj;
	protected boolean dirty = false;
	
	@SuppressWarnings("unqualified-field-access")
	public DirtableStorage(T object)
	{
		obj = object;
		
	}
	
	public T get()
	{
		return this.obj;
	}
	
	public boolean set(T object)
	{
		this.obj = object;
		
		this.setIsDirty(true);
		
		return true;
	}
	
	@Override
	public boolean isDirty()
	{
		return this.dirty;
	}
	
	@Override
	public void setIsDirty(boolean b)
	{
		this.dirty = b;
		
	}
	
}
