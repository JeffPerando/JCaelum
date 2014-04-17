
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
public abstract class ThreadStoppable extends Thread implements IThreadStoppable
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
			this.stopThread();
			
			return;
		}
		
		while (this.isRunning() && this.canRun())
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
	
	@Override
	public boolean isPaused()
	{
		return this.paused;
	}
	
	@Override
	public synchronized void setPaused(boolean pause)
	{
		this.paused = pause;
		
	}
	
	@Override
	public final boolean isRunning()
	{
		return this.running;
	}
	
	@Override
	public synchronized final void stopThread()
	{
		this.running = false;
		
	}
	
	protected boolean initiate()
	{
		return true;
	}
	
	protected boolean canRun()
	{
		return true;
	}
	
	public abstract void rawUpdate() throws Throwable;
	
	public void onThreadStopped(){}
	
	public void handleException(Throwable e)
	{
		e.printStackTrace();
		
	}
	
}
