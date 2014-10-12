
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
	void onGameStateSwitch(GameState gs);
	
}
