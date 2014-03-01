
package com.elusivehawk.engine.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.elusivehawk.engine.physics.IPhysicsScene;
import com.elusivehawk.engine.physics.ThreadPhysics;
import com.elusivehawk.engine.render.IRenderEnvironment;
import com.elusivehawk.engine.render.IRenderHUB;
import com.elusivehawk.engine.render.ThreadGameRender;
import com.elusivehawk.engine.util.Buffer;
import com.elusivehawk.engine.util.FileHelper;
import com.elusivehawk.engine.util.ReflectionHelper;
import com.elusivehawk.engine.util.TextParser;
import com.elusivehawk.engine.util.ThreadStoppable;
import com.elusivehawk.engine.util.Tuple;
import com.google.common.collect.Maps;

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
	private Game game;
	private AssetManager assets;
	private IGameEnvironment env;
	private IRenderEnvironment renv;
	private ILog log = new GameLog();
	private Map<EnumInputType, Input> inputs = Maps.newHashMap();
	
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
		
		instance().start(buf);
		
	}
	
	private void start(Buffer<String> args)
	{
		String cur = (args.hasNext() ? args.next() : "");
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
		
		cur = args.next();
		
		env.initiate(json, args.toArray());
		
		this.env = env;
		this.renv = env.getRenderEnv();
		
		List<Input> inputList = env.loadInputs();
		
		if (inputList == null || inputList.isEmpty())
		{
			this.log.log(EnumLogType.WARN, "Unable to load input.");
			
		}
		else
		{
			for (Input input : inputList)
			{
				this.inputs.put(input.getType(), input);
				
			}
			
		}
		
		Game g = null;
		
		if (cur.startsWith("game:"))
		{
			g = (Game)ReflectionHelper.newInstance(TextParser.splitOnce(cur, "game:")[1], new Class<?>[]{Game.class}, null);
			
		}
		
		if (g == null)
		{
			System.err.println("Could not load game.");
			System.exit("NO-GAME-FOUND".hashCode());
			
		}
		
		ILog log = this.env.getLog();
		
		if (log != null)
		{
			this.log = log;
			
		}
		
		if (this.game != null)
		{
			return;
		}
		
		this.game = g;
		
		this.assets = new AssetManager();
		
		if (!g.initiate(args, this.assets))
		{
			return;
		}
		
		this.assets.initiate();
		
		this.threads.put(EnumEngineFeature.ASSET_LOADING, new ThreadAssetLoader(this.assets));
		
		this.threads.put(EnumEngineFeature.LOGIC, new ThreadGameLoop(this.inputs, this.game));
		
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
		
		if (!this.game.canGameShutDown())
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
	
	public static Game game()
	{
		return instance().game;
	}
	
	public static AssetManager assetManager()
	{
		ThreadStoppable thr = instance().threads.get(EnumEngineFeature.ASSET_LOADING);
		
		if (thr == null)
		{
			return null;
		}
		
		if (thr.isRunning())
		{
			return null;
		}
		
		return instance().assets;
	}
	
	public static IGameEnvironment environment()
	{
		return instance().env;
	}
	
	public static IRenderEnvironment renderEnvironment()
	{
		return instance().renv;
	}
	
	public static ILog log()
	{
		return instance().log;
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
