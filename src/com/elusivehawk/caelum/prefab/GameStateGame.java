
package com.elusivehawk.caelum.prefab;

import com.elusivehawk.caelum.CaelumEngine;
import com.elusivehawk.caelum.Display;
import com.elusivehawk.caelum.Game;
import com.elusivehawk.caelum.assets.AssetManager;
import com.elusivehawk.caelum.input.InputEvent;
import com.elusivehawk.caelum.physics.IPhysicsSimulator;
import com.elusivehawk.caelum.render.RenderContext;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class GameStateGame extends Game
{
	private GameState state = null, nextState = null;
	
	protected GameStateGame(String title)
	{
		super(title);
		
	}
	
	@Override
	public void onInputReceived(InputEvent event, double delta)
	{
		if (this.state != null)
		{
			this.state.onInputReceived(event, delta);
			
		}
		
	}
	
	@Override
	public final void initiate(Display display, AssetManager assets) throws Throwable
	{
		if (this.nextState != null)
		{
			this.nextState.initiate(display, assets);
			
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
	
	@Override
	public void onShutdown()
	{
		if (this.state != null)
		{
			this.state.onShutdown();
			
		}
		
	}
	
	@Override
	public IPhysicsSimulator getPhysicsSimulator()
	{
		return this.state == null ? null : this.state.getPhysicsSimulator();
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		if (this.state != null)
		{
			this.state.update(delta);
			
		}
		
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
				this.state.initiate(CaelumEngine.defaultDisplay(), CaelumEngine.assets());
				
			}
			catch (Throwable e)
			{
				throw new RuntimeException("Error caught during game state initiation:", e);
			}
			
		}
		
	}
	
	public void setGameState(GameState gs)
	{
		this.nextState = gs;
		
	}
	
}
