
package com.elusivehawk.caelum.input;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import com.elusivehawk.caelum.Display;
import com.elusivehawk.caelum.IGameEnvironment;
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
	private final Display display;
	
	private final List<IInputListener> listeners = SyncList.newList();
	
	private boolean initiated = false;
	private IInputImpl impl = null;
	
	@SuppressWarnings("unqualified-field-access")
	public Input(Display screen)
	{
		display = screen;
		
	}
	
	@Override
	public void close() throws IOException
	{
		if (this.impl != null)
		{
			this.impl.close();
			
		}
		
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
	
	public void initiate(IGameEnvironment env)
	{
		assert !this.initiated;
		assert env != null;
		
		this.impl = env.createInputImpl(this.getClass());
		
		this.initiated = true;
		
	}
	
	protected void updateImpl(double delta) throws Throwable
	{
		if (this.impl != null)
		{
			this.impl.updateInput(delta, this);
			
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
	
	public Display getDisplay()
	{
		return this.display;
	}
	
	public abstract void updateInput(double delta) throws Throwable;
	
}
