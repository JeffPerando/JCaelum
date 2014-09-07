
package com.elusivehawk.engine.prefab;

import com.elusivehawk.util.IPopulator;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Component extends ComponentGroup
{
	protected volatile int priority = 0;
	
	public Component(ComponentGroup parent)
	{
		super(parent);
		
	}
	
	public Component(ComponentGroup parent, IPopulator<Component> pop)
	{
		super(parent);
		
		pop.populate(this);
		
	}
	
	public int getPriority()
	{
		return this.priority;
	}
	
	public Component setPriority(int p)
	{
		assert p >= 0;
		
		this.priority = p;
		
		return this;
	}
	
}
