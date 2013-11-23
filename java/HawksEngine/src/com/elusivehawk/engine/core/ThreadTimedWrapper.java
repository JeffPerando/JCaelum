
package com.elusivehawk.engine.core;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ThreadTimedWrapper extends ThreadTimed
{
	protected final IThreadable t;
	
	public ThreadTimedWrapper(IThreadable thr)
	{
		if (thr instanceof IGame && !(this instanceof ThreadGameLoop))
		{
			throw new RuntimeException("Use ThreadGameLoop instead!");
		}
		
		t = thr;
		
	}
	
	@Override
	public boolean initiate()
	{
		super.initiate();
		
		return this.t.initiate();
	}
	
	@Override
	public void update(double delta, boolean paused)
	{
		this.t.update(delta, paused);
		
	}
	
	@Override
	public int getTargetUpdateCount()
	{
		return this.t.getTargetUpdateCount();
	}
	
	@Override
	public double getMaxDelta()
	{
		return this.t.getMaxDelta();
	}
	
	@Override
	public void handleException(Throwable e)
	{
		this.t.handleException(e);
		
	}
	
	@Override
	public void onThreadStopped()
	{
		this.t.onThreadStopped();
		
	}
	
	@Override
	public boolean isPaused()
	{
		return super.isPaused() || this.t.isPaused();
	}
	
	@Override
	public synchronized void setPaused(boolean p)
	{
		super.setPaused(p);
		
		this.t.setPaused(p);
		
	}
	
}
