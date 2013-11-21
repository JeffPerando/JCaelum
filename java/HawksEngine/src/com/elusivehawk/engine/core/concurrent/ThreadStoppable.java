
package com.elusivehawk.engine.core.concurrent;

import com.elusivehawk.engine.core.GameLog;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class ThreadStoppable extends Thread
{
	protected boolean running = true, paused = false;
	
	public ThreadStoppable()
	{
		super();
		
	}
	
	public ThreadStoppable(String name)
	{
		super(name);
		
	}
	
	public ThreadStoppable(Runnable r)
	{
		super(r);
		
	}
	
	@Override
	public final void run()
	{
		if (!this.initiate())
		{
			this.onThreadStopped();
			
			return;
		}
		
		while (this.isRunning())
		{
			if (!this.isPaused())
			{
				try
				{
					this.rawUpdate();
					
				}
				catch (Throwable e)
				{
					this.handleException(e);
					
				}
				
			}
			
		}
		
		this.onThreadStopped();
		
	}
	
	public boolean initiate()
	{
		return true;
	}
	
	public boolean isRunning()
	{
		return this.running;
	}
	
	public boolean isPaused()
	{
		return this.paused;
	}
	
	public abstract void rawUpdate();
	
	public void onThreadStopped(){}
	
	public void handleException(Throwable e)
	{
		GameLog.error(e);
		
	}
	
	public synchronized void stopThread()
	{
		this.running = false;
		
	}
	
	public synchronized void setPaused(boolean pause)
	{
		this.paused = pause;
		
	}
	
}
