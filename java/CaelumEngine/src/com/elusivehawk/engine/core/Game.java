
package com.elusivehawk.engine.core;

import com.elusivehawk.engine.physics.IPhysicsScene;
import com.elusivehawk.engine.render.IRenderHUB;
import com.elusivehawk.engine.util.Buffer;
import com.elusivehawk.engine.util.IUpdatable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@SuppressWarnings({"static-method", "unused"})
public abstract class Game implements IUpdatable
{
	public boolean initiate(Buffer<String> args, AssetManager mgr)
	{
		return true;
	}
	
	public int getUpdateCount()
	{
		return 30;
	}
	
	/**
	 * 
	 * Safety function to prevent malicious thingummywhats from shutting down the game early.
	 * 
	 * @return True to enable the game threads to shut down.
	 */
	public boolean canGameShutDown()
	{
		return true;
	}
	
	/**
	 * 
	 * Called during startup.
	 * 
	 * @return The rendering HUB to be used to render the game.
	 */
	public abstract IRenderHUB getRenderHUB();
	
	/**
	 * 
	 * Called during startup.
	 * 
	 * @return The physics scene to use during the game's lifespan.
	 */
	public abstract IPhysicsScene getPhysicsScene();
	
}
