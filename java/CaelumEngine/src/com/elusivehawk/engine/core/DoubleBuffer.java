
package com.elusivehawk.engine.core;

import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class DoubleBuffer<T> extends Buffer<T>
{
	protected final List<T> list;
	
	@SuppressWarnings("unqualified-field-access")
	public DoubleBuffer(List<T> l)
	{
		super(new SyncList<T>());
		list = l;
		
	}
	
	@Override
	public void norm()
	{
		for (int c = 0; c < this.size(); c++)
		{
			if (this.isDirty(c))
			{
				this.list.add(this.get(c));
				
			}
			
		}
		
		super.norm();
		
	}
	
}
