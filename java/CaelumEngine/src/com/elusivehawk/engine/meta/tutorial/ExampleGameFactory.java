
package com.elusivehawk.engine.meta.tutorial;

import com.elusivehawk.engine.Game;
import com.elusivehawk.engine.GameFactory;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ExampleGameFactory extends GameFactory
{
	@Override
	public Game createGame()
	{
		return new ExampleGame();
	}
	
}
