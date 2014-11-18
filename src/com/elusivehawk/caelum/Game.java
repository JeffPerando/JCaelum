
package com.elusivehawk.caelum;

import java.util.List;
import com.elusivehawk.caelum.assets.AssetManager;
import com.elusivehawk.caelum.physics.IPhysicsSimulator;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.util.IPausable;
import com.elusivehawk.util.Version;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@SuppressWarnings("unused")
public abstract class Game extends AbstractGameComponent implements IPausable
{
	private final List<IGameStateListener> gamestateLis = Lists.newArrayList();
	
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
	public final void initiate(GameArguments args, Display display, AssetManager assets) throws Throwable
	{
		this.initiateGame(args, assets);
		
		if (this.nextState != null)
		{
			this.nextState.initiate(args, display, assets);
			
			this.state = this.nextState;
			this.nextState = null;
			
		}
		
	}
	
	@Override
	public void render(RenderContext rcon)
	{
		if (this.state != null)
		{
			this.state.render(rcon);
			
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
	public void update(double delta) throws Throwable
	{
		try
		{
			if (this.state == null)
			{
				this.updateModules(delta);
				
			}
			else
			{
				this.state.update(delta);
				
			}
			
		}
		catch (Throwable e)
		{
			throw e;
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
					this.state.initiate(CaelumEngine.gameArgs(), CaelumEngine.defaultDisplay(), CaelumEngine.assets());
					
				}
				catch (Throwable e)
				{
					throw new RuntimeException("Error caught during game state initiation:", e);
				}
				
				if (!this.gamestateLis.isEmpty())
				{
					this.gamestateLis.forEach(((lis) -> {lis.onGameStateSwitch(this.state);}));
					
				}
				
			}
			
		}
		
	}
	
	@Override
	public String getFormattedName()
	{
		return this.getGameVersion() == null ? this.name : String.format("%s %s", this.name, this.getGameVersion());
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		if (this.state != null)
		{
			this.state.preRender(rcon, delta);
			
		}
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		if (this.state != null)
		{
			this.state.postRender(rcon);
			
		}
		
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
		this.gamestateLis.add(gsl);
		
	}
	
	//XXX Abstract methods
	
	protected abstract void initiateGame(GameArguments args, AssetManager assets) throws Throwable;
	
	protected abstract void onGameShutdown();
	
	public abstract Version getGameVersion();
	
}
