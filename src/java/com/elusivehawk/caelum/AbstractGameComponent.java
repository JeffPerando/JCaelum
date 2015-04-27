
package com.elusivehawk.caelum;

import com.elusivehawk.caelum.window.IWindowListener;
import com.elusivehawk.util.IUpdatable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class AbstractGameComponent implements IDisposable, IWindowListener, IUpdatable
{
	protected final String name;
	
	@SuppressWarnings("unqualified-field-access")
	protected AbstractGameComponent(String title)
	{
		name = title;
		
	}
	
	@Override
	public String toString()
	{
		return this.name;
	}
	
	public abstract void initiate() throws Throwable;
	
}
