
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
	protected final List<IInputListener> listeners = new SimpleList<IInputListener>();
	
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
	
	public abstract void setFlag(IInputConst name, boolean value);
	
	public String getName()
	{
		return this.getClass().getSimpleName();
	}
	
	public boolean getBool(IInputConst name)
	{
		return this.getBool(name, 0);
	}
	
	public boolean getBool(IInputConst name, int extra)
	{
		if (name.getInputType() != this.inputType)
		{
			return false;
		}
		
		Boolean ret = this.bools.get(name.getValue() + extra);
		
		return ret == null ? false : ret;
	}
	
	public float getFloat(IInputConst name)
	{
		return this.getFloat(name, 0);
	}
	
	public float getFloat(IInputConst name, int extra)
	{
		if (name.getInputType() != this.inputType)
		{
			return 0f;
		}
		
		Float ret = this.floats.get(name.getValue() + extra);
		
		return ret == null ? 0f : ret;
	}
	
	public int getInt(IInputConst name)
	{
		return this.getInt(name, 0);
	}
	
	public int getInt(IInputConst name, int extra)
	{
		if (name.getInputType() != this.inputType)
		{
			return 0;
		}
		
		Integer ret = this.integers.get(name.getValue() + extra);
		
		return ret == null ? 0 : ret;
	}
	
}
