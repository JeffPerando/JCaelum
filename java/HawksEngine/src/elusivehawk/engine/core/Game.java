
package elusivehawk.engine.core;

import java.io.File;
import java.lang.management.ManagementFactory;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import elusivehawk.engine.render.Color;
import elusivehawk.engine.render.EnumColorFilter;
import elusivehawk.engine.render.GL;
import elusivehawk.engine.render.GLProgram;
import elusivehawk.engine.render.IScene;
import elusivehawk.engine.render.RenderEngine;
import elusivehawk.engine.util.GameLog;
import elusivehawk.engine.util.TextParser;
import elusivehawk.engine.util.Timer;

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
	 * 
	 * Called on every update.
	 * 
	 * @param delta The time between the last update and this one.
	 */
	protected abstract void update(long delta);
	
	/**
	 * Called before the game is closed out.
	 */
	protected abstract void onGameClosed();
	
	/**
	 * 
	 * @return The rendering mode for this game.
	 */
	public abstract EnumRenderMode getRenderMode();
	
	protected abstract GameSettings getSettings();
	
	protected abstract IScene getCurrentScene();
	
	//===============================BEGIN OPTIONAL GAME METHODS===============================
	
	/**
	 * Use this to set up everything you need before the game loop begins.
	 * 
	 * @return False to stop the game.
	 */
	protected boolean initiate()
	{
		this.running = true;
		
		return true;
	}
	
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
	
	public void handleException(Throwable e)
	{
		GameLog.error(e);
		
	}
	
	//===============================END OPTIONAL GAME METHODS===============================
	
	/**
	 * Call this when you're ready for the game loop to be handled automatically.
	 */
	protected final void start()
	{
		if (currentGame != null)
		{
			return;
		}
		
		currentGame = this;
		
		if (!this.initiate())
		{
			currentGame = null;
			this.onGameClosed();
			
			return;
		}
		
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
			try
			{
				Display.setTitle(settings.title);
				Display.setIcon(settings.icons);
				Color bg = settings.bg;
				Display.setInitialBackground(bg.getColorFloat(EnumColorFilter.RED), bg.getColorFloat(EnumColorFilter.GREEN), bg.getColorFloat(EnumColorFilter.BLUE));
				Display.setDisplayMode(settings.mode);
				Display.setFullscreen(settings.fullscreen);
				Display.setVSyncEnabled(settings.vsync);
				Display.setResizable(settings.resize);
				
				Display.create();
				
				Display.setDisplayConfiguration(settings.gamma, settings.brightness, settings.constrast);
				
				GL.glViewport(0, 0, settings.mode.getWidth(), settings.mode.getHeight());
				GL.glClearColor(bg.getColorFloat(EnumColorFilter.RED), bg.getColorFloat(EnumColorFilter.GREEN), bg.getColorFloat(EnumColorFilter.BLUE), bg.getColorFloat(EnumColorFilter.ALPHA));
				
			}
			catch (LWJGLException e)
			{
				this.handleException(e);
				
			}
			
		}
		
		GameLog.info("Beginning game loop...");
		
		long lastTime = Sys.getTime(), delta = 0, fallback = settings.fallbackDelay;
		int targetFPS = settings.targetFPS, targetUpdates = settings.targetUpdates;
		int updates = 0;
		Timer timer = new Timer();
		
		if (targetUpdates <= 0)
		{
			throw new RuntimeException("Invalid target update count!");
			
		}
		
		while (!Display.isCloseRequested() && this.isRunning())
		{
			delta = Sys.getTime() - lastTime;
			
			if (delta >= this.getDelayBetweenUpdates())
			{
				lastTime += delta;
				updates++;
				
				if (this.updateSettings())
				{
					settings = this.getSettings();
					
					try
					{
						Display.setDisplayMode(settings.mode);
						Display.setFullscreen(settings.fullscreen);
						Display.setVSyncEnabled(settings.vsync);
						Display.setDisplayConfiguration(settings.gamma, settings.brightness, settings.constrast);
						
					}
					catch (LWJGLException e)
					{
						this.handleException(e);
						
					}
					
					targetFPS = settings.targetFPS;
					targetUpdates = settings.targetUpdates;
					
					if (targetUpdates <= 0)
					{
						this.handleException(new RuntimeException("Invalid target update count!"));
						
					}
					
				}
				
				timer.start();
				
				try
				{
					this.update(delta);
					
				}
				catch (Throwable e)
				{
					this.handleException(e);
					
				}
				
				timer.stop();
				
				if (timer.report() >= fallback)
				{
					try
					{
						Thread.sleep(1L);
						
					}
					catch (InterruptedException e){}
					
					continue;
				}
				
				if (RenderEngine.render(this.getCurrentScene(), this.getRenderMode()))
				{
					Display.sync(targetFPS);
					Display.update();
					
				}
				
				if (updates >= targetUpdates)
				{
					try
					{
						Thread.sleep(1L);
						
					}
					catch (InterruptedException e){}
					
				}
				
			}
			
		}
		
		this.onGameClosed();
		
		GLProgram.deletePrograms();
		
		Display.destroy();
		
		System.exit(0);
		
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
