
package com.elusivehawk.caelum;

import com.elusivehawk.caelum.input.IInputListener;
import com.elusivehawk.caelum.physics.IPhysicsSimulator;
import com.elusivehawk.caelum.prefab.GameState;
import com.elusivehawk.caelum.render.IRenderable;
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
public abstract class AbstractGameComponent implements IInputListener, IUpdatable, IRenderable
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
	
	/**
	 * 
	 * Called during startup.
	 * 
	 * @return The physics simulator to use during the game's lifespan.
	 */
	public abstract IPhysicsSimulator getPhysicsSimulator();
	
	public abstract void initiate() throws Throwable;
	
	public abstract void onShutdown();
	
}
