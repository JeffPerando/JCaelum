
package com.elusivehawk.engine.util.storage;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class SyncBuffer<T> extends Buffer<T>
{
	public SyncBuffer()
	{
		this(new SyncList<T>());
		
	}
	
	public SyncBuffer(int limit)
	{
		this(new SyncList<T>(limit));
		
	}
	
	@SafeVarargs
	public SyncBuffer(T... array)
	{
		this(new SyncList<T>(Arrays.asList(array)));
		
	}
	
	public SyncBuffer(List<T> list)
	{
		super(list);
		
	}
	
	public SyncBuffer(IStorable<T> stor)
	{
		this();
		
		stor.store(this);
		
		rewind();
		
	}
	
}
