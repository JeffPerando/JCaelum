
package com.elusivehawk.caelum.meta;

import com.elusivehawk.caelum.CaelumEngine;
import com.elusivehawk.caelum.Display;
import com.elusivehawk.caelum.DisplaySettings;
import com.elusivehawk.caelum.Game;
import com.elusivehawk.caelum.assets.AssetManager;
import com.elusivehawk.caelum.input.InputEvent;
import com.elusivehawk.caelum.input.Key;
import com.elusivehawk.caelum.input.KeyEvent;
import com.elusivehawk.caelum.input.MouseEvent;
import com.elusivehawk.caelum.input.PasteEvent;
import com.elusivehawk.caelum.physics.IPhysicsSimulator;
import com.elusivehawk.caelum.render.Canvas;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderException;
import com.elusivehawk.util.EnumLogType;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.Version;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class ExampleGame extends Game
{
	public static final Version VERSION = new Version(1, 0, 0);
	
	private Canvas canvas = new Canvas();
	
	public ExampleGame()
	{
		super("Example Game");
		
	}
	
	public static void main(String... args)
	{
		CaelumEngine.start((() -> {return new ExampleGame();}), args);
		
	}
	
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
					event.display.close();
					
				}
				else
				{
					Logger.log(EnumLogType.DEBUG, "Key down: %s", ke.key);
					
				}
				
			}
			else
			{
				Logger.log(EnumLogType.DEBUG, "Key up: %s", ke.key);
				
			}
			
		}
		else if (event instanceof MouseEvent)
		{
			MouseEvent me = (MouseEvent)event;
			
			Logger.log(EnumLogType.DEBUG, "Mouse pos: %s; Delta: %s", me.pos, me.delta);
			
		}
		else if (event instanceof PasteEvent)
		{
			Logger.log(EnumLogType.DEBUG, "Pasted: %s", ((PasteEvent)event).pasted);
			
		}
		
	}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
		this.canvas.render(rcon);
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		this.canvas.preRender(rcon, delta);
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		this.canvas.postRender(rcon);
		
	}
	
	@Override
	public Version getGameVersion()
	{
		return VERSION;
	}
	
	@Override
	public DisplaySettings getDisplaySettings()
	{
		return null;
	}
	
	@Override
	public void initiate(Display display, AssetManager assets) throws Throwable
	{
		this.canvas.drawImage(0f, 0f, 1f, 1f, null);
		
	}
	
	@Override
	public void onShutdown(){}
	
	@Override
	public IPhysicsSimulator getPhysicsSimulator()
	{
		return null;
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		//CaelumEngine.log(EnumLogType.INFO, "Test: %s", delta);
		
	}
	
}
