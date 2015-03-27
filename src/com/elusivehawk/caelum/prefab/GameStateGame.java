
package com.elusivehawk.caelum.prefab;

import com.elusivehawk.caelum.Game;
import com.elusivehawk.caelum.input.Input;
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
	public void onInputReceived(Input input, double delta)
	{
		if (this.state != null)
		{
			this.state.onInputReceived(input, delta);
			
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
	public void preRender(RenderContext rcon)
	{
		if (this.state != null)
		{
			this.state.preRender(rcon);
			
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
	public void dispose()
	{
		if (this.state != null)
		{
			this.state.dispose();
			
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
	public void update(double delta) throws Throwable
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
				this.state.initiate();
				
			}
			catch (Throwable e)
			{
				throw new RuntimeException("Error caught during game state initiation:", e);
			}
			
		}
		
		if (this.state != null)
		{
			this.state.update(delta);
			
		}
		
	}
	
	public void setGameState(GameState gs)
	{
		this.nextState = gs;
		
	}
	
}
