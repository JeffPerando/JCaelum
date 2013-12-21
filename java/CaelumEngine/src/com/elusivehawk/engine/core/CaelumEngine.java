
package com.elusivehawk.engine.core;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import com.elusivehawk.engine.render.IRenderHUB;
import com.elusivehawk.engine.render.ThreadGameRender;
import com.elusivehawk.engine.sound.ThreadSoundPlayer;

/**
 * 
 * The core class for the Caelum Engine.
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
	private Map<EnumEngineFeature, ThreadStoppable> threads = new HashMap<EnumEngineFeature, ThreadStoppable>();
	private IGame game;
	private IGameEnvironment environment;
	private ILog log = new GameLog();
	
	private CaelumEngine(){}
	
	public static CaelumEngine instance()
	{
		return INSTANCE;
	}
	
	public static void main(String... args)
	{
		System.out.println("Starting Caelum Engine v" + VERSION);
		
		if (args.length == 0)
		{
			System.err.println("The starting arguments require the path to the file that the launch settings are in.");
			return;
		}
		
		Buffer<String> buf = new Buffer<String>(args);
		
		String cur = buf.next();
		
		if (cur.startsWith("file:"))
		{
			File file = FileHelper.createFile(".", TextParser.splitOnce(cur, "file:")[1]);
			
			if (!file.exists())
			{
				return;
			}
			
			if (!file.isFile())
			{
				return;
			}
			
			if (!file.canRead())
			{
				return;
			}
			
			//TODO Parse JSON file
			
			cur = buf.next();
			
		}
		
		IGameEnvironment env = null;
		
		if (cur.startsWith("class:"))
		{
			env = (IGameEnvironment)ReflectionHelper.newInstance(TextParser.splitOnce(cur, "class:")[1], new Class<?>[]{IGameEnvironment.class}, null);
			
		}
		
		if (env == null)
		{
			System.err.println("Could not load game environment.");
			System.exit("NO-ENVIRONMENT-FOUND".hashCode());
			
		}
		
		env.initiate();
		
		instance().environment = env;
		
		cur = buf.next();
		
		IGame g = null;
		
		if (cur.startsWith("game:"))
		{
			g = (IGame)ReflectionHelper.newInstance(TextParser.splitOnce(cur, "game:")[1], new Class<?>[]{IGame.class}, null);
			
		}
		
		if (g == null)
		{
			System.err.println("Could not load game.");
			System.exit("NO-GAME-FOUND".hashCode());
			
		}
		
		ILog log = env.getLog();
		
		if (log != null)
		{
			instance().log = log;
			
		}
		
		instance().start(g);
		
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
			String lwjgl = this.game.getLWJGLPath();
			
			if (lwjgl == null)
			{
				//GameLog.warn("LWJGL path is set to null! What are you thinking?!");
				
				lwjgl = CaelumEngine.determineLWJGLPath();
				
			}
			
			System.setProperty("org.lwjgl.librarypath", lwjgl);
			
		}
		
		this.threads.put(EnumEngineFeature.LOGIC, new ThreadGameLoop(this.game));
		
		IRenderHUB hub = this.game.getRenderHUB();
		
		if (hub != null)
		{
			this.threads.put(EnumEngineFeature.RENDER, new ThreadGameRender(hub));
			
		}
		
		this.threads.put(EnumEngineFeature.SOUND, new ThreadSoundPlayer());
		
		//TODO Moar threadz!!!
		
		for (EnumEngineFeature fe : EnumEngineFeature.values())
		{
			ThreadStoppable t = this.threads.get(fe);
			
			if (t != null)
			{
				t.start();
				
			}
			
		}
		
		this.startupHook.notifyAll();
		
	}
	
	public void shutDownGame(String err)
	{
		this.shutDownGame(err == null ? 0 : err.hashCode());
		
	}
	
	public void shutDownGame(int error)
	{
		if (this.game == null)
		{
			return;
		}
		
		if (!this.game.enableShutdown())
		{
			return;
		}

		for (EnumEngineFeature fe : EnumEngineFeature.values())
		{
			ThreadStoppable t = this.threads.get(fe);
			
			if (t != null)
			{
				t.stopThread();
				
			}
			
		}
		
		try
		{
			this.shutdownHook.notifyAll();
			
		}
		catch (Exception e){}
		
		System.exit(error);
		
	}
	
	public static String determineLWJGLPath()
	{
		//TODO: this only works on Debian... but we'll try it for now.
		
		return (EnumOS.OS == EnumOS.LINUX && new File("/usr/lib/jni/liblwjgl.so").exists()) ? "/usr/lib/jni" : FileHelper.createFile("/lwjgl/native/" + EnumOS.OS.toString()).getAbsolutePath();
	}
	
	public IGame getCurrentGame()
	{
		return this.game;
	}
	
	public ILog getLog()
	{
		return this.log;
	}
	
	public void pauseGame(boolean pause)
	{
		this.pauseGame(pause, EnumEngineFeature.values());
		
	}
	
	public void pauseGame(boolean pause, EnumEngineFeature... features)
	{
		for (EnumEngineFeature fe : features)
		{
			ThreadStoppable t = this.threads.get(fe);
			
			if (t != null)
			{
				t.setPaused(pause);
				
			}
			
		}
		
	}
	
}
