
package com.elusivehawk.engine.core;

import java.util.List;
import com.elusivehawk.engine.assets.AssetManager;
import com.elusivehawk.engine.physics.IPhysicsSimulator;
import com.elusivehawk.engine.render.IRenderHUB;
import com.elusivehawk.engine.util.IPausable;
import com.elusivehawk.engine.util.IUpdatable;
import com.elusivehawk.engine.util.Version;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@SuppressWarnings({"static-method", "unused"})
public abstract class Game implements IUpdatable, IPausable
{
	public final String name;
	
	private GameState state = null, nextState = null;
	private List<IGameStateListener> listeners = Lists.newArrayList();
	private boolean initiated = false, paused = false;
	
	@SuppressWarnings("unqualified-field-access")
	protected Game(String title)
	{
		name = title;
		
	}
	
	@Override
	public final void update(double delta) throws GameTickException
	{
		try
		{
			if (this.state == null)
			{
				this.tick(delta);
				
			}
			else
			{
				this.state.gsTick(this, delta);
				
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
					this.state.finish();
					
				}
				
				this.state = this.nextState;
				this.nextState = null;
				
				this.state.initiate();
				
				if (!this.listeners.isEmpty())
				{
					for (IGameStateListener gsl : this.listeners)
					{
						gsl.onGameStateSwitch(this.state);
						
					}
					
				}
				
			}
			
		}
		
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
	public String toString()
	{
		return this.getGameVersion() == null ? this.name : String.format("%s v%s", this.name, this.getGameVersion());
	}
	
	public final void initiateGame(GameArguments args) throws Throwable
	{
		this.initiate(args);
		
		if (this.nextState != null)
		{
			this.nextState.initiate();
			
			this.state = this.nextState;
			this.nextState = null;
			
		}
		
	}
	
	public void preInit(){}
	
	public void loadAssets(AssetManager mgr){}
	
	public void onScreenFlipped(boolean flip){}
	
	public int getUpdateCount()
	{
		return 30;
	}
	
	public final void onShutdown()
	{
		this.onGameShutdown();
		
		if (this.state != null)
		{
			this.state.finish();
			
		}
		
	}
	
	public void setGameState(GameState gs)
	{
		this.nextState = gs;
		
	}
	
	public void addGameStateListener(IGameStateListener gsl)
	{
		this.listeners.add(gsl);
		
	}
	
	public abstract Version getGameVersion();
	
	protected abstract void initiate(GameArguments args) throws Throwable;
	
	/**
	 * 
	 * Called on every update, if there is no current game state.
	 * 
	 * @param delta
	 * @throws Throwable
	 */
	protected abstract void tick(double delta) throws Throwable;
	
	/**
	 * Called during shutdown.
	 */
	protected abstract void onGameShutdown();
	
	/**
	 * 
	 * Called during startup.
	 * 
	 * @return The rendering HUB to be used to render the game.
	 */
	public IRenderHUB getRenderHUB()
	{
		return this.state == null ? null : this.state.getRenderHUB();
	}
	
	/**
	 * 
	 * Called during startup.
	 * 
	 * @return The physics simulator to use during the game's lifespan.
	 */
	public IPhysicsSimulator getPhysicsSimulator()
	{
		return this.state == null ? null : this.state.getPhysicsSimulator();
	}
	
}
