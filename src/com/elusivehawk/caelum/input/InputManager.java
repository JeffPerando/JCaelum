
package com.elusivehawk.caelum.input;

import java.io.Closeable;
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
public final class InputManager implements Closeable, IUpdatable
{
	private final List<EnumInputType> types = Lists.newArrayList();
	private final List<Input> input = Lists.newArrayList();
	private final Map<EnumInputType, List<IInputListener>> listeners = Maps.newHashMap();
	private final List<InputEvent> eventQueue = SyncList.newList();
	
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
	
	@Override
	public void close()
	{
		this.input.forEach(((in) ->
		{
			try
			{
				in.close();
				
			}
			catch (Exception e)
			{
				Logger.err(e);
				
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
		
		for (EnumInputType type : this.types)
		{
			Input in = ge.createInput(this, type);
			
			if (in == null)
			{
				continue;
			}
			
			if (in.getType() != type)
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
		
		this.initiated = true;
		
	}
	
	public void queueInputEvent(InputEvent event)
	{
		assert event != null;
		
		this.eventQueue.add(event);
		
	}
	
	public void sendInputEvents(double delta)
	{
		if (this.listeners.isEmpty() || this.eventQueue.isEmpty())
		{
			return;
		}
		
		for (int c = 0; c < this.eventQueue.size(); c++)
		{
			InputEvent event = this.eventQueue.get(c);
			
			List<IInputListener> inList = this.listeners.get(event.type);
			
			if (inList != null)
			{
				inList.forEach(((lis) -> {lis.onInputReceived(event, delta);}));
				
			}
			
			this.eventQueue.remove(c);
			
		}
		
	}
	
	public void createInputType(EnumInputType type)
	{
		if (!this.types.contains(type))
		{
			this.types.add(type);
			
		}
		
	}
	
	public void addListener(EnumInputType type, IInputListener lis)
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
	
	public Display getDisplay()
	{
		return this.display;
	}
	
}
