
package com.elusivehawk.caelum.input;

import com.elusivehawk.caelum.render.Window;
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
	
	public DelayedInput(Window screen)
	{
		this(0.1, screen);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public DelayedInput(double delay, Window screen)
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
