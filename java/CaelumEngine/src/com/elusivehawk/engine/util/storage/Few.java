
package com.elusivehawk.engine.util.storage;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Few<T> extends TriTuple<T, T, T>
{
	public Few(T first, T second, T third)
	{
		super(first, second, third);
		
	}
	
	@Override
	public Few<T> clone()
	{
		return new Few<T>(this.one, this.two, this.three);
	}
	
	public static <T> Few<T> createFew(T one, T two, T three)
	{
		return new Few<T>(one, two, three);
	}
	
}
