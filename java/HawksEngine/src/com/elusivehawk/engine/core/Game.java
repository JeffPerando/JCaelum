
package com.elusivehawk.engine.core;

import java.io.File;
import java.lang.management.ManagementFactory;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import com.elusivehawk.engine.render.Color;
import com.elusivehawk.engine.render.EnumColorFilter;
import com.elusivehawk.engine.render.GL;
import com.elusivehawk.engine.render.IRenderHUB;
import com.elusivehawk.engine.render.RenderHelper;
import com.elusivehawk.engine.render.ThreadGameRender;

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
	
	protected abstract GameSettings getSettings();
	
	protected abstract IRenderHUB getRenderHUB();
	
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
	
	/**
	 * Called before the game is closed out.
	 */
	protected void onGameClosed()
	{
		this.running = false;
		
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
		
		if (settings == null)
		{
			settings = new GameSettings();
			
		}
		
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
				
				//TODO Display.setDisplayConfiguration(settings.gamma, settings.brightness, settings.constrast);
				
				GL.glViewport(0, 0, settings.mode.getWidth(), settings.mode.getHeight());
				GL.glClearColor(bg.getColorFloat(EnumColorFilter.RED), bg.getColorFloat(EnumColorFilter.GREEN), bg.getColorFloat(EnumColorFilter.BLUE), bg.getColorFloat(EnumColorFilter.ALPHA));
				
			}
			catch (LWJGLException e)
			{
				this.handleException(e);
				
			}
			
		}
		
		ThreadGameRender renderer = null;
		
		IRenderHUB hub = this.getRenderHUB();
		
		if (hub != null)
		{
			renderer = new ThreadGameRender(hub, settings.targetFPS);
			
		}
		
		GameLog.info("Beginning game loop...");
		
		long lastTime = Sys.getTime(), delta = 0, fallback = settings.fallbackDelay;
		int targetUpdates = settings.targetUpdates, updates = 0;
		Timer timer = new Timer();
		boolean rendering = (renderer != null);
		
		if (targetUpdates <= 0)
		{
			throw new RuntimeException("Invalid target update count!");
			
		}
		
		if (renderer != null)
		{
			renderer.start();
			
		}
		
		while (!Display.isCloseRequested() && this.isRunning())
		{
			delta = Sys.getTime() - lastTime;
			
			if (delta < this.getDelayBetweenUpdates())
			{
				continue;
			}
			
			lastTime += delta;
			updates++;
			
			if (this.updateSettings())
			{
				settings = this.getSettings();
				
				if (rendering)
				{
					renderer.setPaused(true);
					
				}
				
				RenderHelper.makeContextCurrent();
				
				try
				{
					Display.setDisplayMode(settings.mode);
					Display.setFullscreen(settings.fullscreen);
					Display.setVSyncEnabled(settings.vsync);
					//TODO Display.setDisplayConfiguration(settings.gamma, settings.brightness, settings.constrast);
					
				}
				catch (LWJGLException e)
				{
					this.handleException(e);
					
				}
				
				if (rendering)
				{
					renderer.setTargetFPS(settings.targetFPS);
					
					renderer.setPaused(false);
					
				}
				
				if (settings.targetUpdates > 0)
				{
					targetUpdates = settings.targetUpdates;
					
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
			
			if (timer.report() >= fallback || updates == targetUpdates)
			{
				try
				{
					Thread.sleep(1L);
					
				}
				catch (InterruptedException e){}
				
			}
			
		}
		
		this.onGameClosed();
		
		if (rendering)
		{
			renderer.stopThread();
			
		}
		
		GL.cleanup();
		
		Display.destroy();
		
		System.exit(0);
		
	}
	
	//===============================END OPTIONAL GAME METHODS===============================
	
	//Static methods.
	
	public static Game getCurrentGame()
	{
		return currentGame;
	}
	
	public static String determineLWJGLPath()
	{
		//TODO: this only works on Debian... but we'll try it for now.
		
		return (EnumOS.OS == EnumOS.LINUX && new File("/usr/lib/jni/liblwjgl.so").exists()) ? "/usr/lib/jni" : TextParser.createFile("/lwjgl/native/" + EnumOS.OS.toString()).getAbsolutePath();
	}
	
}
