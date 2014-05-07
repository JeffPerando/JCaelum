/*
 * Java port of Bullet (c) 2008 Martin Dvorak <jezek2@advel.cz>
 *
 * Bullet Continuous Collision Detection and Physics Library
 * Copyright (c) 2003-2008 Erwin Coumans  http://www.bulletphysics.com/
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from
 * the use of this software.
 * 
 * Permission is granted to anyone to use this software for any purpose, 
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 * 
 * 1. The origin of this software must not be misrepresented; you must not
 *    claim that you wrote the original software. If you use this software
 *    in a product, an acknowledgment in the product documentation would be
 *    appreciated but is not required.
 * 2. Altered source versions must be plainly marked as such, and must not be
 *    misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 */

package com.elusivehawk.engine.physics.jbullet.util;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.AbstractList;
import java.util.RandomAccess;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 *
 * @author jezek2
 */
public final class ObjectArrayList<T> extends AbstractList<T> implements RandomAccess, Externalizable
{
	private T[] array;
	private int size;
	
	public ObjectArrayList()
	{
		this(16);
		
	}
	
	@SuppressWarnings("unchecked")
	public ObjectArrayList(int initialCapacity)
	{
		this.array = (T[])new Object[initialCapacity];
		
	}
	
	@Override
	public boolean add(T value)
	{
		if (this.size == this.array.length)
		{
			expand();
			
		}
		
		this.array[this.size++] = value;
		
		return true;
	}
	
	@Override
	public void add(int index, T value)
	{
		if (this.size == this.array.length)
		{
			expand();
		}
		
		int num = this.size - index;
		
		if (num > 0)
		{
			System.arraycopy(this.array, index, this.array, index + 1, num);
			
		}
		
		this.array[index] = value;
		this.size++;
		
	}
	
	@Override
	public T remove(int index)
	{
		if (index < 0 || index >= this.size)
			throw new IndexOutOfBoundsException();
		
		T prev = this.array[index];
		System.arraycopy(this.array, index + 1, this.array, index, this.size - index - 1);
		this.array[this.size - 1] = null;
		this.size--;
		return prev;
	}
	
	@SuppressWarnings("unchecked")
	private void expand()
	{
		T[] newArray = (T[])new Object[this.array.length << 1];
		System.arraycopy(this.array, 0, newArray, 0, this.array.length);
		this.array = newArray;
		
	}
	
	public void removeQuick(int index)
	{
		System.arraycopy(this.array, index + 1, this.array, index, this.size - index - 1);
		
		this.array[this.size - 1] = null;
		this.size--;
		
	}
	
	@Override
	public T get(int index)
	{
		if (index >= this.size)
			throw new IndexOutOfBoundsException();
		
		return this.array[index];
	}
	
	public T getQuick(int index)
	{
		return this.array[index];
	}
	
	@Override
	public T set(int index, T value)
	{
		if (index >= this.size)
			throw new IndexOutOfBoundsException();
		
		T old = this.array[index];
		this.array[index] = value;
		return old;
	}
	
	public void setQuick(int index, T value)
	{
		this.array[index] = value;
		
	}
	
	@Override
	public int size()
	{
		return this.size;
	}
	
	public int capacity()
	{
		return this.array.length;
	}
	
	@Override
	public void clear()
	{
		this.size = 0;
	}
	
	@Override
	public int indexOf(Object o)
	{
		int _size = this.size;
		T[] _array = this.array;
		
		for (int i = 0; i < _size; i++)
		{
			if (o == null ? _array[i] == null : o.equals(_array[i]))
			{
				return i;
			}
			
		}
		return -1;
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException
	{
		out.writeInt(this.size);
		
		for (int i = 0; i < this.size; i++)
		{
			out.writeObject(this.array[i]);
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
	{
		this.size = in.readInt();
		int cap = 16;
		
		while (cap < this.size) cap <<= 1;
		
		this.array = (T[])new Object[cap];
		
		for (int i = 0; i < this.size; i++)
		{
			this.array[i] = (T)in.readObject();
		}
		
	}
	
}
