
package elusivehawk.engine.math;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Vectorf implements IVector
{
	public abstract float[] array();
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Vectorf)) return false;
		
		Vectorf vec = (Vectorf)obj;
		float[] first = this.array();
		float[] sec = vec.array();
		
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
