
package com.elusivehawk.engine;

import java.io.Closeable;
import java.util.List;
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
	
	@Override
	public void update(double delta)
	{
		this.poll();
		
		this.listeners.forEach((lis) ->
		{
			lis.onInputReceived(delta, this);
			
		});
		
		this.postUpdate();
		
	}
	
	public void addListener(IInputListener lis)
	{
		this.listeners.add(lis);
		
	}
	
	public abstract boolean initiateInput();
	
	protected abstract void poll();
	
	protected abstract void postUpdate();
	
}
