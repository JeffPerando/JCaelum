
package com.elusivehawk.caelum.input;

import com.elusivehawk.caelum.Display;
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
	
	public DelayedInput(Display screen)
	{
		this(0.1, screen);
		
	}
	
	public DelayedInput(Display screen, IInputImpl implementation)
	{
		this(0.1, screen, implementation);
		
	}
	
	public DelayedInput(double delay, Display screen)
	{
		this(delay, screen, null);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public DelayedInput(double delay, Display screen, IInputImpl implementation)
	{
		super(screen, implementation);
		
		updater = new DelayedUpdater(delay, this::updateInput);
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		this.updater.update(delta);
		
	}
	
}
