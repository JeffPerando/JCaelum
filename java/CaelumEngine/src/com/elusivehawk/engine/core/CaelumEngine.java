
package com.elusivehawk.engine.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.elusivehawk.engine.physics.IPhysicsScene;
import com.elusivehawk.engine.physics.ThreadPhysics;
import com.elusivehawk.engine.render.IRenderHUB;
import com.elusivehawk.engine.render.ThreadGameRender;
import com.elusivehawk.engine.util.Buffer;
import com.elusivehawk.engine.util.FileHelper;
import com.elusivehawk.engine.util.ReflectionHelper;
import com.elusivehawk.engine.util.TextParser;
import com.elusivehawk.engine.util.ThreadStoppable;
import com.elusivehawk.engine.util.Tuple;

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
			return;
		}
		
		Buffer<String> buf = new Buffer<String>(args);
		
		String cur = (buf.hasNext() ? buf.next() : "");
		IGameEnvironment env = null;
		JsonObject json = null;
		
		if (cur.startsWith("class:"))
		{
			env = (IGameEnvironment)ReflectionHelper.newInstance(TextParser.splitOnce(cur, "class:")[1], new Class<?>[]{IGameEnvironment.class}, null);
			
		}
		else
		{
			File file = FileHelper.createFile(".", "/Game.json");
			
			if (!file.exists())
			{
				return;
			}
			
			if (!file.canRead())
			{
				return;
			}
			
			FileInputStream fis = FileHelper.createInStream(file);
			
			if (fis != null)
			{
				JsonObject j = null;
				
				try
				{
					j = JsonObject.readFrom(new BufferedReader(new FileReader(file)));
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
					
				}
				
				if (j != null)
				{
					JsonValue curEnv = j.get(EnumOS.getCurrentOS().toString());
					
					if (curEnv != null && curEnv.isObject())
					{
						json = curEnv.asObject();
						JsonValue envLoc = json.get("location");
						
						if (envLoc.isString())
						{
							File envLibFile = FileHelper.createFile(envLoc.asString());
							
							if (envLibFile.exists() && envLibFile.canRead() && envLibFile.getName().endsWith(".jar"))
							{
								Tuple<ClassLoader, Set<Class<?>>> tuple = ReflectionHelper.loadLibrary(envLibFile);
								Set<Class<?>> set = tuple.two;
								
								if (set != null && !set.isEmpty())
								{
									for (Class<?> c : set)
									{
										env = (IGameEnvironment)ReflectionHelper.newInstance(c, new Class<?>[]{IGameEnvironment.class}, null);
										
										if (env != null)
										{
											break;
										}
										
									}
									
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
		if (env == null)
		{
			System.err.println("Could not load game environment.");
			System.exit("NO-ENVIRONMENT-FOUND".hashCode());
			
		}
		
		if (!env.isCompatible(EnumOS.getCurrentOS()))
		{
			return;
		}
		
		cur = buf.next();
		
		env.initiate(json, buf.toArray());
		
		instance().environment = env;
		
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
		
		ILog log = instance().environment.getLog();
		
		if (log != null)
		{
			instance().log = log;
			
		}
		
		instance().start(g, buf);
		
	}
	
	private void start(IGame g, Buffer<String> args)
	{
		if (this.game != null)
		{
			return;
		}
		
		this.game = g;
		this.threads.put(EnumEngineFeature.LOGIC, new ThreadGameLoop(this.game, args));
		
		IRenderHUB hub = this.game.getRenderHUB();
		
		if (hub != null)
		{
			this.threads.put(EnumEngineFeature.RENDER, new ThreadGameRender(hub));
			
		}
		
		//this.threads.put(EnumEngineFeature.SOUND, new ThreadSoundPlayer());
		
		IPhysicsScene ph = this.game.getPhysicsScene();
		
		if (ph != null)
		{
			this.threads.put(EnumEngineFeature.PHYSICS, new ThreadPhysics(ph, this.game.getUpdateCount()));
			
		}
		
		for (EnumEngineFeature fe : EnumEngineFeature.values())
		{
			ThreadStoppable t = this.threads.get(fe);
			
			if (t != null)
			{
				t.start();
				
			}
			
		}
		
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
		
		if (!this.game.onGameShutdown())
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
	
	public IGame getCurrentGame()
	{
		return this.game;
	}
	
	public IGameEnvironment getEnvironment()
	{
		return this.environment;
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
