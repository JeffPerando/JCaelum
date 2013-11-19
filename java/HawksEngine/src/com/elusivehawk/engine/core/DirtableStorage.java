
package com.elusivehawk.engine.core;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class DirtableStorage<T> implements IDirty
{
	protected T obj;
	protected boolean dirty = true;
	
	public DirtableStorage(T object)
	{
		obj = object;
		
	}
	
	public T get()
	{
		return this.obj;
	}
	
	public void set(T object)
	{
		this.obj = object;
		
		this.setIsDirty(true);
		
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
