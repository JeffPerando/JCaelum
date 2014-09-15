
package com.elusivehawk.engine;

import com.elusivehawk.engine.input.Key;
import com.elusivehawk.engine.input.Keyboard;
import com.elusivehawk.engine.input.Mouse;
import com.elusivehawk.util.EnumLogType;
import com.elusivehawk.util.Internal;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.Version;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Internal
public final class ExampleGame extends Game
{
	public static final Version VERSION = new Version(1, 0, 0);
	
	public ExampleGame()
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
		CaelumEngine.addInputListener(Keyboard.class, ((in) ->
		{
			Keyboard kb = (Keyboard)in;
			
			for (Key key : kb.getPushedKeys())
			{
				Logger.log().log(EnumLogType.VERBOSE, "Key down: %s", key);
				
			}
			
			for (Key key : kb.getOldPushedKeys())
			{
				Logger.log().log(EnumLogType.VERBOSE, "Key up: %s", key);
				
			}
			
		}));
		
		CaelumEngine.addInputListener(Mouse.class, ((in) ->
		{
			Mouse m = (Mouse)in;
			
			for (int c = 0; c < m.getButtonCount(); c++)
			{
				Logger.log().log(EnumLogType.VERBOSE, "Mouse click: #%s, type: %s, pos: %s", c, m.getClickStatus(c), m.getMousePos());
				
			}
			
		}));
		
	}
	
	@Override
	protected void tick(double delta, Object... extra) throws Throwable
	{
		//CaelumEngine.log().log(EnumLogType.INFO, "Test: %s", delta);
		
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onGameShutdown()
	{
		// TODO Auto-generated method stub
		
	}
	
}
