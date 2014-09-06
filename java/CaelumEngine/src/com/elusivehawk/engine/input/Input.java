
package com.elusivehawk.engine.input;

import java.io.Closeable;
import java.util.List;
import com.elusivehawk.engine.CaelumException;
import com.elusivehawk.util.IUpdatable;
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
	private boolean initiated = false;
	
	@Override
	public void update(double delta)
	{
		if (!this.initiated)
		{
			throw new CaelumException("Input %s has yet to be initiated.", this.getClass().getSimpleName());
		}
		
		this.poll();
		
		this.listeners.forEach(((lis) -> {lis.onInputReceived(delta, this);}));
		
		this.postUpdate();
		
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
	
	protected abstract void poll();
	
	protected abstract void postUpdate();
	
}
