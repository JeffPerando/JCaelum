
package com.elusivehawk.caelum.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Triangle
{
	public final int a, b, c;
	
	@SuppressWarnings("unqualified-field-access")
	public Triangle(int first, int second, int third)
	{
		a = first;
		b = second;
		c = third;
		
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Triangle))
		{
			return false;
		}
		
		Triangle t = (Triangle)obj;
		
		return this.a == t.a && this.b == t.b && this.c == t.c;
	}
	
	@Override
	public int hashCode()
	{
		int ret = 31;
		
		ret *= (31 + this.a);
		ret *= (31 + this.b);
		ret *= (31 + this.c);
		
		return ret;
	}
	
	public boolean canGoAheadOf(Triangle t)
	{
		//T:	  1 2 3
		//This:	1 2 3
		return t.a == this.b && t.b == this.c;
	}
	
	public boolean canGoBehind(Triangle t)
	{
		//T:	1 2 3
		//This:	  1 2 3
		return t.b == this.a && t.c == this.b;
	}
	
}
