
package com.elusivehawk.caelum.input;

import java.io.Closeable;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.Display;
import com.elusivehawk.caelum.IGameEnvironment;
import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.storage.SyncList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class InputManager implements IUpdatable, Closeable
{
	private final List<Class<? extends Input>> types = Lists.newArrayList();
	private final List<Input> input = Lists.newArrayList();
	private final Map<Class<? extends Input>, List<IInputListener>> listeners = Maps.newHashMap();
	
	private final Display display;
	
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
			catch (Exception e)
			{
				Logger.err(e);
				
			}
			
		}));
		
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
				this.updateListeners(input, delta);
				
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
		
		if (this.types.isEmpty())
		{
			return;
		}
		
		for (Class<? extends Input> type : this.types)
		{
			IInputImpl impl = ge.createInputImpl(type);
			
			if (impl == null)
			{
				Logger.warn("Could not create implementation for type %s", type.getSimpleName());
				
			}
			
			Input in = null;
			
			try
			{
				Constructor<? extends Input> con = type.getConstructor(Display.class, IInputImpl.class);
				
				if (con == null)
				{
					if (impl != null)
					{
						throw new CaelumException("Cannot find constructor for input type \"%s\": Has implementation, but does not have a constructor that accepts it", type.getSimpleName());
					}
					
					con = type.getConstructor(Display.class);
					
					if (con == null)
					{
						throw new CaelumException("Cannot find constructor for input type \"%s\"", type.getSimpleName());
					}
					
					in = con.newInstance(this.display);
					
				}
				else
				{
					in = con.newInstance(this.display, impl);
					
				}
				
			}
			catch (Throwable e)
			{
				Logger.err(e);
				
			}
			
			this.input.add(in);
			
		}
		
		this.initiated = true;
		
	}
	
	public void createInputType(Class<? extends Input> type)
	{
		if (!this.types.contains(type))
		{
			this.types.add(type);
			
		}
		
	}
	
	public void addListener(Class<? extends Input> type, IInputListener lis)
	{
		assert type != null;
		assert lis != null;
		
		List<IInputListener> inList = this.listeners.get(type);
		
		if (inList == null)
		{
			inList = SyncList.newList();
			
			this.listeners.put(type, inList);
			
		}
		
		inList.add(lis);
		
	}
	
	private void updateListeners(Input input, double delta)
	{
		List<IInputListener> list = this.listeners.get(input.getClass());
		
		if (list != null)
		{
			list.forEach(((lis) -> {lis.onInputReceived(input, delta);}));
			
		}
		
	}
	
	public Display getDisplay()
	{
		return this.display;
	}
	
}
