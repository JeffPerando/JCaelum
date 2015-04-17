
package com.elusivehawk.caelum.input;

import java.io.Closeable;
import java.util.List;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.render.Window;
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
	private final Window window;
	
	private final List<Input> input = Lists.newArrayList();
	
	private boolean initiated = false;
	
	@SuppressWarnings("unqualified-field-access")
	public InputManager(Window win)
	{
		window = win;
		
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
		
		if (!this.window.isInitiated())
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
	
	public void addInput(Input input)
	{
		assert input != null;
		assert input.getWindow() == this.window;
		
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
