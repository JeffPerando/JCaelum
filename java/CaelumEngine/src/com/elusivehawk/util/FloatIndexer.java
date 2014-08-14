
package com.elusivehawk.util;

import java.util.List;
import com.elusivehawk.util.math.Vector;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class FloatIndexer
{
	private List<Vector> vecs = Lists.newArrayList();
	private List<Integer> indices = Lists.newArrayList();
	private final int fpi;
	
	@SuppressWarnings("unqualified-field-access")
	public FloatIndexer(int floatsPerIndex)
	{
		fpi = floatsPerIndex;
		
	}
	
	public void add(float... fs)
	{
		if (fs.length < this.fpi)
		{
			throw new ArrayIndexOutOfBoundsException();
		}
		
		int ind = -1;
		
		if (!this.vecs.isEmpty())
		{
			floatcounter: for (int c = 0; c < this.vecs.size(); c++)
			{
				Vector vec = this.vecs.get(c);
				
				for (int i = 0; i < this.fpi; i++)
				{
					if (vec.get(i) != fs[i])
					{
						continue floatcounter;
					}
					
				}
				
				ind = c;
				
				break;
			}
			
		}
		
		if (ind == -1)
		{
			ind = this.vecs.size();
			
			this.vecs.add(new Vector(this.fpi, fs));
			
		}
		
		this.indices.add(ind);
		
	}
	
}
