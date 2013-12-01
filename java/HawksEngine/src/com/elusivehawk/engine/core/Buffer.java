
package com.elusivehawk.engine.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Buffer<T>
{
	protected final List<T> l;
	protected int pos = 0, mark = 0;
	
	public Buffer()
	{
		this(new ArrayList<T>());
		
	}
	
	@SafeVarargs
	public Buffer(T... array)
	{
		this(Arrays.asList(array));
		
	}
	
	public Buffer(List<T> list)
	{
		l = list;
		
	}
	
	public boolean hasNext()
	{
		return this.pos < this.l.size();
	}
	
	public T next()
	{
		return this.hasNext() ? this.l.get(this.pos++) : null;
	}
	
	public T previewNext()
	{
		return this.hasNext() ? this.l.get(this.pos + 1) : null;
	}
	
	public void add(T obj)
	{
		this.l.add(this.pos++, obj);
	}
	
	public int position()
	{
		return this.pos;
	}
	
	public void mark()
	{
		this.mark = this.pos;
		
	}
	
	public void eraseMarker()
	{
		this.mark = 0;
		
	}
	
	public void rewind()
	{
		this.pos = this.mark;
		
	}
	
}
