
package com.elusivehawk.caelum.meta;

import java.util.Random;
import com.elusivehawk.caelum.CaelumEngine;
import com.elusivehawk.caelum.Display;
import com.elusivehawk.caelum.DisplaySettings;
import com.elusivehawk.caelum.Game;
import com.elusivehawk.caelum.input.InputEvent;
import com.elusivehawk.caelum.physics.IPhysicsSimulator;
import com.elusivehawk.caelum.prefab.gui.Gui;
import com.elusivehawk.caelum.render.Canvas;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderException;
import com.elusivehawk.caelum.render.tex.Color;
import com.elusivehawk.caelum.render.tex.ITexture;
import com.elusivehawk.caelum.render.tex.TextureAsset;
import com.elusivehawk.util.RNG;
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
	//public static final int RANDOM_MATERIAL_CAP = 4;
	
	private Canvas canvas = new Canvas();
	private Gui gui = new Gui();
	
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
		this.gui.onInputReceived(event, delta);
		
		/*if (event instanceof KeyEvent)
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
			
		}*/
		
	}
	
	@Override
	public boolean render(RenderContext rcon) throws RenderException
	{
		return this.canvas.render(rcon);
	}
	
	@Override
	public void preRender(RenderContext rcon)
	{
		this.canvas.preRender(rcon);
		
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
		return new DisplaySettings(((settings) ->
		{
			settings.title = "Example Game (Now with a GUI!)";
			settings.width = 600;
			settings.height = 600;
			settings.bg = Color.GREY;
			
		}));
	}
	
	@Override
	public void initiate(Display display) throws Throwable
	{
		Random rng = RNG.rng();
		
		ITexture tex = new TextureAsset("/res/gui_buttons.png");
		
		/*this.canvas.setMaterial(tex);
		this.canvas.setMaterial(tex, Color.RED);
		this.canvas.setMaterial(tex, true);
		this.canvas.setMaterial(tex, Color.GREY);
		
		this.canvas.setMaterial(new TextureAsset("/res/test.png"), new Color(ColorFormat.RGB, rng.nextFloat(), rng.nextFloat(), rng.nextFloat()));*/
		
		this.canvas.drawImage(0f, 0f, 1f, 1f);
		
		/*this.gui.addComponent(new Button(0.0f, 0.7f, 0.25f, 0.9f, new Icon(0.0f, 0.0f, 0.5f, 0.5f)).setLeftClick(((d, button) ->
		{
			Logger.info("Button #1 clicked!");
			
		})));
		this.gui.addComponent(new Button(0.25f, 0.7f, 0.5f, 0.9f, new Icon(0.5f, 0.0f, 1.0f, 0.5f)).setLeftClick(((d, button) ->
		{
			Logger.info("Button #2 clicked!");
			
		})));
		this.gui.addComponent(new Button(0.5f, 0.7f, 0.75f, 0.9f, new Icon(0.0f, 0.5f, 0.5f, 1.0f)).setLeftClick(((d, button) ->
		{
			Logger.info("Button #3 clicked!");
			
		})));
		this.gui.addComponent(new Button(0.75f, 0.7f, 1.0f, 0.9f, new Icon(0.5f, 0.5f, 1.0f, 1.0f)).setLeftClick(((d, button) ->
		{
			Logger.info("Button #4 clicked!");
			
		})));*/
		
		/*ITexture testimg = new TextureAsset("/res/test.png");
		
		this.canvas.addMaterial(new Material().tex(testimg).lock());
		
		for (int c = 0; c < RANDOM_MATERIAL_CAP; c++)
		{
			this.canvas.addMaterial(new Material(((mat) ->
			{
				mat.filter(new Color(ColorFormat.RGB, rng.nextFloat(), rng.nextFloat(), rng.nextFloat()));
				mat.tex(testimg);
				mat.invert(rng.nextBoolean());
				
			})).lock());
			
		}
		
		this.canvas.drawImage(0.2f, 0.2f, 0.8f, 0.8f);
		
		this.canvas.drawImage(0.0f, 0.0f, 0.2f, 0.2f, new Icon(0.0f, 0.0f, 0.5f, 0.5f));
		this.canvas.drawImage(0.8f, 0.8f, 1.0f, 1.0f, new Icon(0.5f, 0.5f, 1.0f, 1.0f));
		this.canvas.drawImage(0.0f, 0.8f, 0.2f, 1.0f, new Icon(0.0f, 0.5f, 0.5f, 1.0f));
		this.canvas.drawImage(0.8f, 0.0f, 1.0f, 0.2f, new Icon(0.5f, 0.0f, 1.0f, 0.5f));*/
		
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
