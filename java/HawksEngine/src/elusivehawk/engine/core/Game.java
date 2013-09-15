
package elusivehawk.engine.core;

import java.io.File;
import java.lang.management.ManagementFactory;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import elusivehawk.engine.render.Color;
import elusivehawk.engine.render.EnumColor;
import elusivehawk.engine.render.GL;
import elusivehawk.engine.render.IScene;
import elusivehawk.engine.render.RenderEngine;
import elusivehawk.engine.render.RenderHelper;
import elusivehawk.engine.util.GameLog;
import elusivehawk.engine.util.TextParser;

/**
 * 
 * The core game class.
 * 
 * @author Elusivehawk
 */
public abstract class Game
{
	private static Game currentGame;
	public static final boolean DEBUG = ManagementFactory.getRuntimeMXBean().getInputArguments().toString().contains("-agentlib:jdwp");
	
	protected boolean running = false;
	
	//Methods for the custom game.
	
	/**
	 * Use this to set up everything you need before the game loop begins.
	 */
	protected abstract void initiate();
	
	/**
	 * 
	 * Called on every update.
	 * 
	 * @param delta The time between the last update and this one.
	 */
	protected abstract void update(long delta);
	
	protected abstract void onGameClosed();
	
	/**
	 * 
	 * @return Whether or not the rendering thread should be activated.
	 */
	public abstract boolean shouldRender();
	
	protected abstract GameSettings getSettings();
	
	protected abstract IScene getCurrentScene();
	
	//===============================BEGIN OPTIONAL GAME METHODS===============================
	
	public int getDelayBetweenUpdates()
	{
		return 10;
	}
	
	public boolean isRunning()
	{
		return this.running;
	}
	
	public boolean updateSettings()
	{
		return false;
	}
	
	public void handleError(Throwable e)
	{
		GameLog.error(e);
		
	}
	
	/**
	 * Call this when you're ready for the game loop to be handled automatically.
	 */
	protected void start()
	{
		if (currentGame != null)
		{
			return;
		}
		
		currentGame = this;
		this.running = true;
		
		try
		{
			this.initiate();
			
			GameSettings settings = this.getSettings();
			
			if (System.getProperty("org.lwjgl.librarypath") == null)
			{
				if (settings.lwjglPath == null)
				{
					GameLog.warn("LWJGL path is set to null! What are you thinking?!");
					
					settings.lwjglPath = determineLWJGLPath();
					
				}
				
				System.setProperty("org.lwjgl.librarypath", settings.lwjglPath);
				
			}
			
			if (!Display.isCreated())
			{
				Display.setTitle(settings.title);
				Display.setIcon(settings.icons);
				Color bg = settings.bg;
				Display.setInitialBackground(bg.getColorFloat(EnumColor.RED), bg.getColorFloat(EnumColor.GREEN), bg.getColorFloat(EnumColor.BLUE));
				Display.setDisplayMode(settings.mode);
				Display.setFullscreen(settings.fullscreen);
				Display.setVSyncEnabled(settings.vsync);
				Display.setResizable(settings.resize);
				
				Display.create();
				
				Display.setDisplayConfiguration(settings.gamma, settings.brightness, settings.constrast);
				
				GL.glViewport(0, 0, settings.mode.getWidth(), settings.mode.getHeight());
				GL.glClearColor(bg.getColorFloat(EnumColor.RED), bg.getColorFloat(EnumColor.GREEN), bg.getColorFloat(EnumColor.BLUE), bg.getColorFloat(EnumColor.ALPHA));
				
			}
			
			GameLog.info("Beginning game loop...");
			
			long lastTime = Sys.getTime(), delta = 0;
			int targetFPS = settings.targetFPS;
			
			while (!Display.isCloseRequested() && this.isRunning())
			{
				delta = Sys.getTime() - lastTime;
				
				if (delta >= this.getDelayBetweenUpdates())
				{
					lastTime += delta;
					
					if (this.updateSettings())
					{
						settings = this.getSettings();
						
						Display.setTitle(settings.title != null ? settings.title : "Caelum Engine Game (Now with a streamlined Game class!)");
						Display.setDisplayMode(settings.mode);
						Display.setFullscreen(settings.fullscreen);
						Display.setVSyncEnabled(settings.vsync);
						Display.setDisplayConfiguration(settings.gamma, settings.brightness, settings.constrast);
						
						targetFPS = settings.targetFPS;
						
					}
					
					this.update(delta);
					
					if (this.shouldRender())
					{
						RenderEngine.render(this.getCurrentScene());
						
						Display.sync(targetFPS);
						Display.update();
						
					}
					
				}
				
			}
			
			this.onGameClosed();
			
			RenderHelper.deletePrograms();
			
			Display.destroy();
			
			System.exit(0);
			
		}
		catch (Exception e)
		{
			this.handleError(e);
			
		}
		
	}
	
	//Static methods.
	
	public static Game getCurrentGame()
	{
		return currentGame;
	}
	
	public static String determineLWJGLPath()
	{
		//TODO: this only works on Debian... but we'll try it for now.
		
		return (EnumOS.OS == EnumOS.LINUX && new File("/usr/lib/jni/liblwjgl.so").exists()) ? "/usr/lib/jni" : TextParser.createFile("/lwjgl/native").getAbsolutePath();
	}
	
}
