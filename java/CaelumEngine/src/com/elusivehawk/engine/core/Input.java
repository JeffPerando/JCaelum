
package com.elusivehawk.engine.core;

import com.google.common.eventbus.EventBus;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Input
{
	protected final EnumInputType inputType;
	protected final EventBus bus = new EventBus(String.format("input-%s", this.getName()));
	
	@SuppressWarnings("unqualified-field-access")
	public Input(EnumInputType type)
	{
		inputType = type;
		
	}
	
	public final EnumInputType getType()
	{
		return this.inputType;
	}
	
	public final void registerListener(Object obj)
	{
		this.bus.register(obj);
		
	}
	
	public String getName()
	{
		return this.getClass().getSimpleName();
	}
	
	public abstract boolean initiateInput();
	
	protected abstract void updateInput();
	
	public abstract void cleanup();
	
	public abstract void setFlag(String name, boolean value);
	
}
