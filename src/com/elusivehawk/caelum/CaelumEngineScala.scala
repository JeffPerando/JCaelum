
package com.elusivehawk.caelum

import java.io.File

import com.elusivehawk.caelum.assets.AssetManager
import com.elusivehawk.util.Version
import com.elusivehawk.util.concurrent.ThreadStoppable
import com.elusivehawk.util.parse.json.JsonObject
import com.elusivehawk.util.task.TaskManager

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
object CaelumEngineScala
{
	final val VERSION = new Version(Version.ALPHA, 1, 0, 0, 0);
	
	private final val threads: Map[EnumEngineFeature, ThreadStoppable] = Map();
	private final val tasks: TaskManager = new TaskManager();
	private final val assets: AssetManager = new AssetManager();
	
	private final val startargs: Map[String, String] = Map();
	private final val startupPrefixes: List[String] = List("env:", "gamefac:", "verbose:");
	
	private final var nativeLocation: File = null;
	private final var env: IGameEnvironment = null;
	private final var envConfig: JsonObject = null;
	
	private var factory: IGameFactory = null;
	private var game: Game = null;
	private var display: Display = null;
	private var displayManager: DisplayManager = null;
	
	def getGame: Game = this.game;
	
	def getAssets: AssetManager = this.assets;
	
	def getTasks: TaskManager = this.tasks;
	
	def getNativeLocation: File = this.nativeLocation;
	
	def main(args: Array[String]) =
	{
		Runtime.getRuntime.addShutdownHook(new Thread(new Runnable()
		{
			override def run
			{
				CaelumEngineScala.shutDownGame
				CaelumEngineScala.clearGameEnv
				
			}
			
		}));
		
		
		createGameEnv(args);
		startGame;
		setPaused(false);
		
		System.out.println("Spamspamspamspamspamspam")
		
	}
	
	def start(fac: IGameFactory, args: Array[String]) =
	{
		if (fac == null) throw new CaelumException(new NullPointerException());
		
		if (this.factory != null)
		{
			throw new CaelumException("Nuh uh uh, you didn't say the magic word...");
		}
		
		this.factory = fac;
		
		main(args);
		
	}
	
	def createGameEnv(args: Array[String]) =
	{
		
		
	}
	
	def startGame =
	{
		
		
	}
	
	def shutDownGame =
	{
		
		
	}
	
	def clearGameEnv =
	{
		
		
	}
	
	def setPaused(paused: Boolean) = {}
	
}