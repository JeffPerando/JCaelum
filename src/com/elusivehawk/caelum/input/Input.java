
package com.elusivehawk.caelum.input;

import java.io.Closeable;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.Display;
import com.elusivehawk.util.IUpdatable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Input implements IUpdatable, Closeable
{
	protected final Display display;
	
	private boolean initiated = false;
	
	@SuppressWarnings("unqualified-field-access")
	public Input(Display window)
	{
		assert window != null;
		
		display = window;
		
	}
	
	@Override
	public void update(double delta)
	{
		if (!this.initiated)
		{
			throw new CaelumException("Input not initiated!");
		}
		
		this.pollInput(delta);
		
	}
	
	public String getName()
	{
		return this.getClass().getSimpleName();
	}
	
	public Display getParent()
	{
		return this.display;
	}
	
	public boolean initiateInput()
	{
		this.initiated = true;
		
		return true;
	}
	
	public abstract EnumInputType getType();
	
	protected abstract void pollInput(double delta);
	
}
