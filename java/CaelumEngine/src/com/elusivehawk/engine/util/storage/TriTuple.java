
package com.elusivehawk.engine.util.storage;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TriTuple<O, T, TH> extends Tuple<O, T>
{
	public TH three;
	
	@SuppressWarnings("unqualified-field-access")
	public TriTuple(O first, T second, TH third)
	{
		super(first, second);
		three = third;
		
	}
	
	public static <O, T, TH> TriTuple<O, T, TH> create(O one, T two, TH three)
	{
		return new TriTuple<O, T, TH>(one, two, three);
	}
	
}
