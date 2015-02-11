
package com.elusivehawk.caelum.input;

import java.io.Closeable;
import java.util.List;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.Display;
import com.elusivehawk.caelum.IGameEnvironment;
import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.Logger;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class InputManager implements IUpdatable, Closeable
{
	private final Display display;
	
	private final List<Input> input = Lists.newArrayList();
	
	private boolean initiated = false;
	
	@SuppressWarnings("unqualified-field-access")
	public InputManager(Display window)
	{
		display = window;
		
	}
	
	@Override
	public void close()
	{
		this.input.forEach(((input) ->
		{
			try
			{
				input.close();
				
			}
			catch (Throwable e)
			{
				Logger.err(e);
				
			}
			
		}));
		
		this.input.clear();
		
	}
	
	@Override
	public void update(double delta)
	{
		if (!this.initiated)
		{
			throw new CaelumException("Input manager was not initiated!");
		}
		
		if (!this.display.isInitiated())
		{
			return;
		}
		
		this.input.forEach(((input) ->
		{
			try
			{
				input.update(delta);
				
			}
			catch (Throwable e)
			{
				Logger.err(e);
				
			}
			
		}));
		
	}
	
	public void sendInputEvents(double delta)
	{
		this.input.forEach(((input) ->
		{
			if (input.isDirty())
			{
				input.triggerHooks(delta);
				
				input.setIsDirty(false);
				
			}
			
		}));
		
	}
	
	public void initiateInput(IGameEnvironment ge)
	{
		if (this.initiated)
		{
			return;
		}
		
		if (this.input.isEmpty())
		{
			return;
		}
		
		for (Input in : this.input)
		{
			in.initiate(ge);
			
		}
		
		this.initiated = true;
		
	}
	
	public void addInput(Input input)
	{
		assert input != null;
		assert input.getDisplay() == this.display;
		
		for (Input in : this.input)
		{
			if (in.getClass().isInstance(input))
			{
				throw new InputException("Duplicate input receptical of type %s", in.getClass());
			}
			
		}
		
		this.input.add(input);
		
	}
	
	public void addListener(Class<? extends Input> type, IInputListener lis)
	{
		assert type != null;
		assert lis != null;
		
		Input in = this.getInput(type);
		
		if (in != null)
		{
			in.addListener(lis);
			
		}
		
	}
	
	public Input getInput(Class<? extends Input> type)
	{
		assert this.initiated;
		
		for (Input in : this.input)
		{
			if (type.isInstance(in))
			{
				return in;
			}
			
		}
		
		return null;
	}
	
}
