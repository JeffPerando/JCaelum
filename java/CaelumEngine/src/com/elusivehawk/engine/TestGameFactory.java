
package com.elusivehawk.engine;


/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TestGameFactory extends GameFactory
{
	@Override
	public Game createGame()
	{
		return new TestGame();
	}
	
}
