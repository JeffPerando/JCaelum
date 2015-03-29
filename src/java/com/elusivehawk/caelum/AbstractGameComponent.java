
package com.elusivehawk.caelum;

import com.elusivehawk.caelum.input.IInputListener;
import com.elusivehawk.caelum.prefab.GameState;
import com.elusivehawk.caelum.render.IRenderer;
import com.elusivehawk.util.IUpdatable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 * 
 * @see Game
 * @see GameArguments
 * @see GameState
 * @see IUpdatable
 */
public abstract class AbstractGameComponent implements IDisposable, IInputListener, IRenderer, IUpdatable
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
