
package com.elusivehawk.engine.core;

import java.io.File;
import java.lang.management.ManagementFactory;
import com.elusivehawk.engine.render.IRenderHUB;
import com.elusivehawk.engine.render.ThreadGameRender;
import com.elusivehawk.engine.sound.ThreadSoundPlayer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class CaelumEngine
{
	private static final CaelumEngine INSTANCE = new CaelumEngine();
	public static final boolean DEBUG = ManagementFactory.getRuntimeMXBean().getInputArguments().toString().contains("-agentlib:jdwp");
	public static final String VERSION = "1.0.0";
	
	public final Object startupHook = new Object();
	public final Object shutdownHook = new Object();
	private IGame game;
	private ThreadGameLoop threadCore;
	private ThreadGameRender threadRender;
	private ThreadSoundPlayer threadSound;
	
	private CaelumEngine(){}
	
	public static CaelumEngine instance()
	{
		return INSTANCE;
	}
	
	public void start(IGame g)
	{
		if (this.game != null)
		{
			return;
		}
		
		this.game = g;
		
		if (System.getProperty("org.lwjgl.librarypath") == null)
		{
			String lwjgl = game.getLWJGLPath();
			
			if (lwjgl == null)
			{
				GameLog.warn("LWJGL path is set to null! What are you thinking?!");
				
				lwjgl = CaelumEngine.instance().determineLWJGLPath();
				
			}
			
			System.setProperty("org.lwjgl.librarypath", lwjgl);
			
		}
		
		this.threadCore = new ThreadGameLoop(this.game);
		
		IRenderHUB hub = this.game.getRenderHUB();
		
		if (hub != null)
		{
			this.threadRender = new ThreadGameRender(hub);
			
		}
		
		this.threadSound = new ThreadSoundPlayer();
		
		this.threadCore.start();
		if (this.threadRender != null) this.threadRender.start();
		this.threadSound.start();
		
		this.startupHook.notifyAll();
		
		try
		{
			this.shutdownHook.wait();
			
		}
		catch (InterruptedException e)
		{
			GameLog.error(e);
			
		}
		
		this.threadCore.stopThread();
		if (this.threadRender != null) this.threadRender.stopThread();
		this.threadSound.stopThread();
		
	}
	
	public void shutDownGame(int error)
	{
		try
		{
			this.shutdownHook.notifyAll();
			
		}
		catch (Exception e){}
		
		System.exit(error);
		
	}
	
	public String determineLWJGLPath()
	{
		//TODO: this only works on Debian... but we'll try it for now.
		
		return (EnumOS.OS == EnumOS.LINUX && new File("/usr/lib/jni/liblwjgl.so").exists()) ? "/usr/lib/jni" : FileHelper.createFile("/lwjgl/native/" + EnumOS.OS.toString()).getAbsolutePath();
	}
	
	public IGame getCurrentGame()
	{
		return this.game;
	}
	
}
