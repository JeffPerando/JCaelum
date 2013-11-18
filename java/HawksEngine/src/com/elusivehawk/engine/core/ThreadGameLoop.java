
package com.elusivehawk.engine.core;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ThreadGameLoop extends ThreadTimed
{
	private final IGame g;
	private double delta;
	
	public ThreadGameLoop(IGame game)
	{
		g = game;
		
	}
	
	@Override
	public boolean initiate()
	{
		super.initiate();
		
		this.delta = (1000000000.0 / this.g.getTargetUpdateCount());
		
		return this.g.initiate();
	}
	
	@Override
	public void timedUpdate(double delta)
	{
		this.g.update(delta);
		
	}
	
	@Override
	public void onThreadStopped()
	{
		this.g.onGameClosed();
		
	}
	
	@Override
	public double getDelta()
	{
		return this.delta;
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
