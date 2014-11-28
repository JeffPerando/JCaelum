
package com.elusivehawk.caelum;

import com.elusivehawk.caelum.assets.AssetManager;
import com.elusivehawk.caelum.input.InputEvent;
import com.elusivehawk.caelum.input.Key;
import com.elusivehawk.caelum.input.KeyEvent;
import com.elusivehawk.caelum.input.MouseEvent;
import com.elusivehawk.caelum.input.PasteEvent;
import com.elusivehawk.caelum.render.Canvas;
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
		this.canvas.drawImage(0.2f, 0.2f, 0.8f, 0.8f, null);
		
	}
	
	@Override
	protected void onGameShutdown(){}
	
	@Override
	public void onInputReceived(InputEvent event, double delta)
	{
		if (event instanceof KeyEvent)
		{
			KeyEvent ke = (KeyEvent)event;
			
			if (ke.down)
			{
				if (ke.key == Key.ESCAPE)
				{
					ShutdownHelper.exit(1337);
					
				}
				else
				{
					Logger.log().log(EnumLogType.VERBOSE, "Key down: %s", ke.key);
					
				}
				
			}
			else
			{
				Logger.log().log(EnumLogType.VERBOSE, "Key up: %s", ke.key);
				
			}
			
		}
		else if (event instanceof MouseEvent)
		{
			MouseEvent me = (MouseEvent)event;
			
			Logger.log().log(EnumLogType.VERBOSE, "Mouse pos: %s", me.pos);
			
		}
		else if (event instanceof PasteEvent)
		{
			Logger.log().log(EnumLogType.VERBOSE, "Pasted: %s", ((PasteEvent)event).pasted);
			
		}
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		//CaelumEngine.log().log(EnumLogType.INFO, "Test: %s", delta);
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		this.canvas.preRender(rcon, delta);
		
	}
	
	@Override
	public void render(RenderContext rcon)
	{
		this.canvas.render(rcon);
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		this.canvas.postRender(rcon);
		
	}
	
}
