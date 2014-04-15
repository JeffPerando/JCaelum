
package com.elusivehawk.engine.meta;

import com.elusivehawk.engine.core.Game;
import com.elusivehawk.engine.core.GameArguments;
import com.elusivehawk.engine.util.Version;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ExampleGame extends Game
{
	protected final Version v = new Version(0, 0, 1);
	
	public ExampleGame()
	{
		super("Example Game");
		
	}
	
	@Override
	public Version getGameVersion()
	{
		return this.v;
	}
	
	@Override
	protected boolean gameInit(GameArguments args)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void gameTick(double delta)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onGameShutdown()
	{
		// TODO Auto-generated method stub
		
	}
	
}
