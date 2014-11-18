
package com.elusivehawk.caelum.input;

import java.util.List;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.Display;
import com.elusivehawk.caelum.IGameEnvironment;
import com.elusivehawk.util.IUpdatable;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class InputManager implements IUpdatable
{
	private final List<EnumInputType> types = Lists.newArrayList();
	private final List<Input> input = Lists.newArrayList();
	private final List<IInputListener> listeners = Lists.newArrayList();
	
	private final Display display;
	
	private boolean initiated = false;
	
	@SuppressWarnings("unqualified-field-access")
	public InputManager(Display window)
	{
		display = window;
		
	}
	
	@Override
	public void update(double delta)
	{
		if (!this.initiated)
		{
			throw new CaelumException("Input manager was not initiated!");
		}
		
		this.input.forEach(((input) -> {input.update(delta);}));
		
	}
	
	public void initiateInput(IGameEnvironment ge)
	{
		if (this.types.isEmpty())
		{
			return;
		}
		
		for (EnumInputType type : this.types)
		{
			Input in = ge.loadInput(this.display, type);
			
			if (in == null)
			{
				continue;
			}
			
			if (in.getType() != type)
			{
				continue;
			}
			
			if (in.getParent() != this.display)
			{
				continue;
			}
			
			this.input.add(in);
			
		}
		
		for (Input in : this.input)
		{
			if (!in.initiateInput())
			{
				this.input.remove(in);
				
			}
			
		}
		
	}
	
	public void createInputType(EnumInputType type)
	{
		if (!this.types.contains(type))
		{
			this.types.add(type);
			
		}
		
	}
	
	public void addListener(IInputListener lis)
	{
		assert lis != null;
		
		this.listeners.add(lis);
		
	}
	
}
