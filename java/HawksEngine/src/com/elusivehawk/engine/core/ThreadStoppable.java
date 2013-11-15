
package com.elusivehawk.engine.core;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class ThreadStoppable extends Thread
{
	protected boolean running = false, paused = false;
	
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
	public void run()
	{
		while (this.running)
		{
			if (!this.paused)
			{
				this.update();
				
			}
			
		}
		
	}
	
	public abstract void update();
	
	public synchronized final void stopThread()
	{
		this.running = false;
		
	}
	
	public synchronized final void setPaused(boolean pause)
	{
		this.paused = pause;
		
	}
	
	@Override
	public synchronized void start()
	{
		super.start();
		
		this.running = true;
		
	}
	
}
