
package com.elusivehawk.engine;

import com.elusivehawk.engine.assets.AssetManager;
import com.elusivehawk.engine.input.Key;
import com.elusivehawk.engine.input.Keyboard;
import com.elusivehawk.engine.input.Mouse;
import com.elusivehawk.engine.render.Canvas;
import com.elusivehawk.engine.render.Icon;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.util.EnumLogType;
import com.elusivehawk.util.Internal;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.ShutdownHelper;
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
	
	private Canvas canvas = new Canvas();
	
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
	protected void initiateGame(GameArguments args, AssetManager assets)
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
			
			Logger.log().log(EnumLogType.VERBOSE, "Mouse pos: %s", m.getMousePos());
			
		}));
		
		this.canvas.drawImage(0, 0, 1, 1, (Icon)null);
		
	}
	
	@Override
	protected void tick(double delta, Object... extra) throws Throwable
	{
		//CaelumEngine.log().log(EnumLogType.INFO, "Test: %s", delta);
		
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void render(RenderContext rcon, double delta)
	{
		try
		{
			this.canvas.render(rcon, delta);
			
		}
		catch (Exception e)
		{
			Logger.log().err(e);
			ShutdownHelper.exit("CANNOT-RENDER");
		}
		
	}
	
	@Override
	protected void onGameShutdown()
	{
		// TODO Auto-generated method stub
		
	}
	
}
