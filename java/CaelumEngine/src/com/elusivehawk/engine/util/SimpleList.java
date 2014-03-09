
package com.elusivehawk.engine.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class SimpleList<T> implements List<T>
{
	protected Object[] list;
	protected int size = 0;
	protected final boolean exp;
	
	public SimpleList()
	{
		this(16);
		
	}
	
	public SimpleList(int length)
	{
		this(length, true);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public SimpleList(int length, boolean grow)
	{
		list = new Object[length];
		exp = grow;
		
	}
	
	@Override
	public boolean add(T obj)
	{
		int index = this.indexOf(null);
		
		if (index == -1)
		{
			if (!this.exp)
			{
				return false;
			}
			
			this.list = expand(this.list, 1);
			this.list[this.list.length - 1] = obj;
			
		}
		else
		{
			this.list[index] = obj;
			
		}
		
		this.size++;
		
		return true;
	}
	
	@Override
	public void add(int i, T obj)
	{
		this.list[i] = obj;
		
	}
	
	@Override
	public boolean addAll(Collection<? extends T> collection)
	{
		return this.addAll(collection.size(), collection);
	}
	
	@Override
	public boolean addAll(int count, Collection<? extends T> collection)
	{
		if (count + collection.size() > this.list.length && !this.exp)
		{
			return false;
		}
		
		int index = 0, c = 0;
		Iterator<? extends T> itr = collection.iterator();
		
		while (itr.hasNext() && c < count)
		{
			index = this.indexOf(null);
			
			if (index == -1)
			{
				this.list = expand(this.list, count - c);
				index = this.indexOf(null);
				
			}
			
			this.add(index, itr.next());
			
			c++;
			
		}
		
		return true;
	}
	
	@Override
	public void clear()
	{
		for (int c = 0; c < this.list.length; c++)
		{
			this.list[c] = null;
			
		}
		
		this.size = 0;
		
	}
	
	@Override
	public boolean contains(Object obj)
	{
		return this.indexOf(obj) != -1;
	}
	
	@Override
	public boolean containsAll(Collection<?> collection)
	{
		for (Object obj : collection)
		{
			if (!this.contains(obj))
			{
				return false;
			}
			
		}
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T get(int i)
	{
		return (T)this.list[i];
	}
	
	@Override
	public int indexOf(Object obj)
	{
		for (int c = 0; c < this.list.length; c++)
		{
			if (this.list[c] == obj)
			{
				return c;
			}
			
		}
		
		return -1;
	}
	
	@Override
	public boolean isEmpty()
	{
		return this.size > 0;
	}
	
	@Override
	public Iterator<T> iterator()
	{
		return new Buffer<T>(this);
	}
	
	@Override
	public int lastIndexOf(Object obj)
	{
		int ret = -1;
		
		for (int c = 0; c < this.list.length; c++)
		{
			if (this.get(c) == obj)
			{
				ret = c;
				
			}
			
		}
		
		return ret;
	}
	
	@Override
	public ListIterator<T> listIterator()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ListIterator<T> listIterator(int arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean remove(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		
		int index = this.indexOf(obj);
		
		if (index != -1)
		{
			this.list[index] = null;
			return true;
		}
		
		return false;
	}
	
	@Override
	public T remove(int i)
	{
		T ret = this.get(i);
		
		this.list[i] = null;
		
		return ret;
	}
	
	@Override
	public boolean removeAll(Collection<?> collection)
	{
		boolean ret = false;
		
		for (Object obj : collection)
		{
			if (this.remove(obj))
			{
				ret = true;
				
			}
			
		}
		
		return ret;
	}
	
	@Override
	public boolean retainAll(Collection<?> arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public T set(int i, T obj)
	{
		T ret = this.get(i);
		
		this.list[i] = obj;
		
		return ret;
	}
	
	@Override
	public int size()
	{
		return this.list.length;
	}
	
	@Override
	public List<T> subList(int offset, int length)
	{
		List<T> ret = new SimpleList<T>(length);
		
		for (int c = 0; c < length; c++)
		{
			ret.add(this.get(offset + c));
			
		}
		
		return ret;
	}
	
	@Override
	public Object[] toArray()
	{
		return this.list;
	}
	
	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	public static <T> SimpleList<T> createNewList()
	{
		return new SimpleList<T>();
	}
	
	protected static Object[] expand(Object[] arr, int length)
	{
		if (arr == null)
		{
			return new Object[length];
		}
		
		assert length > 0;
		
		Object[] ret = new Object[arr.length + length];
		
		for (int c = 0; c < arr.length; c++)
		{
			ret[c] = arr[c];
			
		}
		
		return ret;
	}
	
}
