
package com.elusivehawk.engine.util;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class ThreadStoppable extends Thread
{
	protected boolean running = true;
	
	public synchronized final void stopThread()
	{
		this.running = false;
		
	}
	
}
