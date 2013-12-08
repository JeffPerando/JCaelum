
package com.elusivehawk.engine.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Buffer<T> implements IDirty, Iterator<T>
{
	protected final List<T> l;
	protected final List<Boolean> dirt = new ArrayList<Boolean>();
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
	
	@Override
	public boolean hasNext()
	{
		return this.pos < this.l.size();
	}
	
	@Override
	public T next()
	{
		return this.hasNext() ? this.l.get(this.pos++) : null;
	}
	
	@Override
	public void remove()
	{
		this.dirt.remove(this.pos);
		this.l.remove(this.pos);
		
	}
	
	public T previewNext()
	{
		return this.hasNext() ? this.l.get(this.pos + 1) : null;
	}
	
	public void add(T obj)
	{
		this.dirt.add(this.pos, true);
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
	
	public void norm()
	{
		this.mark = 0;
		
		for (int c = 0; c < this.dirt.size(); c++)
		{
			this.dirt.set(c, false);
			
		}
		
	}
	
	public void rewind()
	{
		this.pos = this.mark;
		
	}
	
	public boolean isDirty(int i)
	{
		return this.dirt.get(i);
	}
	
	@Override
	public boolean isDirty()
	{
		return this.isDirty(this.pos);
	}
	
	@Override
	public void setIsDirty(boolean b)
	{
		this.dirt.add(this.pos, b);
		
	}
	
}
