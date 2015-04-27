
package com.elusivehawk.caelum;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import com.elusivehawk.caelum.assets.AssetManager;
import com.elusivehawk.caelum.input.InputManager;
import com.elusivehawk.caelum.input.Keyboard;
import com.elusivehawk.caelum.input.Mouse;
import com.elusivehawk.caelum.render.ThreadGameRender;
import com.elusivehawk.caelum.window.IWindowListener;
import com.elusivehawk.caelum.window.Window;
import com.elusivehawk.caelum.window.WindowManager;
import com.elusivehawk.caelum.window.WindowSettings;
import com.elusivehawk.util.CompInfo;
import com.elusivehawk.util.EnumLogType;
import com.elusivehawk.util.HashGen;
import com.elusivehawk.util.IPausable;
import com.elusivehawk.util.Internal;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.ShutdownHelper;
import com.elusivehawk.util.Version;
import com.elusivehawk.util.concurrent.ThreadStoppable;
import com.elusivehawk.util.io.FileHelper;
import com.elusivehawk.util.io.IOHelper;
import com.elusivehawk.util.parse.ParseHelper;
import com.elusivehawk.util.task.TaskManager;
import com.google.common.collect.ImmutableList;
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
	
	private File natives = null;
	
	private Game game = null;
	private Window window = null;
	private WindowManager windows = null;
	
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
		return instance().natives;
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
	
	public static Window createWindow(String name, IWindowListener winlis)
	{
		return instance().windows.createWindow(name, winlis);
	}
	
	public static Window createWindow(String name, IWindowListener winlis, WindowSettings settings)
	{
		return instance().windows.createWindow(name, winlis, settings);
	}
	
	public static Window defaultWindow()
	{
		return instance().window;
	}
	
	public static Window getWindow(String name)
	{
		return instance().windows.getWindow(name);
	}
	
	//End hooks
	
	public static void start(Game g, String... args)
	{
		instance().start0(g, args);
		
	}
	
	@Internal
	private void start0(Game g, String... args)
	{
		if (g == null)
		{
			Logger.log(EnumLogType.ERROR, "Could not load game: Game not created by game factory.");
			ShutdownHelper.exit("NO-GAME-FOUND");
			
			return;
		}
		
		Logger.info("Starting Caelum Engine %s on %s.", VERSION, CompInfo.OS);
		
		Logger.info("Verbosity is set to \'%s\'", Logger.enableVerbosity());
		
		/*if (DEBUG)
		{
			for (Entry<Object, Object> entry : System.getProperties().entrySet())
			{
				this.log.log(EnumLogType.DEBUG, "Argument: \"%s\": \'%s\'", entry.getKey(), entry.getValue());
				
			}
			
		}*/
		
		//XXX Load game environment
		
		//XXX Load natives
		
		this.loadNatives();
		
		Logger.debug("Test: %s", this.natives.getAbsolutePath());
		
		//XXX Set library paths
		
		System.setProperty("java.library.path", this.natives.getAbsolutePath());
		
		//XXX Load display system
		
		this.windows = new WindowManager();
		
		//XXX Start tasks
		
		this.tasks.start();
		
		//XXX Pre-init game
		
		this.game = g;
		
		g.preInit();
		
		Logger.info("Loading %s", g);
		
		//XXX Create display
		
		if (!this.game.isGameHeadless())
		{
			this.window = createWindow("default", g);
			
			InputManager input = this.window.getInput();
			
			input.addInput(Keyboard.class);
			input.addInput(Mouse.class);
			
		}
		
		//XXX Initiate game
		
		try
		{
			g.initiate();
			
		}
		catch (Throwable e)
		{
			Logger.err("Game failed to load!", e);
			ShutdownHelper.exit("GAME-LOAD-FAILURE");
			
			return;
		}
		
		//XXX Create game threads
		
		this.threads.put(EnumEngineFeature.LOGIC, new ThreadGameLoop(this.game, this.windows));
		
		if (!this.game.isGameHeadless())
		{
			this.threads.put(EnumEngineFeature.RENDER, new ThreadGameRender(this.windows, this.game.getUpdateCount()));
			
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
			
			t.start();
			
		}
		
	}
	
	public static void kill()
	{
		instance().kill0();
		
	}
	
	@Internal
	private void kill0()
	{
		if (this.threads.isEmpty())
		{
			return;
		}
		
		if (this.game != null)
		{
			this.game.dispose();
			this.game = null;
			
		}
		
		try
		{
			Thread.sleep(2L);
			
		}
		catch (InterruptedException e)
		{
			Logger.err(e);
			
		}
		finally
		{
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
			
			this.windows = null;
			
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
	private void loadNatives()
	{
		if (this.natives != null)
		{
			return;
		}
		
		ImmutableList<String> natives = Natives.getNatives();
		
		if (natives == null || natives.isEmpty())
		{
			return;
		}
		
		File tmp = new File(CompInfo.TMP_DIR, String.format("/.caelum/natives/%s/%s", VERSION.formatted, CompInfo.BUILT ? "" : "dev/"));
		
		for (String n : natives)
		{
			Logger.debug("Loading \"%s\"", n);
			
			copyNative(CaelumEngine.class.getResourceAsStream(n), FileHelper.createFile(tmp, ParseHelper.getSuffix(n, "/")));
			
		}
		
		this.natives = tmp;
		
	}
	
	@Internal
	private static void copyNative(InputStream is, File dest)
	{
		Logger.debug("Copying native %s", dest.getAbsolutePath());
		
		byte[] bytes = IOHelper.readBytes(is);
		
		if (bytes.length == 0)
		{
			throw new CaelumException("Could not read bytes from InputStream %s", is);
		}
		
		String name = dest.getName();
		
		if (!dest.exists())
		{
			Logger.debug("Cannot find \"%s\", attempting to create it", dest.getAbsolutePath());
			
			try
			{
				if (!dest.mkdirs())
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
			Logger.err("Could not generate checksum for \"%s\"; THIS IS A BUG!", name);
			
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
		
		try
		{
			IOHelper.write(bytes, dest);
			
		}
		catch (Exception e)
		{
			Logger.log(EnumLogType.WARN, "Cannot copy native %s; Loading anyway", e, name);
			
		}
		
		System.load(dest.getAbsolutePath());
		
	}
	
}
