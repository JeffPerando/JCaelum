
package com.elusivehawk.engine;

import java.util.List;
import com.elusivehawk.engine.assets.AssetManager;
import com.elusivehawk.engine.physics.IPhysicsSimulator;
import com.elusivehawk.engine.render.DisplaySettings;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.util.IPausable;
import com.elusivehawk.util.Version;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@SuppressWarnings({"static-method", "unused"})
public abstract class Game extends AbstractGameComponent implements IPausable
{
	private final List<IGameStateListener> listeners = Lists.newArrayList();
	
	private GameState state = null, nextState = null;
	private boolean initiated = false, paused = false;
	
	protected Game(String title)
	{
		super(title);
		
	}
	
	@Override
	public boolean isPaused()
	{
		return this.paused;
	}
	
	@Override
	public void setPaused(boolean p)
	{
		this.paused = p;
		
	}
	
	@Override
	public final void initiate(GameArguments args, AssetManager assets) throws Throwable
	{
		this.initiateGame(args, assets);
		
		if (this.nextState != null)
		{
			this.nextState.initiate(args, assets);
			
			this.state = this.nextState;
			this.nextState = null;
			
		}
		
	}
	
	@Override
	public void render(RenderContext rcon, double delta)
	{
		if (this.state != null)
		{
			this.state.render(rcon, delta);
			
		}
		
	}
	
	@Override
	public final void onShutdown()
	{
		this.onGameShutdown();
		
		if (this.state != null)
		{
			this.state.onShutdown();
			
		}
		
	}
	
	//XXX Getters
	
	/**
	 * 
	 * Called during startup.
	 * 
	 * @return The physics simulator to use during the game's lifespan.
	 */
	@Override
	public IPhysicsSimulator getPhysicsSimulator()
	{
		return this.state == null ? null : this.state.getPhysicsSimulator();
	}
	
	//XXX Overridden methods
	
	@Override
	public final void update(double delta, Object... extra) throws GameTickException
	{
		try
		{
			if (this.state == null)
			{
				this.tick(delta, extra);
				
			}
			else
			{
				this.state.update(delta, extra);
				
			}
			
		}
		catch (Throwable e)
		{
			throw new GameTickException(e);
		}
		finally
		{
			if (this.nextState != null)
			{
				if (this.state != null)
				{
					this.state.onShutdown();
					
				}
				
				this.state = this.nextState;
				this.nextState = null;
				
				try
				{
					this.state.initiate(CaelumEngine.gameArgs(), CaelumEngine.assetManager());
					
				}
				catch (Throwable e)
				{
					throw new GameTickException("Error caught during game state initiation:", e);
				}
				
				if (!this.listeners.isEmpty())
				{
					this.listeners.forEach(((lis) -> {lis.onGameStateSwitch(this.state);}));
					
				}
				
			}
			
		}
		
	}
	
	@Override
	public String getFormattedName()
	{
		return this.getGameVersion() == null ? this.name : String.format("%s %s", this.name, this.getGameVersion());
	}
	
	//XXX Optional/technical methods
	
	protected void preInit(GameArguments args){}
	
	public int getUpdateCount()
	{
		return 30;
	}
	
	public DisplaySettings getDisplaySettings()
	{
		return null;
	}
	
	//XXX Game state stuff
	
	public void setGameState(GameState gs)
	{
		this.nextState = gs;
		
	}
	
	public void addGameStateListener(IGameStateListener gsl)
	{
		this.listeners.add(gsl);
		
	}
	
	/**
	 * 
	 * Called on every update, if there is no current game state.
	 * 
	 * @param delta
	 * @throws Throwable
	 */
	protected void tick(double delta, Object... extra) throws Throwable
	{
		this.updateModules(delta, extra);
		
	}
	
	//XXX Abstract methods
	
	protected abstract void initiateGame(GameArguments args, AssetManager assets) throws Throwable;
	
	protected abstract void onGameShutdown();
	
	public abstract Version getGameVersion();
	
}
