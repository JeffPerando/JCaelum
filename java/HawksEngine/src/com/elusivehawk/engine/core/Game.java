
package com.elusivehawk.engine.core;

import java.io.File;
import java.lang.management.ManagementFactory;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import com.elusivehawk.engine.render.IRenderHUB;
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
	protected String lwjglPath = determineLWJGLPath();
	
	//Methods for the custom game.
	
	/**
	 * 
	 * Called on every update.
	 * 
	 * @param delta The time between the last update and this one.
	 */
	protected abstract void update(long delta);
	
	protected abstract IRenderHUB getRenderHUB();
	
	//===============================BEGIN OPTIONAL GAME METHODS===============================
	
	/**
	 * Use this to set up everything you need before the game loop begins.
	 * 
	 * @return True to continue game loading, false otherwise.
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
	
	public int getTargetUpdates()
	{
		return 30;
	}
	
	public boolean isRunning()
	{
		return this.running;
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
		
		if (System.getProperty("org.lwjgl.librarypath") == null)
		{
			if (this.lwjglPath == null)
			{
				GameLog.warn("LWJGL path is set to null! What are you thinking?!");
				
				this.lwjglPath = determineLWJGLPath();
				
			}
			
			System.setProperty("org.lwjgl.librarypath", this.lwjglPath);
			
		}
		
		ThreadGameRender renderer = null;
		
		IRenderHUB hub = this.getRenderHUB();
		
		if (hub != null)
		{
			renderer = new ThreadGameRender(hub);
			
		}
		
		GameLog.info("Beginning game loop...");
		
		int targetUpdates = this.getTargetUpdates(), updates = 0;
		long lastTime = Sys.getTime(), delta = 0, fallback = targetUpdates + 1000L;
		Timer timer = new Timer();
		boolean rendering = (renderer != null);
		
		if (targetUpdates <= 0)
		{
			throw new RuntimeException("Invalid target update count!");
			
		}
		
		if (rendering)
		{
			renderer.start();
			
		}
		
		while (!Display.isCloseRequested() && this.isRunning())
		{
			delta = Sys.getTime() - lastTime;
			
			//TODO Fix
			/*if (delta < this.getDelayBetweenUpdates())
			{
				continue;
			}*/
			
			lastTime += delta;
			updates++;
			
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
		
		return (EnumOS.OS == EnumOS.LINUX && new File("/usr/lib/jni/liblwjgl.so").exists()) ? "/usr/lib/jni" : FileHelper.createFile("/lwjgl/native/" + EnumOS.OS.toString()).getAbsolutePath();
	}
	
}
