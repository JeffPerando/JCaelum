
package com.elusivehawk.caelum.input;

import java.io.Closeable;
import com.elusivehawk.caelum.window.Window;
import com.elusivehawk.util.Dirtable;
import com.elusivehawk.util.IUpdatable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Input extends Dirtable implements IUpdatable, Closeable
{
	protected final Window window;
	
	private final IInputListener listener;
	
	private boolean initiated = false;
	
	@SuppressWarnings("unqualified-field-access")
	public Input(Window screen, IInputListener lis)
	{
		assert screen != null;
		assert lis != null;
		
		window = screen;
		listener = lis;
		
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
	
	public void triggerHooks(double delta)
	{
		this.listener.onInputReceived(this, delta);
		
	}
	
	public Window getWindow()
	{
		return this.window;
	}
	
	public abstract void updateInput(double delta) throws Throwable;
	
}
