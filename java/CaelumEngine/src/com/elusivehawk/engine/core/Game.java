
package com.elusivehawk.engine.core;

import java.util.List;
import com.elusivehawk.engine.assets.AssetManager;
import com.elusivehawk.engine.physics.IPhysicsSimulator;
import com.elusivehawk.engine.render.RenderHelper;
import com.elusivehawk.engine.render.RenderSystem;
import com.elusivehawk.engine.render.old.IRenderHUB;
import com.elusivehawk.util.IPausable;
import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.Version;
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
	
	private final List<IGameStateListener> listeners = Lists.newArrayList();
	private final List<IUpdatable> modules = Lists.newArrayList();
	
	private GameState state = null, nextState = null;
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
				this.state.update(delta);
				
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
		return this.getGameVersion() == null ? this.name : String.format("%s %s", this.name, this.getGameVersion());
	}
	
	//XXX Optional/technical methods
	
	protected void preInit(GameArguments args){}
	
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
	
	//XXX Game state stuff
	
	public void setGameState(GameState gs)
	{
		this.nextState = gs;
		
	}
	
	public void addGameStateListener(IGameStateListener gsl)
	{
		this.listeners.add(gsl);
		
	}
	
	//XXX Module things
	
	public synchronized void addModule(IUpdatable m)
	{
		if (m instanceof Thread)
		{
			throw new CaelumException("Threads are NOT modules. Silly Buttons..."/*[sic]*/);
		}
		
		this.modules.add(m);
		
	}
	
	public synchronized void removeModule(IUpdatable m)
	{
		this.modules.remove(m);
		
	}
	
	/**
	 * 
	 * Called on every update, if there is no current game state.
	 * 
	 * @param delta
	 * @throws Throwable
	 */
	protected void tick(double delta) throws Throwable
	{
		for (IUpdatable m : this.modules)
		{
			m.update(delta);
			
		}
		
	}
	
	//XXX Abstract methods
	
	public abstract Version getGameVersion();
	
	protected abstract void initiate(GameArguments args) throws Throwable;
	
	protected abstract void onGameShutdown();
	
	//XXX Getters
	
	/**
	 * 
	 * Called during startup.
	 * 
	 * @return The rendering HUB to be used to render the game.
	 */
	@Deprecated
	public IRenderHUB getRenderHUB()
	{
		return this.state == null ? null : this.state.getRenderHUB();
	}
	
	/**
	 * 
	 * NOTICE: THIS IS NOT THREAD SAFE!<br>
	 * I mean it people, sync your entities and crap!
	 * 
	 * @param rsys
	 * @param delta
	 * 
	 * @see RenderHelper
	 */
	public void render(RenderSystem rsys, double delta)
	{
		if (this.state != null)
		{
			this.state.render(rsys, delta);
			
		}
		
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
