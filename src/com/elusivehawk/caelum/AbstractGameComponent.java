
package com.elusivehawk.caelum;

import com.elusivehawk.caelum.assets.AssetManager;
import com.elusivehawk.caelum.input.IInputListener;
import com.elusivehawk.caelum.physics.IPhysicsSimulator;
import com.elusivehawk.caelum.prefab.GameState;
import com.elusivehawk.caelum.render.IRenderable;
import com.elusivehawk.util.IUpdatable;

/**
 * 
 * An abstract class that is meant to unify some {@link Game} and {@link GameState} classes.
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
	private final AbstractGameComponent master;
	
	protected final String name;
	
	protected AbstractGameComponent(String title)
	{
		this(null, title);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	protected AbstractGameComponent(AbstractGameComponent owner, String title)
	{
		master = owner;
		name = title;
		
	}
	
	@Override
	public String toString()
	{
		if (this.master == null)
		{
			return this.getFormattedName();
		}
		
		return String.format("%s.%s", this.master.master == null ? this.master.name : this.master.toString(), this.name);
	}
	
	public String getFormattedName()
	{
		return this.name;
	}
	
	public void onScreenFlipped(boolean flip){}
	
	/**
	 * 
	 * Called during startup.
	 * 
	 * @return The physics simulator to use during the game's lifespan.
	 */
	public abstract IPhysicsSimulator getPhysicsSimulator();
	
	public abstract void initiate(GameArguments args, Display display, AssetManager assets) throws Throwable;
	
	public abstract void onShutdown();
	
}
