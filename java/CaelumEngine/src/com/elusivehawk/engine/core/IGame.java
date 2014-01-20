
package com.elusivehawk.engine.core;

import com.elusivehawk.engine.physics.IPhysicsScene;
import com.elusivehawk.engine.render.IRenderHUB;
import com.elusivehawk.engine.util.Buffer;
import com.elusivehawk.engine.util.IUpdatable;

/**
 * 
 * The primary interface for your game.
 * 
 * @author Elusivehawk
 */
public interface IGame extends IUpdatable
{
	public boolean initiate(Buffer<String> args);
	
	public int getUpdateCount();
	
	/**
	 * 
	 * Safety function to prevent malicious thingummywhats from shutting down the game early.
	 * 
	 * @return True to enable the game threads to shut down.
	 */
	public boolean onGameShutdown();
	
	/**
	 * 
	 * Called during startup.
	 * 
	 * @return The rendering HUB to be used to render the game.
	 */
	public IRenderHUB getRenderHUB();
	
	/**
	 * 
	 * Called during startup.
	 * 
	 * @return The physics scene to use during the game's lifespan.
	 */
	public IPhysicsScene getPhysicsScene();
	
}
