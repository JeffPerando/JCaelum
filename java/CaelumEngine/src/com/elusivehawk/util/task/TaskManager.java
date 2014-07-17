
package com.elusivehawk.util.task;

import com.elusivehawk.util.IPausable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TaskManager implements IPausable
{
	private final ThreadTaskWorker[] threads;
	private boolean started = false, paused = true;
	private int next = 0;
	
	public TaskManager()
	{
		this(Runtime.getRuntime().availableProcessors());
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TaskManager(int threadCount)
	{
		threads = new ThreadTaskWorker[threadCount];
		
		for (int c = 0; c < threadCount; c++)
		{
			threads[c] = new ThreadTaskWorker();
			
		}
		
	}
	
	@Override
	public boolean isPaused()
	{
		return this.paused;
	}
	
	@Override
	public void setPaused(boolean p)
	{
		this.paused = p;
		
		for (ThreadTaskWorker th : this.threads)
		{
			th.setPaused(p);
			
		}
		
	}
	
	public void start()
	{
		if (this.started)
		{
			return;
		}
		
		for (ThreadTaskWorker t : this.threads)
		{
			t.start();
			
		}
		
		this.started = true;
		
	}
	
	public void stop()
	{
		if (!this.started)
		{
			return;
		}
		
		for (int c = 0; c < this.threads.length; c++)
		{
			this.threads[c].stopThread();
			
			this.threads[c] = null;
			
		}
		
		this.started = false;
		
	}
	
	public boolean scheduleTask(Task t)
	{
		if (!this.started)
		{
			return false;
		}
		
		this.threads[this.next++].scheduleTask(t);
		
		if (this.next == this.threads.length)
		{
			this.next = 0;
			
		}
		
		return true;
	}
	
}