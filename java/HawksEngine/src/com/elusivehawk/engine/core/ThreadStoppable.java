
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
