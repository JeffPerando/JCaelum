
package com.elusivehawk.engine.core;

import com.elusivehawk.engine.render.IRenderHUB;

/**
 * 
 * The primary interface for your game.
 * 
 * @author Elusivehawk
 */
public interface IGame extends IThreadable
{
	/**
	 * 
	 * Safety function to prevent malicious thingummywhats from shutting down the game early.
	 * 
	 * @return True to enable the game threads to shut down.
	 */
	public boolean enableShutdown();
	
	/**
	 * 
	 * Called during startup.
	 * 
	 * @return The rendering HUB to be used to render the game.
	 */
	public IRenderHUB getRenderHUB();
	
}
