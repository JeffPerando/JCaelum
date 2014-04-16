
package com.elusivehawk.engine.util.storage;

/**
 * 
 * Contains two objects, and that's IT.
 * 
 * @author Elusivehawk
 */
public class Tuple<O, T>
{
	public O one;
	public T two;
	
	@SuppressWarnings("unqualified-field-access")
	public Tuple(O first, T second)
	{
		one = first;
		two = second;
		
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Tuple))
		{
			return false;
		}
		
		Tuple t = (Tuple)obj;
		
		if (!t.one.equals(this.one) || !t.two.equals(this.one))
		{
			return false;
		}
		
		if (!t.one.equals(this.two) || !t.two.equals(this.two))
		{
			return false;
		}
		
		return true;
	}
	
	@Override
	public Tuple<O, T> clone()
	{
		return new Tuple<O, T>(this.one, this.two);
	}
	
	public static <O, T> Tuple<O, T> create(O one, T two)
	{
		return new Tuple<O, T>(one, two);
	}
	
}
