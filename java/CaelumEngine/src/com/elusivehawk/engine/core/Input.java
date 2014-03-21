
package com.elusivehawk.engine.core;

import java.util.List;
import java.util.Map;
import com.elusivehawk.engine.util.SimpleList;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Input
{
	protected final EnumInputType inputType;
	protected final List<IInputListener> listeners = SimpleList.newList();
	
	protected final Map<Integer, Boolean> bools = Maps.newHashMap();
	protected final Map<Integer, Float> floats = Maps.newHashMap();
	protected final Map<Integer, Integer> integers = Maps.newHashMap();
	
	@SuppressWarnings("unqualified-field-access")
	public Input(EnumInputType type)
	{
		inputType = type;
		
	}
	
	public final EnumInputType getType()
	{
		return this.inputType;
	}
	
	public final void update()
	{
		this.updateInput();
		
		if (!this.listeners.isEmpty())
		{
			for (IInputListener listener : this.listeners)
			{
				listener.onInputUpdated(this);
				
			}
			
		}
		
	}
	
	public abstract boolean initiateInput();
	
	protected abstract void updateInput();
	
	public abstract void cleanup();
	
	public abstract void setFlag(int name, boolean value);
	
	public String getName()
	{
		return this.getClass().getSimpleName();
	}
	
	public boolean getBool(int name)
	{
		Boolean ret = this.bools.get(name);
		
		return ret == null ? false : ret;
	}
	
	public float getFloat(int name)
	{
		Float ret = this.floats.get(name);
		
		return ret == null ? 0f : ret;
	}
	
	public int getInt(int name)
	{
		Integer ret = this.integers.get(name);
		
		return ret == null ? 0 : ret;
	}
	
}
