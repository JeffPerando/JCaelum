
package com.elusivehawk.engine.meta.tutorial;

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
	public ExampleGame()
	{
		super("Example Game");
		
	}
	
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.core.Game#getGameVersion()
	 */
	@Override
	public Version getGameVersion()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.core.Game#initiate(com.elusivehawk.engine.core.GameArguments)
	 */
	@Override
	protected boolean initiate(GameArguments args)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.core.Game#tick(double)
	 */
	@Override
	protected void tick(double delta)
	{
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.core.Game#onGameShutdown()
	 */
	@Override
	protected void onGameShutdown()
	{
		// TODO Auto-generated method stub
		
	}
	
}
