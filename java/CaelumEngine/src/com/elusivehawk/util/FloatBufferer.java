
package com.elusivehawk.util;

import java.nio.FloatBuffer;
import java.util.List;
import com.elusivehawk.util.storage.Pair;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class FloatBufferer
{
	protected final FloatBuffer backing;
	protected final List<Pair<Integer>> diff = Lists.newArrayList();
	
	@SuppressWarnings("unqualified-field-access")
	public FloatBufferer(int size)
	{
		assert size > 0;
		
		backing = BufferHelper.createFloatBuffer(size);
		
	}
	
	public List<Pair<Integer>> getDiffs()
	{
		return this.diff;
	}
	
	public void sync(FloatBuffer fb)
	{
		this.diff.clear();
		
		int in = -1, count = 0;
		int cap = Math.min(fb.capacity(), this.backing.capacity());
		
		for (int c = 0; c < cap; c++)
		{
			if (fb.get(c) != this.backing.get(c))
			{
				if (in == -1)
				{
					in = c;
					
				}
				
				count++;
				
				this.backing.put(c, fb.get(c));
				
				continue;
			}
			
			this.diff.add(Pair.createPair(in, count));
			
			in = -1;
			count = 0;
			
		}
		
		if (in != -1)
		{
			this.diff.add(Pair.createPair(in, count));
			
		}
		
	}
	
}
