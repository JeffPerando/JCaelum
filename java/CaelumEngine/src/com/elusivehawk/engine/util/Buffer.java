
package com.elusivehawk.engine.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * Buffers are basically {@link List}s with a position and marker.
 * <p>
 * Note that they're designed to emulate {@linkplain java.nio.Buffer NIO buffers}, and that they can be iterated through.
 * 
 * @author Elusivehawk
 */
public class Buffer<T> implements IDirty, Collection<T>, Iterator<T>
{
	protected final List<T> l;
	protected final List<Boolean> dirt;
	protected int pos = 0, mark = 0;
	
	public Buffer()
	{
		this(16);
		
	}
	
	public Buffer(int limit)
	{
		this(new SimpleList<T>(limit));
		
	}
	
	@SafeVarargs
	public Buffer(T... array)
	{
		this(Arrays.asList(array));
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Buffer(List<T> list)
	{
		l = list;
		dirt = new SimpleList<Boolean>(16);
		
	}
	
	public Buffer(IStorable<T> stor)
	{
		this();
		
		stor.store(this);
		
		rewind();
		
	}
	
	@Override
	public boolean hasNext()
	{
		return this.remaining() > 0;
	}
	
	@Override
	public T next()
	{
		return this.next(true);
	}
	
	public T next(boolean next)
	{
		return this.hasNext() ? (this.l.get(next ? this.pos++ : this.pos + 1)) : null;
	}
	
	public T get()
	{
		return this.get(this.pos);
	}
	
	public T get(int pos)
	{
		return this.l.size() > pos && pos >= 0 ? this.l.get(pos) : null;
	}
	
	@Override
	public void remove()
	{
		this.dirt.remove(this.pos);
		this.l.remove(this.pos);
		
	}
	
	@Override
	public boolean add(T obj)
	{
		this.dirt.add(this.pos, true);
		this.l.add(this.pos++, obj);
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public Buffer<T> add(T... objs)
	{
		for (T obj : objs)
		{
			this.add(obj);
			
		}
		
		return this;
	}
	
	@Override
	public boolean addAll(Collection<? extends T> col)
	{
		for (T obj : col)
		{
			this.add(obj);
			
		}
		
		return true;
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
	
	public int remaining()
	{
		return this.l.size() - this.pos;
	}
	
	public void rewind()
	{
		this.pos = this.mark;
		
	}
	
	@Override
	public int size()
	{
		return this.l.size();
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
	
	@Override
	public Iterator<T> iterator()
	{
		return this;
	}
	
	@Override
	public void clear()
	{
		while (this.hasNext())
		{
			this.remove();
			
		}
		
	}
	
	@Override
	public boolean contains(Object arg0)
	{
		return this.l.contains(arg0);
	}
	
	@Override
	public boolean containsAll(Collection<?> arg0)
	{
		return this.l.containsAll(arg0);
	}
	
	@Override
	public boolean isEmpty()
	{
		return !this.hasNext();
	}
	
	@Override
	public boolean remove(Object arg0)
	{
		return this.l.remove(arg0);
	}
	
	@Override
	public boolean removeAll(Collection<?> arg0)
	{
		return this.l.removeAll(arg0);
	}
	
	@Override
	public boolean retainAll(Collection<?> arg0)
	{
		return this.l.retainAll(arg0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T[] toArray()
	{
		return (T[])this.l.toArray();
	}
	
	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] arg0)
	{
		return this.l.toArray(arg0);
	}
	
}
