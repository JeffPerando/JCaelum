
package com.elusivehawk.engine;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IGameStateListener
{
	public void onGameStateSwitch(GameState gs);
	
}
