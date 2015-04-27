
package com.elusivehawk.caelum.input;

import com.elusivehawk.caelum.window.Window;
import com.elusivehawk.util.DelayedUpdater;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class DelayedInput extends Input
{
	private final DelayedUpdater updater;
	
	public DelayedInput(Window screen, IInputListener lis)
	{
		this(0.1, screen, lis);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public DelayedInput(double delay, Window screen, IInputListener lis)
	{
		super(screen, lis);
		
		updater = new DelayedUpdater(delay, this::updateInput);
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		this.updater.update(delta);
		
	}
	
}
