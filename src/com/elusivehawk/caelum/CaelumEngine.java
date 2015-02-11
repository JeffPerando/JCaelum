
package com.elusivehawk.caelum;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.elusivehawk.caelum.assets.AssetManager;
import com.elusivehawk.caelum.input.InputManager;
import com.elusivehawk.caelum.input.Keyboard;
import com.elusivehawk.caelum.input.Mouse;
import com.elusivehawk.caelum.render.IRenderable;
import com.elusivehawk.caelum.render.ThreadGameRender;
import com.elusivehawk.util.CompInfo;
import com.elusivehawk.util.EnumLogType;
import com.elusivehawk.util.HashGen;
import com.elusivehawk.util.IPausable;
import com.elusivehawk.util.Internal;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.ReflectionHelper;
import com.elusivehawk.util.ShutdownHelper;
import com.elusivehawk.util.Version;
import com.elusivehawk.util.concurrent.ThreadStoppable;
import com.elusivehawk.util.io.FileHelper;
import com.elusivehawk.util.io.IOHelper;
import com.elusivehawk.util.parse.ParseHelper;
import com.elusivehawk.util.parse.json.JsonObject;
import com.elusivehawk.util.parse.json.JsonParseException;
import com.elusivehawk.util.parse.json.JsonParser;
import com.elusivehawk.util.parse.json.JsonValue;
import com.elusivehawk.util.storage.Pair;
import com.elusivehawk.util.storage.Tuple;
import com.elusivehawk.util.task.TaskManager;
import com.google.common.collect.ImmutableList;
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
	
	public static final Version VERSION = new Version(Version.ALPHA, 1, 0, 0, 0);
	
	private final Map<EnumEngineFeature, ThreadStoppable> threads = Maps.newEnumMap(EnumEngineFeature.class);
	private final TaskManager tasks = new TaskManager();
	private final AssetManager assets = new AssetManager();
	
	private final Map<String, String> startargs = Maps.newHashMap();
	private final List<String> startupPrefixes = Arrays.asList("env:", "gamefac:", "verbose:");
	
	private File nativeLocation = null;
	private IGameEnvironment env = null;
	private JsonObject envConfig = null;
	
	private IGameFactory factory = null;
	private Game game = null;
	private Display display = null;
	private DisplayManager displays = null;
	
	private CaelumEngine(){}
	
	public static CaelumEngine instance()
	{
		return INSTANCE;
	}
	
	public static Game game()
	{
		return instance().game;
	}
	
	public static AssetManager assets()
	{
		return instance().assets;
	}
	
	public static TaskManager tasks()
	{
		return instance().tasks;
	}
	
	public static File getNativeLocation()
	{
		return instance().nativeLocation;
	}
	
	public static boolean isPaused()
	{
		Thread t = Thread.currentThread();
		
		if (t instanceof IPausable)
		{
			return ((IPausable)t).isPaused();
		}
		
		return false;
	}
	
	//XXX Hooks
	
	public static Display createDisplay(String name, DisplaySettings settings, IRenderable renderer)
	{
		return instance().displays.createDisplay(name, settings, renderer);
	}
	
	public static Display defaultDisplay()
	{
		return instance().display;
	}
	
	public static Display getDisplay(String name)
	{
		return instance().displays.getDisplay(name);
	}
	
	//End hooks
	
	@Internal
	public static void flipScreen(boolean flip)
	{
		/*if (instance().rcon != null)
		{
			instance().rcon.onScreenFlipped(flip);
			
		}*/
		
		if (game() != null)
		{
			game().onScreenFlipped(flip);
			
		}
		
	}
	
	@Internal
	public static void main(String... args)
	{
		Runtime.getRuntime().addShutdownHook(new Thread((() ->
		{
			CaelumEngine.instance().shutDownGame();
			CaelumEngine.instance().clearGameEnv();
			
		})));
		
		instance().createGameEnv(args);
		instance().startGame();
		instance().pauseGame(false);
		
	}
	
	/**
	 * 
	 * ONLY FOR DEBUGGING AND SMALL GAMES!
	 * 
	 * @param gamefac
	 * @param args
	 */
	public static void start(IGameFactory gamefac, String... args)
	{
		if (instance().factory != null)
		{
			throw new CaelumException("Nuh uh uh, you didn't say the magic word...");
		}
		
		instance().factory = gamefac;
		
		main(args);
		
	}
	
	@Internal
	public void createGameEnv(String... args)
	{
		if (this.env != null)
		{
			return;
		}
		
		//XXX Parse the starting arguments
		
		List<String> gargs = Lists.newArrayList();
		Map<String, String> strs = Maps.newHashMap();
		Pair<String> spl;
		
		for (String str : args)
		{
			boolean testForGameArg = true;
			
			for (String prefix : this.startupPrefixes)
			{
				if (str.startsWith(prefix))
				{
					spl = ParseHelper.splitFirst(str, ":");
					strs.put(spl.one, spl.two);
					testForGameArg = false;
					
					break;
				}
				
			}
			
			if (testForGameArg)
			{
				gargs.add(str);
				
			}
			
		}
		
		this.startargs.putAll(strs);
		
		Logger.info("Starting Caelum Engine %s on %s.", VERSION, CompInfo.OS);
		
		boolean verbose = !"false".equalsIgnoreCase(this.startargs.get("verbose"));
		
		Logger.setEnableVerbosity(verbose);
		
		Logger.info("Verbosity is set to \'%s\'", verbose);
		
		/*if (DEBUG)
		{
			for (Entry<Object, Object> entry : System.getProperties().entrySet())
			{
				this.log.log(EnumLogType.DEBUG, "Argument: \"%s\": \'%s\'", entry.getKey(), entry.getValue());
				
			}
			
		}*/
		
		//XXX Load game environment
		
		if (this.env == null)
		{
			IGameEnvironment gameenv = null;
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
				Logger.verbose("Loading default game environment.");
				
				try
				{
					switch (CompInfo.OS)
					{
						case WINDOWS:
						case MAC:
						case LINUX: clazz = Class.forName("com.elusivehawk.caelum.lwjgl.LWJGLEnvironment"); break;
						case ANDROID: clazz = Class.forName("com.elusivehawk.caelum.android.AndroidEnvironment"); break;
						default: Logger.wtf("Unsupported OS! Enum: %s; OS: %s", CompInfo.OS, System.getProperty("os.name"));
						
					}
					
				}
				catch (Exception e){}
				
			}
			else
			{
				Logger.warn("Loading custom game environment, this is gonna suck...");
				
			}
			
			gameenv = (IGameEnvironment)ReflectionHelper.newInstance(clazz, new Class[]{IGameEnvironment.class}, null);
			
			if (gameenv == null)
			{
				Logger.log(EnumLogType.ERROR, "Unable to load environment: Instance couldn't be created. Class: %s", clazz == null ? "NULL" : clazz.getCanonicalName());
				ShutdownHelper.exit("NO-ENVIRONMENT-FOUND");
				
				return;
			}
			
			if (!gameenv.isCompatible(CompInfo.OS))
			{
				Logger.log(EnumLogType.ERROR, "Unable to load environment: Current OS is incompatible. Class: %s; OS: %s", clazz == null ? "NULL" : clazz.getCanonicalName(), CompInfo.OS);
				ShutdownHelper.exit("NO-ENVIRONMENT-FOUND");
				
				return;
			}
			
			this.env = gameenv;
			
		}
		
		//XXX Pre-initiate game environment
		
		this.env.preInit();
		
		//XXX Load natives
		
		this.loadNatives();
		
		//XXX Initiate game environment
		
		this.env.initiate(this.envConfig, args);
		
		//XXX Load display system
		
		this.displays = new DisplayManager(this.env);
		
		//XXX Load game factory
		
		if (this.factory == null)
		{
			String gamefac = this.startargs.get("gamefac");
			
			if (gamefac == null || gamefac.equals(""))
			{
				Logger.wtf("Cannot make game factory: \"gamefac\" argument not provided!");
				
			}
			else
			{
				this.factory = (IGameFactory)ReflectionHelper.newInstance(gamefac, new Class<?>[]{IGameFactory.class}, null);
				
			}
			
		}
		
	}
	
	@Internal
	public void startGame()
	{
		//XXX Create the game itself
		
		if (this.factory == null)
		{
			Logger.log(EnumLogType.ERROR, "Game factory not found: Factory not provided");
			ShutdownHelper.exit("NO-FACTORY-FOUND");
			
			return;
		}
		
		Game g = this.factory.createGame();
		
		if (g == null)
		{
			Logger.log(EnumLogType.ERROR, "Could not load game");
			ShutdownHelper.exit("NO-GAME-FOUND");
			
			return;
		}
		
		this.tasks.start();
		
		g.preInit();
		
		Logger.info("Loading %s", g);
		
		if (g.getGameVersion() == null)
		{
			Logger.warn("The game is missing a Version object!");
			
		}
		
		this.game = g;
		
		//XXX Create display
		
		if (!this.game.isGameHeadless())
		{
			DisplaySettings settings = this.game.getDisplaySettings();
			
			if (settings == null)
			{
				settings = new DisplaySettings();
				
			}
			
			this.display = createDisplay("default", settings, g);
			
			InputManager input = this.display.getInput();
			
			input.addInput(new Keyboard(this.display));
			input.addInput(new Mouse(this.display));
			
			input.addListener(Keyboard.class, g);
			input.addListener(Mouse.class, g);
			
		}
		
		//XXX Initiate game
		
		try
		{
			g.initiate(this.display);
			
		}
		catch (Throwable e)
		{
			Logger.err("Game failed to load!", e);
			ShutdownHelper.exit("GAME-LOAD-FAILURE");
			
			return;
		}
		
		//XXX Create game threads
		
		this.threads.put(EnumEngineFeature.LOGIC, new ThreadGameLoop(this.game, this.displays));
		
		if (!this.game.isGameHeadless())
		{
			this.threads.put(EnumEngineFeature.RENDER, new ThreadGameRender(this.displays, this.game.getUpdateCount()));
			
		}
		
		/*this.threads.put(EnumEngineFeature.SOUND, new ThreadSoundPlayer());
		
		IPhysicsSimulator ph = this.game.getPhysicsSimulator();
		
		if (ph != null)
		{
			this.threads.put(EnumEngineFeature.PHYSICS, new ThreadPhysics(ph, this.game.getUpdateCount()));
			
		}*/
		
		//XXX Start game threads
		
		ThreadStoppable t;
		
		for (EnumEngineFeature fe : EnumEngineFeature.values())
		{
			t = this.threads.get(fe);
			
			if (t == null)
			{
				continue;
			}
			
			if (t.isAlive())
			{
				continue;
			}
			
			t.setPaused(true);
			t.start();
			
		}
		
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
	
	@Internal
	public void shutDownGame()
	{
		if (this.threads.isEmpty())
		{
			return;
		}
		
		if (this.game != null)
		{
			this.game.onShutdown();
			this.game = null;
			
		}
		
		for (EnumEngineFeature fe : EnumEngineFeature.values())
		{
			ThreadStoppable t = this.threads.get(fe);
			
			if (t != null)
			{
				t.stopThread();
				
			}
			
		}
		
		this.tasks.stop();
		this.threads.clear();
		
	}
	
	@Internal
	public void clearGameEnv()
	{
		if (this.game != null)
		{
			throw new CaelumException("YOU CLUMSY POOP!");
		}
		
		this.startargs.clear();
		
		this.env = null;
		this.envConfig = null;
		this.displays = null;
		
	}
	
	@Internal
	private void loadNatives()
	{
		if (this.nativeLocation != null)
		{
			return;
		}
		
		ImmutableList<String> natives = this.env.getNatives();
		
		if (natives == null || natives.isEmpty())
		{
			return;
		}
		
		File tmp = FileHelper.createFile(CompInfo.TMP_DIR, String.format(".caelum/%s/natives/%s/%s", VERSION.formatted, this.env.getName(), CompInfo.BUILT ? "" : "dev/"));
		
		if (!tmp.exists() && !tmp.mkdirs())
		{
			throw new CaelumException("Could not load natives: Unable to create natives directory");
		}
		
		if (CompInfo.BUILT)
		{
			IOHelper.readZip(CompInfo.JAR_DIR, ((zip, entry, name) ->
			{
				for (String n : natives)
				{
					if (name.endsWith(n))
					{
						try
						{
							copyNative(zip.getInputStream(entry), new File(tmp, name.contains("/") ? ParseHelper.getSuffix(name, "/") : name));
							
						}
						catch (Exception e)
						{
							Logger.err(e);
							
						}
						
					}
					
				}
				
			}));
			
		}
		else
		{
			File nLoc = FileHelper.getChild("lib", CompInfo.JAR_DIR.getParentFile());
			
			if (nLoc == null)
			{
				Logger.wtf("\"Lib\" folder is missing! Expect JNI crashing!");
				
				return;
			}
			
			List<File> nativeFiles = FileHelper.getFiles(nLoc, ((file) ->
			{
				for (String n : natives)
				{
					if (file.getPath().endsWith(n))
					{
						return true;
					}
					
				}
				
				return false;
			}));
			
			if (nativeFiles.isEmpty())
			{
				throw new CaelumException("Could not load natives! THIS IS A BUG! Native directory: %s", nLoc.getAbsolutePath());
			}
			
			for (File n : nativeFiles)
			{
				copyNative(FileHelper.createInStream(n), new File(tmp, n.getName()));
				
			}
			
		}
		
		this.nativeLocation = tmp;
		
	}
	
	@Internal
	private static void copyNative(InputStream is, File dest)
	{
		byte[] bytes = IOHelper.readBytes(is, false);
		
		if (bytes.length == 0)
		{
			return;
		}
		
		String name = dest.getName();
		
		if (!dest.exists())
		{
			try
			{
				if (!dest.createNewFile())
				{
					return;
				}
				
			}
			catch (Exception e)
			{
				Logger.err(e);
				
			}
			
		}
		
		byte[] hash = HashGen.sha256(bytes);
		
		if (hash == null || hash.length == 0)
		{
			Logger.log(EnumLogType.ERROR, "Could not generate checksum for \"%s\"; THIS IS A BUG!", name);
			
			return;
		}
		
		byte[] oldHash = HashGen.sha256(IOHelper.readBytes(dest));
		
		if (Arrays.equals(hash, oldHash))
		{
			if (CompInfo.DEBUG)
			{
				Logger.verbose("Not copying file \"%s\", checksum matched", name);
				
			}
			
			return;
		}
		
		Logger.warn("Checksum for \"%s\" did not match!", name);
		
		if (IOHelper.write(bytes, dest))
		{
			if (CompInfo.DEBUG)
			{
				Logger.verbose("Succesfully copied native: \"%s\"", dest.getName());
				
			}
			
		}
		else
		{
			Logger.warn("Could not copy native: \"%s\"", dest.getName());
			
		}
		
	}
	
	@Internal
	private Class<?> loadEnvironmentFromJson()
	{
		File jsonFile = FileHelper.createFile(".", "gameEnv.json");
		
		if (!FileHelper.isReal(jsonFile))
		{
			return null;
		}
		
		JsonValue<?> j = null;
		
		try
		{
			j = JsonParser.parse(jsonFile);
			
		}
		catch (JsonParseException e)
		{
			Logger.err(e);
			
		}
		
		if (j == null)
		{
			return null;
		}
		
		if (!(j instanceof JsonObject))
		{
			return null;
		}
		
		JsonObject json = (JsonObject)j;
		
		Object curEnv = json.getValue(CompInfo.OS.toString());
		
		if (curEnv == null || (!(curEnv instanceof JsonObject)))
		{
			return null;
		}
		
		this.envConfig = (JsonObject)curEnv;
		Object envLoc = this.envConfig.getValue("lib");
		
		if (envLoc == null || (!(envLoc instanceof String)))
		{
			return null;
		}
		
		File envLibFile = FileHelper.createFile((String)envLoc);
		
		if (!FileHelper.canRead(envLibFile) || !envLibFile.getName().endsWith(".jar"))
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
