
package com.elusivehawk.caelum.input;

import java.io.Closeable;
import java.util.List;
import com.elusivehawk.caelum.render.Window;
import com.elusivehawk.util.Dirtable;
import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.storage.SyncList;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Input extends Dirtable implements IUpdatable, Closeable
{
	protected final Window window;
	
	private final List<IInputListener> listeners = SyncList.newList();
	
	private boolean initiated = false;
	
	@SuppressWarnings("unqualified-field-access")
	public Input(Window screen)
	{
		assert screen != null;
		
		window = screen;
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		if (!this.initiated)
		{
			throw new InputException("Input not initiated: %s", this);
		}
		
		try
		{
			this.updateInput(delta);
			
		}
		catch (Throwable e)
		{
			throw new InputException(e);
		}
		
	}
	
	public void addListener(IInputListener lis)
	{
		assert lis != null;
		
		this.listeners.add(lis);
		
	}
	
	public void triggerHooks(double delta)
	{
		this.listeners.forEach(((lis) ->
		{
			lis.onInputReceived(this, delta);
			
		}));
		
	}
	
	public Window getWindow()
	{
		return this.window;
	}
	
	public abstract void updateInput(double delta) throws Throwable;
	
}
