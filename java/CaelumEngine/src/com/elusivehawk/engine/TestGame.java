
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
		CaelumEngine.addInputListener(Keyboard.class, ((delta, input) ->
		{
			Keyboard kb = (Keyboard)input;
			
			for (Key key : kb.getPushedKeys())
			{
				Logger.log().log(EnumLogType.VERBOSE, "Key type: %s", key);
				
			}
			
		}));
		
		CaelumEngine.addInputListener(Mouse.class, ((delta, input) ->
		{
			Mouse m = (Mouse)input;
			
			for (int c = 0; c < m.getButtonCount(); c++)
			{
				if (m.getClickStatus(c).isDown())
				{
					Logger.log().log(EnumLogType.VERBOSE, "Mouse click: #%s, pos: %s", c, m.getMousePos());
					
				}
				
			}
			
		}));
		
	}
	
	@Override
	protected void tick(double delta) throws Throwable
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
