
package com.elusivehawk.engine.core;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class ThreadStoppable extends Thread
{
	protected boolean running = true, paused = false;
	
	public synchronized final void stopThread()
	{
		this.running = false;
		
	}
	
	public synchronized final void setPaused(boolean pause)
	{
		this.paused = pause;
		
	}
	
}
