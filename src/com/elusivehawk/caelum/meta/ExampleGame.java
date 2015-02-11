
package com.elusivehawk.caelum.meta;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.caelum.CaelumEngine;
import com.elusivehawk.caelum.Display;
import com.elusivehawk.caelum.DisplaySettings;
import com.elusivehawk.caelum.Game;
import com.elusivehawk.caelum.input.Input;
import com.elusivehawk.caelum.input.Key;
import com.elusivehawk.caelum.input.Keyboard;
import com.elusivehawk.caelum.input.Mouse;
import com.elusivehawk.caelum.physics.IPhysicsSimulator;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderException;
import com.elusivehawk.caelum.render.SimpleRenderer;
import com.elusivehawk.caelum.render.gl.GLEnumDrawType;
import com.elusivehawk.caelum.render.tex.Color;
import com.elusivehawk.util.EnumLogType;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.Version;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class ExampleGame extends Game
{
	public static final Version VERSION = new Version(1, 0, 0);
	/*public static final int RANDOM_MATERIAL_CAP = 4;
	
	private Canvas canvas = new Canvas();
	private Gui gui = new Gui();*/
	
	private final FloatBuffer vtx = BufferHelper.makeFloatBuffer(new float[]{-1, -1, -1, 1, -1, -1, -1, 1, -1, -1, -1, 1, 1, 1, -1, -1, 1, 1, 1, -1, 1, 1, 1, 1});
	private final IntBuffer ind = BufferHelper.makeIntBuffer(new int[]{0, 1, 4, 5, 6, 1, 3, 0, 2, 4, 7, 6, 2, 3});
	
	private final SimpleRenderer renderer = new SimpleRenderer(this.vtx, this.ind, GLEnumDrawType.GL_TRIANGLE_STRIP, 12);
	
	public ExampleGame()
	{
		super("Example Game");
		
	}
	
	public static void main(String... args)
	{
		CaelumEngine.instance().start(new ExampleGame(), args);
		
	}
	
	@Override
	public void onInputReceived(Input input, double delta)
	{
		//this.gui.onInputReceived(input, delta);
		
		if (input instanceof Keyboard)
		{
			Keyboard kb = (Keyboard)input;
			
			kb.getDownKeys().forEach(((key) ->
			{
				Logger.log(EnumLogType.DEBUG, "Key down: %s", key);
				
			}));
			
			if (kb.isKeyDown(Key.CONTROL) && kb.isKeyDown(Key.V))
			{
				Logger.log(EnumLogType.DEBUG, "Pasted: %s", kb.getPaste());
				
			}
			
		}
		else if (input instanceof Mouse)
		{
			Mouse m = (Mouse)input;
			
			Logger.log(EnumLogType.DEBUG, "Mouse pos: %s; Delta: %s", m.getPosition(), m.getDelta());
			
		}
		
	}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
		this.renderer.render(rcon);
		
	}
	
	@Override
	public void preRender(RenderContext rcon)
	{
		this.renderer.preRender(rcon);
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		this.renderer.postRender(rcon);
		
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
		/*Random rng = RNG.rng();
		
		ITexture tex = new TextureAsset("/res/gui_buttons.png");
		
		this.canvas.setMaterial(tex);
		this.canvas.setMaterial(tex, Color.RED);
		this.canvas.setMaterial(tex, true);
		this.canvas.setMaterial(tex, Color.GREY);
		
		this.canvas.setMaterial(new TextureAsset("/res/test.png"), new Color(ColorFormat.RGB, rng.nextFloat(), rng.nextFloat(), rng.nextFloat()));
		
		this.canvas.drawImage(0f, 0f, 1f, 1f);
		
		this.gui.addComponent(new Button(0.0f, 0.7f, 0.25f, 0.9f, new Icon(0.0f, 0.0f, 0.5f, 0.5f)).setLeftClick(((d, button) ->
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
