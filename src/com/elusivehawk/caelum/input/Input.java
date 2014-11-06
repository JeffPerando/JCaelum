
package com.elusivehawk.caelum.input;

import java.io.Closeable;
import java.util.List;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.Logger;
import com.google.common.collect.Lists;


/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Input implements IUpdatable, Closeable
{
	private final List<IInputListener> listeners = Lists.newArrayList();
	private boolean initiated = false, updatingInput = false;
	
	@Override
	public void update(double delta) throws Throwable
	{
		if (!this.initiated)
		{
			throw new CaelumException("Input \"%s\" has yet to be initiated.", this.getName());
		}
		
		this.updatingInput = true;
		this.pollInput(delta);
		this.updatingInput = false;
		
		this.postUpdate();
		
	}
	
	public String getName()
	{
		return this.getClass().getSimpleName();
	}
	
	public void addListener(IInputListener lis)
	{
		this.listeners.add(lis);
		
	}
	
	public boolean initiateInput()
	{
		this.initiated = true;
		
		return true;
	}
	
	protected void sendUpdateToListeners(double delta)
	{
		if (!this.updatingInput)
		{
			throw new CaelumException("Not updating input!");
		}
		
		this.listeners.forEach(((lis) ->
		{
			try
			{
				lis.onInputReceived(this, delta);
				
			}
			catch (Throwable e)
			{
				Logger.log().err("[%s] Error caught:", e, this.getName());
				
			}
			
		}));
		
	}
	
	protected abstract void pollInput(double delta);
	
	protected abstract void postUpdate();
	
}
