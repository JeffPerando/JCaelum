
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
	
	@SuppressWarnings("unqualified-field-access")
	public DelayedInput(double delay, Display screen)
	{
		super(screen);
		
		updater = new DelayedUpdater(delay, this::updateInput);
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		this.updater.update(delta);
		
	}
	
}
