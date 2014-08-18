
package com.elusivehawk.engine;

import com.elusivehawk.util.Internal;
import com.elusivehawk.util.Version;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Internal
public final class TestGame extends Game
{
	public static final Version VERSION = new Version(1, 0, 0);
	
	public TestGame()
	{
		super("Example Game");
		
	}
	
	@Override
	public Version getGameVersion()
	{
		return VERSION;
	}
	
	@Override
	protected void initiateGame(GameArguments args)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void tick(double delta) throws Throwable
	{
		CaelumEngine.log().log(EnumLogType.INFO, "Test: %s", delta);
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onGameShutdown()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getUpdateCount()
	{
		return 40;
	}
	
}
