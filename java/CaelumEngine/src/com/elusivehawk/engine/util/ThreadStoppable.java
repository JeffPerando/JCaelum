
package com.elusivehawk.engine.util;

/**
 * 
 * Abstract class for quick 'n dirty threading.
 * <p>
 * If you want a timed version, use {@link ThreadTimed}.
 * 
 * @author Elusivehawk
 */
@SuppressWarnings("static-method")
public abstract class ThreadStoppable extends Thread implements IPausable
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
	
	@Override
	public final void run()
	{
		if (!this.initiate())
		{
			this.running = false;
			
			return;
		}
		
		while (this.canRun())
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
		
		this.onThreadStopped();
		
		this.stopThread();
		
	}
	
	protected boolean initiate()
	{
		return true;
	}
	
	public final boolean isRunning()
	{
		return this.running;
	}
	
	@Override
	public boolean isPaused()
	{
		return this.paused;
	}
	
	protected boolean canRun()
	{
		return this.running;
	}
	
	public abstract void rawUpdate() throws Throwable;
	
	public void onThreadStopped(){}
	
	public void handleException(Throwable e)
	{
		e.printStackTrace();
		
	}
	
	public synchronized final void stopThread()
	{
		this.running = false;
		
	}
	
	@Override
	public synchronized void setPaused(boolean pause)
	{
		this.paused = pause;
		
	}
	
}
