
package com.elusivehawk.engine.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.elusivehawk.engine.physics.IPhysicsScene;
import com.elusivehawk.engine.physics.ThreadPhysics;
import com.elusivehawk.engine.render.IRenderEnvironment;
import com.elusivehawk.engine.render.IRenderHUB;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.ThreadGameRender;
import com.elusivehawk.engine.util.Buffer;
import com.elusivehawk.engine.util.FileHelper;
import com.elusivehawk.engine.util.ReflectionHelper;
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
	
	private final Map<EnumEngineFeature, ThreadStoppable> threads = Maps.newHashMap();
	private final Map<EnumInputType, Input> inputs = Maps.newHashMap();
	
	private ILog log = new GameLog();
	private IGameEnvironment env = null;
	private IRenderEnvironment renv = null;
	
	private Game game = null;
	private AssetManager assets = null;
	
	private CaelumEngine(){}
	
	public static CaelumEngine instance()
	{
		return INSTANCE;
	}
	
	public static Game game()
	{
		return instance().game;
	}
	
	public static AssetManager assetManager()
	{
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
	
	public static IContext getContext(boolean safe)
	{
		Thread t = Thread.currentThread();
		
		if (safe && !(t instanceof IThreadContext))
		{
			return null;
		}
		
		return ((IThreadContext)t).getContext();
	}
	
	public static RenderContext renderContext()
	{
		return renderContext(false);
	}
	
	public static RenderContext renderContext(boolean safe)
	{
		return (RenderContext)getContext(safe);
	}
	
	public static void main(String... args)
	{
		instance().start(args);
		
	}
	
	private void start(String... args)
	{
		if (this.game != null)
		{
			return;
		}
		
		this.log.log(EnumLogType.INFO, String.format("Starting Caelum Engine v%s", VERSION));
		
		Buffer<String> buf = new Buffer<String>(args);
		String cur = (buf.hasNext() ? buf.next() : "");
		IGameEnvironment env = null;
		JsonObject json = null;
		Class<?> clazz = null;
		
		if (cur.startsWith("class:"))
		{
			try
			{
				clazz = Class.forName(cur.substring(6));
				
			}
			catch (Exception e){}
			
		}
		else
		{
			File file = FileHelper.createFile(".", "/game.json");
			
			if (file.exists() && file.canRead())
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
										if (c.isAssignableFrom(IGameEnvironment.class))
										{
											clazz = c;
											
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
		
		if (clazz != null)
		{
			try
			{
				env = (IGameEnvironment)clazz.newInstance();
				
			}
			catch (Exception e){}
			
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
		
		env.initiate(json, buf.toArray());
		
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
		
		ILog l = this.env.getLog();
		
		if (l != null)
		{
			this.log = l;
			
		}
		
		cur = buf.hasNext() ? buf.next() : "";
		Game g = null;
		
		if (!cur.startsWith("game:"))
		{
			l.log(EnumLogType.ERROR, "Could not load game.");
			System.exit("NO-GAME-FOUND".hashCode());
			
			return;
		}
		
		g = (Game)ReflectionHelper.newInstance(cur.substring(5), new Class<?>[]{Game.class}, null);
		
		if (!g.initiate(new GameArguments(buf)))
		{
			return;
		}
		
		this.game = g;
		
		ThreadAssetLoader al = new ThreadAssetLoader();
		this.assets = new AssetManager(al);
		
		g.loadAssets(this.assets);
		this.assets.initiate();
		
		this.threads.put(EnumEngineFeature.ASSET_LOADING, al);
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
		
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@SuppressWarnings("synthetic-access")
			@Override
			public void run()
			{
				CaelumEngine.instance().shutDownGame();
				
			}
			
		});
		
		for (EnumEngineFeature fe : EnumEngineFeature.values())
		{
			ThreadStoppable t = this.threads.get(fe);
			
			if (t != null)
			{
				t.start();
				
			}
			
		}
		
	}
	
	private void shutDownGame()
	{
		if (this.game == null)
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
		
		this.inputs.clear();
		
		this.game.onShutdown();
		this.game = null;
		
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
