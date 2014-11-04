
package com.elusivehawk.caelum;

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
