
package com.elusivehawk.util.storage;

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
	
	@Override
	public Tuple<O, T> clone()
	{
		return new Tuple<O, T>(this.one, this.two);
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
		
		return t.one.equals(this.one) && t.two.equals(this.two);
	}
	
	@Override
	public int hashCode()
	{
		int ret = 31;
		
		ret *= (31 + this.one.hashCode());
		ret *= (31 + this.two.hashCode());
		
		return ret;
	}
	
	public static <O, T> Tuple<O, T> create(O one, T two)
	{
		return new Tuple<O, T>(one, two);
	}
	
}
