
package com.elusivehawk.caelum.input;

import java.io.Closeable;
import java.util.List;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.window.Window;
import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.ReflectionHelper;
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
	private final IInputListener listener;
	
	private final List<Class<? extends Input>> inputClasses = Lists.newArrayList();
	private final List<Input> input = Lists.newArrayList();
	
	private boolean initiated = false;
	
	@SuppressWarnings("unqualified-field-access")
	public InputManager(Window win, IInputListener lis)
	{
		assert win != null;
		assert lis != null;
		
		window = win;
		listener = lis;
		
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
	
	public void initiate()
	{
		if (this.initiated)
		{
			throw new CaelumException("Already initiated!");
		}
		
		if (!this.inputClasses.isEmpty())
		{
			this.inputClasses.forEach(((clazz) ->
			{
				this.input.add((Input)ReflectionHelper.newInstance(clazz, this.window, this.listener));
				
			}));
			
		}
		
		this.initiated = true;
		
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
	
	public void addInput(Class<? extends Input> input)
	{
		if (this.initiated)
		{
			throw new CaelumException("Cannot add input to initiated input manager!");
		}
		
		assert input != null;
		
		if (this.inputClasses.contains(input))
		{
			throw new CaelumException("Duplicate input class!");
		}
		
		this.inputClasses.add(input);
		
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
