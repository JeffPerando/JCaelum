
package com.elusivehawk.engine.core.concurrent;

import com.elusivehawk.engine.core.IGame;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ThreadGameLoop extends ThreadTimed
{
	private final IGame g;
	
	public ThreadGameLoop(IGame game)
	{
		g = game;
		
	}
	
	@Override
	public boolean initiate()
	{
		super.initiate();
		
		return this.g.initiate();
	}
	
	@Override
	public void update(double delta)
	{
		this.g.update(delta);
		
	}
	
	@Override
	public void onThreadStopped()
	{
		this.g.onGameClosed();
		
	}
	
	@Override
	public void handleException(Throwable e)
	{
		this.g.handleException(e);
		
	}
	
	@Override
	public int getTargetUpdateCount()
	{
		return this.g.getTargetUpdateCount();
	}
	
	@Override
	public double getMaxDelta()
	{
		return 0.5;
	}
	
}
