
package com.elusivehawk.engine.core;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.elusivehawk.engine.assets.AssetManager;
import com.elusivehawk.engine.render.IRenderEnvironment;
import com.elusivehawk.engine.render.IRenderHUB;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.RenderSystem;
import com.elusivehawk.engine.render.ThreadGameRender;
import com.elusivehawk.util.EnumOS;
import com.elusivehawk.util.FileHelper;
import com.elusivehawk.util.IPausable;
import com.elusivehawk.util.ReflectionHelper;
import com.elusivehawk.util.ShutdownHelper;
import com.elusivehawk.util.StringHelper;
import com.elusivehawk.util.Version;
import com.elusivehawk.util.concurrent.IThreadStoppable;
import com.elusivehawk.util.json.EnumJsonType;
import com.elusivehawk.util.json.JsonKeypair;
import com.elusivehawk.util.json.JsonObject;
import com.elusivehawk.util.json.JsonParser;
import com.elusivehawk.util.storage.Tuple;
import com.elusivehawk.util.task.TaskManager;
import com.google.common.collect.Lists;
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
	public static final Version VERSION = new Version(1, 0, 0);
	
	private final Map<EnumEngineFeature, IThreadStoppable> threads = Maps.newEnumMap(EnumEngineFeature.class);
	private final Map<EnumInputType, Input> inputs = Maps.newEnumMap(EnumInputType.class);
	private final Map<String, String> startargs = Maps.newHashMap();
	private final TaskManager tasks = new TaskManager();
	
	private ILog log = new GameLog();
	private IGameEnvironment env = null;
	private IRenderEnvironment renv = null;
	private JsonObject envConfig = null;
	
	private Game game = null;
	private GameArguments gameargs = null;
	private AssetManager assets = null;
	private RenderSystem rsys = null;
	
	private CaelumEngine()
	{
		if (EnumOS.getCurrentOS() != EnumOS.ANDROID)
		{
			Runtime.getRuntime().addShutdownHook(new Thread(() ->
			{
				CaelumEngine.instance().shutDownGame();
				CaelumEngine.instance().clearGameEnv();
				
			}));
			
		}
		
	}
	
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
	
	public static ILog log()
	{
		return instance().log;
	}
	
	public static TaskManager tasks()
	{
		return instance().tasks;
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
		return renderContext(true);
	}
	
	public static RenderContext renderContext(boolean safe)
	{
		return (RenderContext)getContext(safe);
	}
	
	public static boolean isPaused()
	{
		Thread t = Thread.currentThread();
		
		if (!(t instanceof IPausable))
		{
			return false;
		}
		
		return ((IPausable)t).isPaused();
	}
	
	public static void flipScreen(boolean flip)
	{
		if (instance().rsys != null)
		{
			instance().rsys.onScreenFlipped(flip);
			
		}
		
		if (game() != null)
		{
			game().onScreenFlipped(flip);
			
		}
		
	}
	
	public static void main(String... args)
	{
		instance().createGameEnv(args);
		instance().startGame();
		instance().pauseGame(false);
		
	}
	
	public void createGameEnv(String... args)
	{
		if (this.game != null)
		{
			return;
		}
		
		//XXX Parsing the starting arguments
		
		List<String> gargs = Lists.newArrayList();
		Map<String, String> strs = Maps.newHashMap();
		String[] spl;
		
		for (String str : args)
		{
			if (str.indexOf(":") == -1)
			{
				gargs.add(str);
				
			}
			else
			{
				spl = StringHelper.splitOnce(str, ":");
				strs.put(spl[0], spl[1]);
				
			}
			
		}
		
		this.startargs.putAll(strs);
		this.gameargs = new GameArguments(gargs);
		
		this.log.log(EnumLogType.INFO, String.format("Starting Caelum Engine v%s on %s", VERSION, EnumOS.getCurrentOS()));
		
		boolean verbose = !"false".equalsIgnoreCase(this.startargs.get("verbose"));
		
		this.log.setEnableVerbosity(verbose);
		
		if (DEBUG)
		{
			this.log.log(EnumLogType.WARN, "Debugging is turned on!");
			
		}
		
		//XXX Loading game environment
		
		if (this.env == null)
		{
			IGameEnvironment env = null;
			Class<?> clazz = null;
			String cl = this.startargs.get("env");
			
			if (cl == null)
			{
				clazz = this.loadEnvironmentFromJson();
				
			}
			else
			{
				try
				{
					clazz = Class.forName(cl);
					
				}
				catch (Exception e){}
				
			}
			
			if (clazz == null)
			{
				this.log.log(EnumLogType.VERBOSE, "Loading default game environment");
				
				try
				{
					switch (EnumOS.getCurrentOS())
					{
						case WINDOWS:
						case MAC:
						case LINUX: clazz = Class.forName("com.elusivehawk.engine.lwjgl.LWJGLEnvironment"); break;
						case ANDROID: clazz = Class.forName("com.elusivehawk.engine.android.AndroidEnvironment"); break;
						default: this.log.log(EnumLogType.WTF, String.format("Unsupported OS! Enum: %s; OS: %s", EnumOS.getCurrentOS(), System.getProperty("os.name")));
						
					}
					
				}
				catch (Exception e){}
				
			}
			else
			{
				this.log.log(EnumLogType.WARN, "Loading custom game environment, this is gonna suck...");
				
			}
			
			env = (IGameEnvironment)ReflectionHelper.newInstance(clazz, new Class[]{IGameEnvironment.class}, null);
			
			if (env == null)
			{
				this.log.log(EnumLogType.ERROR, String.format("Unable to load environment: Instance couldn't be created. Class: %s", clazz == null ? "NULL" : clazz.getCanonicalName()));
				ShutdownHelper.exit("NO-ENVIRONMENT-FOUND".hashCode());
				
			}
			
			if (!env.isCompatible(EnumOS.getCurrentOS()))
			{
				this.log.log(EnumLogType.ERROR, String.format("Unable to load environment: Current OS is incompatible. Class: %s; OS: %s", clazz == null ? "NULL" : clazz.getCanonicalName(), EnumOS.getCurrentOS()));
				ShutdownHelper.exit("NO-ENVIRONMENT-FOUND".hashCode());
				
			}
			
			env.initiate(this.envConfig, args);
			
			this.env = env;
			this.renv = env.getRenderEnv();
			
			ILog l = this.env.getLog();
			
			if (l != null)
			{
				this.log = l;
				
				l.setEnableVerbosity(verbose);
				
			}
			
		}
		
		//XXX Loading input
		
		if (this.inputs.isEmpty())
		{
			List<Input> inputList = this.env.loadInputs();
			
			if (inputList == null || inputList.isEmpty())
			{
				this.log.log(EnumLogType.WARN, "Unable to load input");
				
			}
			else
			{
				for (Input input : inputList)
				{
					this.inputs.put(input.getType(), input);
					
					this.log.log(EnumLogType.VERBOSE, String.format("Loaded input of type %s, with class %s", input.getType().name(), input.getClass().getCanonicalName()));
					
				}
				
			}
			
		}
		
	}
	
	public void startGame()
	{
		//XXX Loading the game
		
		Game g = null;
		
		String game = this.startargs.get("game");
		
		if (game != null)
		{
			g = (Game)ReflectionHelper.newInstance(game, new Class<?>[]{Game.class}, null);
			
		}
		
		if (g == null)
		{
			this.log.log(EnumLogType.ERROR, "Could not load game");
			ShutdownHelper.exit("NO-GAME-FOUND".hashCode());
			
		}
		else
		{
			g.preInit();
			
		}
		
		this.log.log(EnumLogType.INFO, String.format("Loading %s", g));
		
		if (g.getGameVersion() == null)
		{
			this.log.log(EnumLogType.WARN, "The game is missing a Version object!");
			
		}
		
		this.game = g;
		
		try
		{
			g.initiateGame(this.gameargs);
			
		}
		catch (Throwable e)
		{
			this.log.log(EnumLogType.ERROR, "Game failed to load!", e);
			
			ShutdownHelper.exit("GAME-LOAD-FAILURE".hashCode());
			
		}
		
		//XXX Creating game threads
		
		this.assets = new AssetManager();
		
		this.game.loadAssets(this.assets);
		this.assets.initiate();
		
		this.threads.put(EnumEngineFeature.LOGIC, new ThreadGameLoop(this.inputs, this.game));
		
		IRenderHUB hub = this.game.getRenderHUB();
		
		if (hub != null)
		{
			this.rsys = new RenderSystem(this.renv, hub);
			
			IThreadStoppable rt = this.renv.createRenderThread(this.rsys);
			
			if (rt == null)
			{
				rt = new ThreadGameRender(this.rsys);
				
			}
			
			this.threads.put(EnumEngineFeature.RENDER, rt);
			
		}
		
		//this.threads.put(EnumEngineFeature.SOUND, new ThreadSoundPlayer());
		
		/*IPhysicsSimulator ph = this.game.getPhysicsSimulator();
		
		if (ph != null)
		{
			this.threads.put(EnumEngineFeature.PHYSICS, new ThreadPhysics(ph, this.game.getUpdateCount()));
			
		}*/
		
		//XXX Starting game threads
		
		for (EnumEngineFeature fe : EnumEngineFeature.values())
		{
			IThreadStoppable t = this.threads.get(fe);
			
			if (t != null)
			{
				t.setPaused(true);
				((Thread)t).start();
				
			}
			
		}
		
		this.tasks.start();
		
	}
	
	public void pauseGame(boolean pause)
	{
		this.pauseGame(pause, EnumEngineFeature.values());
		
	}
	
	public void pauseGame(boolean pause, EnumEngineFeature... features)
	{
		for (EnumEngineFeature fe : features)
		{
			IThreadStoppable t = this.threads.get(fe);
			
			if (t != null)
			{
				t.setPaused(pause);
				
			}
			
		}
		
	}
	
	public void shutDownGame()
	{
		if (this.threads.isEmpty())
		{
			return;
		}
		
		this.rsys = null;
		
		if (this.game != null)
		{
			this.game.onShutdown();
			this.game = null;
			
		}
		
		for (EnumEngineFeature fe : EnumEngineFeature.values())
		{
			IThreadStoppable t = this.threads.get(fe);
			
			if (t != null)
			{
				t.stopThread();
				
			}
			
		}
		
		this.tasks.stop();
		
		this.threads.clear();
		
	}
	
	public void clearGameEnv()
	{
		if (this.game != null)
		{
			return;
		}
		
		this.inputs.clear();
		this.startargs.clear();
		
		this.env = null;
		this.renv = null;
		this.envConfig = null;
		this.log = new GameLog();
		
		this.gameargs = null;
		this.assets = null;
		
	}
	
	private Class<?> loadEnvironmentFromJson()
	{
		JsonObject j = JsonParser.parse(FileHelper.createFile(".", "/gameEnv.json"));
		
		if (j == null)
		{
			return null;
		}
		
		JsonKeypair curEnv = j.getValue(EnumOS.getCurrentOS().toString());
		
		if (curEnv == null || curEnv.type != EnumJsonType.OBJECT)
		{
			return null;
		}
		
		this.envConfig = (JsonObject)curEnv;
		JsonKeypair envLoc = this.envConfig.getValue("lib");
		
		if (envLoc == null || envLoc.type != EnumJsonType.STRING)
		{
			return null;
		}
		
		File envLibFile = FileHelper.createFile(envLoc.value);
		
		if (!FileHelper.canReadFile(envLibFile) || !envLibFile.getName().endsWith(".jar"))
		{
			return null;
		}
		
		Tuple<ClassLoader, Set<Class<?>>> tuple = ReflectionHelper.loadLibrary(envLibFile);
		Set<Class<?>> set = tuple.two;
		
		if (set == null || set.isEmpty())
		{
			return null;
		}
		
		for (Class<?> c : set)
		{
			if (IGameEnvironment.class.isAssignableFrom(c))
			{
				return c;
			}
			
		}
		
		return null;
	}
	
}
