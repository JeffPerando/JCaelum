
package com.elusivehawk.engine.core;

import java.io.File;
import java.lang.management.ManagementFactory;
import com.elusivehawk.engine.core.concurrent.ThreadGameLoop;
import com.elusivehawk.engine.core.concurrent.ThreadGameRender;
import com.elusivehawk.engine.core.concurrent.ThreadSoundPlayer;
import com.elusivehawk.engine.render.IRenderHUB;

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
	
	public final Object shutdownHook = new Object();
	private ThreadGameLoop threadCore;
	private ThreadGameRender threadRender;
	private ThreadSoundPlayer threadSound;
	
	private CaelumEngine(){}
	
	public static CaelumEngine instance()
	{
		return INSTANCE;
	}
	
	public void start(IGame game)
	{
		if (this.threadCore != null)
		{
			return;
		}
		
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
		
		System.setProperty("org.lwjgl.opengl.Display.noinput", "true");
		
		this.threadCore = new ThreadGameLoop(game);
		
		IRenderHUB hub = game.getRenderHUB();
		
		if (hub != null)
		{
			this.threadRender = new ThreadGameRender(hub);
			
		}
		
		this.threadSound = new ThreadSoundPlayer();
		
		this.threadCore.run();
		this.threadRender.run();
		this.threadSound.run();
		
	}
	
	public void shutDownGame(int error)
	{
		if (this.threadCore != null)
		{
			this.threadCore.stopThread();
			if (this.threadRender != null) this.threadRender.stopThread();
			this.threadSound.stopThread();
			
			this.threadCore = null;
			this.threadRender = null;
			this.threadSound = null;
			
			try
			{
				this.shutdownHook.notifyAll();
				
			}
			catch (Exception e){}
			
			System.exit(error);
			
		}
		
	}
	
	public String determineLWJGLPath()
	{
		//TODO: this only works on Debian... but we'll try it for now.
		
		return (EnumOS.OS == EnumOS.LINUX && new File("/usr/lib/jni/liblwjgl.so").exists()) ? "/usr/lib/jni" : FileHelper.createFile("/lwjgl/native/" + EnumOS.OS.toString()).getAbsolutePath();
	}
	
}
