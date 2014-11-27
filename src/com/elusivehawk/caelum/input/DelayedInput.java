
package com.elusivehawk.caelum.input;

import com.elusivehawk.util.DelayedUpdater;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class DelayedInput extends Input
{
	private final DelayedUpdater updater = new DelayedUpdater(0.1, ((delta) -> {super.update(delta);}));
	
	public DelayedInput(InputManager mgr)
	{
		super(mgr);
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		this.updater.update(delta);
		
	}
	
}
