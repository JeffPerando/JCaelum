
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
	protected final InputManager manager;
	
	private boolean initiated = false;
	
	@SuppressWarnings("unqualified-field-access")
	public Input(InputManager mgr)
	{
		assert mgr != null;
		
		manager = mgr;
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		if (!this.initiated)
		{
			throw new CaelumException("Input not initiated!");
		}
		
		this.pollInput(this.manager.getDisplay());
		
	}
	
	public String getName()
	{
		return this.getClass().getSimpleName();
	}
	
	public boolean initiateInput()
	{
		this.initiated = true;
		
		return true;
	}
	
	public abstract EnumInputType getType();
	
	protected abstract void pollInput(Display display);
	
}
