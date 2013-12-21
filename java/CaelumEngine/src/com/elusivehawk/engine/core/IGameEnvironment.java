
package com.elusivehawk.engine.core;

/**
 * 
 * Interface for library-specific implementations, such as:<br>
 * <ul>
 * <li>2D rendering implementation</li>
 * <li>Input implementation (Mouse, keyboard, console controller, etc.)</li>
 * <li>Logging</li>
 * <li>Display implementation</li>
 * </ul>
 * <p>
 * NOTICE: The game environment in question mustn't be in the same library as the game itself.
 * 
 * @author Elusivehawk
 */
public interface IGameEnvironment
{
	public void initiate();
	
	public ILog getLog();
	
}
