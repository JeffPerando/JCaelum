
package elusivehawk.engine.math;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Vectori implements IVector
{
	public abstract int[] array();
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Vectori)) return false;
		
		Vectori vec = (Vectori)obj;
		int[] first = this.array();
		int[] sec = vec.array();
		
		if (first.length != sec.length) return false;
		
		for (int c = 0; c < sec.length; c++)
		{
			if (first[c] != sec[c])
			{
				return false;
			}
		}
		
		return true;
	}
	
}
