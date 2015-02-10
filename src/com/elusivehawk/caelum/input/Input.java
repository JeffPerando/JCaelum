
package com.elusivehawk.caelum.input;

import java.io.Closeable;
import java.io.IOException;
import com.elusivehawk.caelum.Display;
import com.elusivehawk.util.Dirtable;
import com.elusivehawk.util.IUpdatable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Input extends Dirtable implements IUpdatable, Closeable
{
	private final Display display;
	private final IInputImpl impl;
	
	@SuppressWarnings("unqualified-field-access")
	public Input(Display screen, IInputImpl implementation)
	{
		display = screen;
		impl = implementation;
		
	}
	
	@Override
	public void close() throws IOException
	{
		if (this.impl != null)
		{
			this.impl.close();
			
		}
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		this.updateInput(delta);
		
	}
	
	public Display getDisplay()
	{
		return this.display;
	}
	
	protected void updateImpl(double delta) throws Throwable
	{
		if (this.impl != null)
		{
			this.impl.updateInput(delta, this);
			
		}
		
	}
	
	public abstract void updateInput(double delta) throws Throwable;
	
}
