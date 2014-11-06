
package com.elusivehawk.caelum;

import com.elusivehawk.caelum.assets.AssetManager;
import com.elusivehawk.caelum.input.Key;
import com.elusivehawk.caelum.input.Keyboard;
import com.elusivehawk.caelum.input.Mouse;
import com.elusivehawk.caelum.render.Canvas;
import com.elusivehawk.caelum.render.Icon;
import com.elusivehawk.caelum.render.RenderContext;
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
		CaelumEngine.addInputListener(Keyboard.class, ((in, delta) ->
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
		
		CaelumEngine.addInputListener(Mouse.class, ((in, delta) ->
		{
			Mouse m = (Mouse)in;
			
			Logger.log().log(EnumLogType.VERBOSE, "Mouse pos: %s", m.getMousePos());
			
		}));
		
		this.canvas.drawImage(0, 0, 1, 1, (Icon)null);
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		//CaelumEngine.log().log(EnumLogType.INFO, "Test: %s", delta);
		
	}
	
	@Override
	public void render(RenderContext rcon)
	{
		try
		{
			this.canvas.render(rcon);
			
		}
		catch (Exception e)
		{
			Logger.log().err(e);
			ShutdownHelper.exit("CANNOT-RENDER");
		}
		
	}
	
	@Override
	protected void onGameShutdown(){}
	
}
