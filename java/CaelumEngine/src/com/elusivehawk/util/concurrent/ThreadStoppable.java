
package com.elusivehawk.util.concurrent;

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
	private boolean running = false, paused = false;
	
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
		boolean failure = true;
		
		if (this.initiate())
		{
			failure = false;
			
			synchronized (this)
			{
				this.running = true;
				
			}
			
			try
			{
				this.firstUpdate();
				
			}
			catch (Throwable e)
			{
				this.handleException(e);
				
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
			
		}
		
		this.onThreadStopped(failure);
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
	
	public void firstUpdate() throws Throwable{}
	
	@SuppressWarnings("unused")
	public void onThreadStopped(boolean failed){}
	
	public void handleException(Throwable e)
	{
		e.printStackTrace();
		
	}
	
}
