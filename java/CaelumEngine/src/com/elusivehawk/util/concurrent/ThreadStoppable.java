
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
	private volatile boolean running = false;
	private boolean paused = false;
	
	public ThreadStoppable(){}
	
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
			
			this.running = true;
			
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
				
				this.onPostUpdate();
				
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
	public final void stopThread()
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
	
	protected abstract void rawUpdate() throws Throwable;
	
	protected void firstUpdate() throws Throwable{}
	
	protected void onPostUpdate(){};
	
	@SuppressWarnings("unused")
	public void onThreadStopped(boolean failed){}
	
	public void handleException(Throwable e)
	{
		e.printStackTrace();
		
	}
	
}
